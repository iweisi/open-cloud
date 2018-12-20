package com.github.lyd.sys.producer.service.impl;

import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.utils.StringUtils;
import com.github.lyd.sys.client.entity.SystemUserRole;
import com.github.lyd.sys.client.entity.SystemRole;
import com.github.lyd.sys.producer.mapper.SystemRoleMapper;
import com.github.lyd.sys.producer.mapper.SystemUserRoleMapper;
import com.github.lyd.sys.producer.service.SystemRoleService;
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
    private SystemRoleMapper roleMapper;
    @Autowired
    private SystemUserRoleMapper rolesMemberMapper;

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
        List<SystemRole> list = roleMapper.selectRoleList(null);
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
        return roleMapper.selectByPrimaryKey(roleId);
    }

    /**
     * 添加角色
     *
     * @param roleCode    角色编码
     * @param roleName    角色显示名称
     * @param description 描述
     * @param enable      启用禁用
     * @return
     */
    @Override
    public Boolean addRole(String roleCode, String roleName, String description, Boolean enable) {
        if (StringUtils.isBlank(roleCode)) {
            return false;
        }
        if (StringUtils.isBlank(roleName)) {
            return false;
        }
        if (isExist(roleCode)) {
            throw new OpenMessageException(String.format("roleCode=%s已存在", roleCode));
        }
        SystemRole role = new SystemRole();
        role.setCreateTime(new Date());
        role.setUpdateTime(role.getCreateTime());
        role.setRoleCode(roleCode);
        role.setRoleName(roleName);
        role.setEnabled(enable ? 1 : 0);
        int result = roleMapper.insertSelective(role);
        return result > 0;
    }

    /**
     * 更新角色
     *
     * @param roleId      角色ID
     * @param roleCode    角色编码
     * @param roleName    角色显示名称
     * @param description 描述
     * @param enable      启用禁用
     * @return
     */
    @Override
    public Boolean updateRole(Long roleId, String roleCode, String roleName, String description, Boolean enable) {
        if (roleId == null) {
            return false;
        }
        if (StringUtils.isBlank(roleCode)) {
            return false;
        }
        if (StringUtils.isBlank(roleName)) {
            return false;
        }
        SystemRole role = getRole(roleId);
        if (role == null) {
            throw new OpenMessageException(String.format("roleId=%s不存在", roleId));
        }
        if (!role.getRoleCode().equals(roleCode) && isExist(roleCode)) {
            // 和原先不一致 重新校验唯一性
            throw new OpenMessageException(String.format("roleCode=%s已存在", roleCode));
        }
        role.setUpdateTime(new Date());
        role.setRoleCode(roleCode);
        role.setRoleName(roleName);
        role.setEnabled(enable ? 1 : 0);
        int result = roleMapper.updateByPrimaryKeySelective(role);
        return result > 0;
    }

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return
     */
    @Override
    public Boolean removeRole(Long roleId) {
        if (roleId == null) {
            return false;
        }
        int count = getCountByRole(roleId);
        if (count > 0) {
            throw new OpenMessageException("该角色下存在授权组员,不允许删除!");
        }
        int result = roleMapper.deleteByPrimaryKey(roleId);
        return result > 0;
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
        return roleMapper.selectCountByExample(example) > 0;
    }

    /**
     * 成员分配角色
     *
     * @param userId
     * @param roles
     * @return
     */
    @Override
    public Boolean saveMemberRoles(Long userId, Long... roles) {
        if (userId == null || roles == null) {
            return false;
        }
        if (roles.length == 0) {
            return false;
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
        int result = rolesMemberMapper.insertList(list);
        return result > 0;
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
        int result = rolesMemberMapper.selectCountByExample(example);
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
        int result = rolesMemberMapper.selectCountByExample(example);
        return result;
    }

    /**
     * 移除角色所有组员
     *
     * @param roleId
     * @return
     */
    @Override
    public Boolean removeRoleMembers(Long roleId) {
        ExampleBuilder builder = new ExampleBuilder(SystemUserRole.class);
        Example example = builder.criteria().andEqualTo("roleId", roleId).end().build();
        int result = rolesMemberMapper.deleteByExample(example);
        return result > 0;
    }

    /**
     * 移除组员的所有角色
     *
     * @param userId
     * @return
     */
    @Override
    public Boolean removeMemberRoles(Long userId) {
        ExampleBuilder builder = new ExampleBuilder(SystemUserRole.class);
        Example example = builder.criteria().andEqualTo("userId", userId).end().build();
        int result = rolesMemberMapper.deleteByExample(example);
        return result > 0;
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
        int result = rolesMemberMapper.selectCountByExample(example);
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
        List<SystemRole> roles = rolesMemberMapper.selectUserRoleList(userId);
        return roles;
    }


}
