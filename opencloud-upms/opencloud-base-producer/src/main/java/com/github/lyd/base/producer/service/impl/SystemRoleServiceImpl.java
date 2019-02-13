package com.github.lyd.base.producer.service.impl;

import com.github.lyd.base.client.constants.BaseConstants;
import com.github.lyd.base.client.entity.SystemRole;
import com.github.lyd.base.client.entity.SystemUserRole;
import com.github.lyd.base.producer.mapper.SystemRoleMapper;
import com.github.lyd.base.producer.mapper.SystemUserRoleMapper;
import com.github.lyd.base.producer.service.SystemRoleService;
import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;


/**
 * @author liuyadu
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemRoleServiceImpl implements SystemRoleService {
    @Autowired
    private SystemRoleMapper systemRoleMapper;
    @Autowired
    private SystemUserRoleMapper systemUserRoleMapper;

    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    @Override
    public PageList<SystemRole> findListPage(PageParams pageParams, String keyword) {
        PageHelper.startPage(pageParams.getPage(), pageParams.getLimit(), pageParams.getOrderBy());
        List<SystemRole> list = systemRoleMapper.selectRoleList(null);
        return new PageList(list);
    }

    /**
     * 查询列表
     *
     * @param keyword
     * @return
     */
    @Override
    public PageList<SystemRole> findList(String keyword) {
        ExampleBuilder builder = new ExampleBuilder(SystemRole.class);
        Example example = builder.criteria()
                .orLike("roleCode", keyword)
                .orLike("roleName", keyword).end().build();
        example.orderBy("roleId").asc();
        List<SystemRole> list = systemRoleMapper.selectByExample(example);
        return new PageList(list);
    }

    /**
     * 获取角色信息
     *
     * @param roleId
     * @return
     */
    @Override
    public SystemRole getRole(Long roleId) {
        return systemRoleMapper.selectByPrimaryKey(roleId);
    }

    /**
     * 添加角色
     *
     * @param role 角色
     * @return
     */
    @Override
    public Long addRole(SystemRole role) {
        if (isExist(role.getRoleCode())) {
            throw new OpenMessageException(String.format("%s角色编码已存在,不允许重复添加", role.getRoleCode()));
        }
        if (role.getStatus() == null) {
            role.setStatus(BaseConstants.ENABLED);
        }
        if (role.getIsPersist() == null) {
            role.setIsPersist(BaseConstants.DISABLED);
        }
        role.setCreateTime(new Date());
        role.setUpdateTime(role.getCreateTime());
        systemRoleMapper.insertSelective(role);
        return role.getRoleId();
    }

    /**
     * 更新角色
     *
     * @param role 角色
     * @return
     */
    @Override
    public void updateRole(SystemRole role) {
        if (role.getRoleId() == null) {
            throw new OpenMessageException("ID不能为空");
        }
        SystemRole savedRole = getRole(role.getRoleId());
        if (role == null) {
            throw new OpenMessageException(String.format("roleId=%s不存在", role.getRoleId()));
        }
        if (!savedRole.getRoleCode().equals(role.getRoleCode())) {
            // 和原来不一致重新检查唯一性
            if (isExist(role.getRoleCode())) {
                throw new OpenMessageException(String.format("%s菜单编码已存在,不允许重复添加", role.getRoleCode()));
            }
        }
        role.setUpdateTime(new Date());
        systemRoleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return
     */
    @Override
    public void removeRole(Long roleId) {
        if (roleId == null) {
            return;
        }
        SystemRole role = getRole(roleId);
        if (role != null && role.getIsPersist().equals(BaseConstants.ENABLED)) {
            throw new OpenMessageException(String.format("保留数据,不允许删除"));
        }
        int count = getCountByRole(roleId);
        if (count > 0) {
            throw new OpenMessageException("该角色下存在授权组员,不允许删除!");
        }
        systemRoleMapper.deleteByPrimaryKey(roleId);
    }

    /**
     * 检测角色编码是否存在
     *
     * @param roleCode
     * @return
     */
    @Override
    public Boolean isExist(String roleCode) {
        if (StringUtils.isBlank(roleCode)) {
            throw new OpenMessageException("roleCode is null");
        }
        ExampleBuilder builder = new ExampleBuilder(SystemRole.class);
        Example example = builder.criteria().andEqualTo("roleCode", roleCode).end().build();
        return systemRoleMapper.selectCountByExample(example) > 0;
    }

    /**
     * 成员分配角色
     *
     * @param userId
     * @param roles
     * @return
     */
    @Override
    public void saveMemberRoles(Long userId, Long... roles) {
        if (userId == null || roles == null) {
            return;
        }
        if (roles.length == 0) {
            return;
        }
        // 先清空,在添加
        removeMemberRoles(userId);
        List<SystemUserRole> list = Lists.newArrayList();
        for (Long roleId : roles) {
            SystemUserRole roleUser = new SystemUserRole();
            roleUser.setUserId(userId);
            roleUser.setRoleId(roleId);
            list.add(roleUser);
        }
        // 批量保存
        systemUserRoleMapper.insertList(list);
    }

    /**
     * 获取角色所有授权组员数量
     *
     * @param roleId
     * @return
     */
    @Override
    public int getCountByRole(Long roleId) {
        ExampleBuilder builder = new ExampleBuilder(SystemUserRole.class);
        Example example = builder.criteria().andEqualTo("roleId", roleId).end().build();
        int result = systemUserRoleMapper.selectCountByExample(example);
        return result;
    }

    /**
     * 获取组员角色数量
     *
     * @param userId
     * @return
     */
    @Override
    public int getCountByUser(Long userId) {
        ExampleBuilder builder = new ExampleBuilder(SystemUserRole.class);
        Example example = builder.criteria().andEqualTo("userId", userId).end().build();
        int result = systemUserRoleMapper.selectCountByExample(example);
        return result;
    }

    /**
     * 移除角色所有组员
     *
     * @param roleId
     * @return
     */
    @Override
    public void removeRoleMembers(Long roleId) {
        ExampleBuilder builder = new ExampleBuilder(SystemUserRole.class);
        Example example = builder.criteria().andEqualTo("roleId", roleId).end().build();
        systemUserRoleMapper.deleteByExample(example);
    }

    /**
     * 移除组员的所有角色
     *
     * @param userId
     * @return
     */
    @Override
    public void removeMemberRoles(Long userId) {
        ExampleBuilder builder = new ExampleBuilder(SystemUserRole.class);
        Example example = builder.criteria().andEqualTo("userId", userId).end().build();
        systemUserRoleMapper.deleteByExample(example);
    }

    /**
     * 更新启用禁用
     *
     * @param roleId
     * @param status
     * @return
     */
    @Override
    public void updateStatus(Long roleId, Integer status) {
        SystemRole role = new SystemRole();
        role.setRoleId(roleId);
        role.setStatus(status);
        role.setUpdateTime(new Date());
        systemRoleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 检测是否存在
     *
     * @param userId
     * @param roleId
     * @return
     */
    @Override
    public Boolean isExist(Long userId, Long roleId) {
        ExampleBuilder builder = new ExampleBuilder(SystemUserRole.class);
        Example example = builder.criteria()
                .andEqualTo("userId", userId)
                .andEqualTo("roleId", roleId)
                .end().build();
        int result = systemUserRoleMapper.selectCountByExample(example);
        return result > 0;
    }


    /**
     * 获取组员角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<SystemRole> getUserRoles(Long userId) {
        List<SystemRole> roles = systemUserRoleMapper.selectUserRoleList(userId);
        return roles;
    }


}
