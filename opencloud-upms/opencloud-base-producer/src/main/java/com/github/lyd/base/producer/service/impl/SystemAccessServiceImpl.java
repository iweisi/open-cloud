package com.github.lyd.base.producer.service.impl;

import com.github.lyd.base.client.entity.*;
import com.github.lyd.base.producer.mapper.SystemAccessMapper;
import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.mapper.CrudMapper;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.base.client.constans.RbacConstans;
import com.github.lyd.base.producer.mapper.SystemActionMapper;
import com.github.lyd.base.producer.mapper.SystemApiMapper;
import com.github.lyd.base.producer.mapper.SystemMenuMapper;
import com.github.lyd.base.producer.service.SystemAccessService;
import com.github.lyd.base.producer.service.SystemRoleService;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


/**
 * 资源授权
 * 对菜单、操作、API等进行权限分配操作
 *
 * @author liuyadu
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemAccessServiceImpl implements SystemAccessService {
    @Autowired
    private SystemAccessMapper systemAccessMapper;
    @Autowired
    private SystemMenuMapper systemMenuMapper;
    @Autowired
    private SystemActionMapper systemActionMapper;
    @Autowired
    private SystemApiMapper systemApiMapper;
    @Autowired
    private SystemRoleService systemRoleService;
    @Value("${spring.application.name}")
    private String DEFAULT_SERVICE_ID;

    /**
     * 获取mapper
     *
     * @param resourceType
     * @return
     */
    private CrudMapper getMapper(String resourceType) {
        // 判断资源类型
        if (RbacConstans.RESOURCE_TYPE_MENU.equals(resourceType)) {
            return systemMenuMapper;
        }
        if (RbacConstans.RESOURCE_TYPE_ACTION.equals(resourceType)) {
            return systemActionMapper;
        }
        if (RbacConstans.RESOURCE_TYPE_API.equals(resourceType)) {
            return systemApiMapper;
        }
        return null;
    }

    /**
     * 获取系统用户授权列表
     *
     * @param userId     系统用户ID
     * @param resourceType 资源类型
     * @return
     */
    @Override
    public List<SystemAccess> getUserAccess(Long userId, String resourceType) {
        List<SystemRole> roles = systemRoleService.getUserRoles(userId);
        List<Long> roleIds = Lists.newArrayList();
        List<SystemAccess> permissions = Lists.newArrayList();
        // 系统用户私有权限
        ExampleBuilder builder = new ExampleBuilder(SystemAccess.class);
        Example example = builder.criteria()
                .andEqualTo("identityPrefix", RbacConstans.PERMISSION_IDENTITY_PREFIX_USER)
                .andEqualTo("resourceType", resourceType)
                .andEqualTo("identityId", userId)
                .andEqualTo("enabled",1)
                .end().build();
        List<SystemAccess> userAccesss = systemAccessMapper.selectByExample(example);
        if (userAccesss != null) {
            permissions.addAll(userAccesss);
        }
        //系统用户角色权限
        if (roles != null) {
            roles.forEach(rbacRole -> {
                roleIds.add(rbacRole.getRoleId());
            });
            if (!roleIds.isEmpty()) {
                //强制清空查询
                example.clear();
                example = builder.criteria()
                        .andEqualTo("identityPrefix", RbacConstans.PERMISSION_IDENTITY_PREFIX_ROLE)
                        .andEqualTo("resourceType", resourceType)
                        .andIn("identityId", roleIds)
                        .andEqualTo("enabled",1)
                        .end().build();
                List<SystemAccess> roleAccesss = systemAccessMapper.selectByExample(example);
                if (roleAccesss != null) {
                    permissions.addAll(roleAccesss);
                }
            }
        }
        return permissions;
    }


    /**
     * 获取系统用户私有授权
     *
     * @param userId 系统用户ID
     * @return
     */
    @Override
    public List<SystemAccess> getUserPrivateAccess(Long userId) {
        List<SystemAccess> permissions = Lists.newArrayList();
        // 系统用户私有权限
        ExampleBuilder builder = new ExampleBuilder(SystemAccess.class);
        Example example = builder.criteria()
                .andEqualTo("identityPrefix", RbacConstans.PERMISSION_IDENTITY_PREFIX_USER)
                .andEqualTo("identityId", userId)
                .andEqualTo("enabled",1)
                .end().build();
        List<SystemAccess> userAccesss = systemAccessMapper.selectByExample(example);
        if (userAccesss != null) {
            permissions.addAll(userAccesss);
        }
        return permissions;
    }

    /**
     * 获取所有授权列表
     *
     * @return
     */
    @Override
    public List<SystemAccess> getAccessList() {
        ExampleBuilder builder = new ExampleBuilder(SystemAccess.class);
        Example example = builder.criteria()
                .andEqualTo("enabled",1)
                .end().build();
        return systemAccessMapper.selectByExample(example);
    }

    /**
     * 添加授权
     *
     * @param identityId     所有者ID
     * @param identityPrefix 所有者类型
     * @param resourceType   资源类型:
     * @param resourceIds
     * @return
     */
    @Override
    public Boolean addAccess(Long identityId, String identityPrefix, String resourceType, Long... resourceIds) {
        if (!RbacConstans.PERMISSION_IDENTITY_PREFIX_USER.equals(identityPrefix) && !RbacConstans.PERMISSION_IDENTITY_PREFIX_ROLE.equals(identityPrefix)) {
            throw new OpenMessageException(String.format("%s所有者类型暂不支持!", identityPrefix));
        }
        CrudMapper crudMapper = getMapper(resourceType);
        if (crudMapper == null) {
            throw new OpenMessageException(String.format("%s资源类型暂不支持!", resourceType));
        }
        List<SystemAccess> permissions = Lists.newArrayList();
        for (Long resource : resourceIds) {
            Object object = crudMapper.selectByPrimaryKey(resource);
            SystemAccess permission = buildAccess(resourceType, identityPrefix, identityId, object);
            if (permission != null) {
                permissions.add(permission);
            }
        }
        if (permissions.isEmpty()) {
            return false;
        }
        //先清空所有者的权限
        ExampleBuilder builder = new ExampleBuilder(SystemAccess.class);
        Example example = builder.criteria()
                .andEqualTo("identityPrefix", identityPrefix)
                .andEqualTo("identityId", identityId)
                .end().build();
        systemAccessMapper.deleteByExample(example);
        // 再重新批量授权
        int result = systemAccessMapper.insertList(permissions);
        return result > 0;
    }


    /**
     * 更新授权信息
     *
     * @param resourceType
     * @param resourceId
     * @return
     */
    @Override
    public Boolean updateAccess(String resourceType, Long resourceId) {
        // 判断资源类型
        CrudMapper crudMapper = getMapper(resourceType);
        if (crudMapper == null) {
            return false;
        }
        Object object = crudMapper.selectByPrimaryKey(resourceId);
        if (object != null) {
            SystemAccess permission = buildAccess(resourceType, null, null, object);
            if (permission != null) {
                ExampleBuilder builder = new ExampleBuilder(SystemAccess.class);
                Example example = builder.criteria().andEqualTo("resourceId", resourceId)
                        .andEqualTo("resourceType", resourceType).end().build();
                SystemAccess updateObj = new SystemAccess();
                updateObj.setCode(permission.getCode());
                updateObj.setEnabled(permission.getEnabled());
                updateObj.setServiceId(permission.getServiceId());
                updateObj.setResourcePid(permission.getResourcePid());
                updateObj.setName(permission.getName());
                updateObj.setUrl(permission.getUrl());
                int count = systemAccessMapper.updateByExampleSelective(updateObj, example);
                return count > 0;
            }

        }
        return false;
    }

    /**
     * 检查资源是否存在
     *
     * @param resourceId
     * @param resourceType
     * @return
     */
    @Override
    public Boolean isExist(Long resourceId, String resourceType) {
        ExampleBuilder builder = new ExampleBuilder(SystemAccess.class);
        Example example = builder.criteria()
                .andEqualTo("resourceId", resourceId)
                .andEqualTo("resourceType", resourceType)
                .end().build();
        int count = systemAccessMapper.selectCountByExample(example);
        return count > 0;
    }

    /**
     * 构建授权对象
     *
     * @param resourceType   资源类型
     * @param identityPrefix 授权身份前缀
     * @param identityId     授权身份ID
     * @param object         资源对象
     * @return
     */
    protected SystemAccess buildAccess(String resourceType, String identityPrefix, Long identityId, Object object) {
        if (object == null) {
            return null;
        }
        String code = null;
        String url = null;
        Long resourceId = null;
        Long resourcePid = null;
        String serviceId = "";
        String name = "";
        String identityCode = null;
        Boolean enabled = false;
        SystemAccess permission = null;
        if (object instanceof SystemMenu) {
            SystemMenu menu = (SystemMenu) object;
            code = menu.getMenuCode();
            url = menu.getUrl();
            resourceId = menu.getMenuId();
            resourcePid = menu.getParentId();
            serviceId = DEFAULT_SERVICE_ID;
            enabled = menu.getEnabled();
            name = menu.getMenuName();
        }
        if (object instanceof SystemAction) {
            SystemAction action = (SystemAction) object;
            code = action.getActionCode();
            url = action.getUrl();
            resourceId = action.getActionId();
            resourcePid = action.getMenuId();
            serviceId = DEFAULT_SERVICE_ID;
            name = action.getActionName();
            enabled = action.getEnabled();
        }
        if (object instanceof SystemApi) {
            SystemApi api = (SystemApi) object;
            code = api.getApiCode();
            url = api.getUrl();
            resourceId = api.getApiId();
            resourcePid = 0L;
            serviceId = api.getServiceId();
            name = api.getApiName();
            enabled = api.getEnabled();
        }
        if (object != null) {
            //授权编码=资源类型_资源编码
            code = resourceType + RbacConstans.PERMISSION_SEPARATOR + code;
            if (identityPrefix != null) {
                if (RbacConstans.PERMISSION_IDENTITY_PREFIX_ROLE.equals(identityPrefix)) {
                    SystemRole role = systemRoleService.getRole(identityId);
                    // 角色授权标识=ROLE_角色编码
                    identityCode = identityPrefix + role.getRoleCode();
                } else {
                    // 个人授权特殊标识=USER_资源编码_用户标识
                    identityCode = identityPrefix + code + identityId;
                }
            }
            permission = new SystemAccess();
            permission.setCode(code);
            permission.setServiceId(serviceId);
            permission.setResourceId(resourceId);
            permission.setResourcePid(resourcePid);
            permission.setUrl(url);
            permission.setName(name);
            permission.setEnabled(enabled);
            permission.setIdentityCode(identityCode);
            permission.setIdentityPrefix(identityPrefix);
            permission.setIdentityId(identityId);
        }
        return permission;
    }
}
