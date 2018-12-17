package com.github.lyd.rbac.producer.service.impl;

import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.mapper.CrudMapper;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.rbac.client.constans.RbacConstans;
import com.github.lyd.rbac.client.entity.*;
import com.github.lyd.rbac.producer.mapper.PermissionMapper;
import com.github.lyd.rbac.producer.mapper.ResourceActionMapper;
import com.github.lyd.rbac.producer.mapper.ResourceApiMapper;
import com.github.lyd.rbac.producer.mapper.ResourceMenuMapper;
import com.github.lyd.rbac.producer.service.PermissionService;
import com.github.lyd.rbac.producer.service.RolesService;
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
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private ResourceMenuMapper resourceMenuMapper;
    @Autowired
    private ResourceActionMapper resourceActionMapper;
    @Autowired
    private ResourceApiMapper apiMapperMapper;
    @Autowired
    private RolesService roleService;
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
            return resourceMenuMapper;
        }
        if (RbacConstans.RESOURCE_TYPE_ACTION.equals(resourceType)) {
            return resourceActionMapper;
        }
        if (RbacConstans.RESOURCE_TYPE_API.equals(resourceType)) {
            return apiMapperMapper;
        }
        return null;
    }

    /**
     * 获取租户授权列表
     *
     * @param tenantId     租户ID
     * @param resourceType 资源类型
     * @return
     */
    @Override
    public List<ResourcePermission> getTenantPermission(Long tenantId, String resourceType) {
        List<Roles> roles = roleService.getTenantRoles(tenantId);
        List<Long> roleIds = Lists.newArrayList();
        List<ResourcePermission> permissions = Lists.newArrayList();
        // 租户私有权限
        ExampleBuilder builder = new ExampleBuilder(ResourcePermission.class);
        Example example = builder.criteria()
                .andEqualTo("identityPrefix", RbacConstans.PERMISSION_IDENTITY_PREFIX_USER)
                .andEqualTo("resourceType", resourceType)
                .andEqualTo("identityId", tenantId)
                .end().build();
        List<ResourcePermission> userPermissions = permissionMapper.selectByExample(example);
        if (userPermissions != null) {
            permissions.addAll(userPermissions);
        }
        if (roles != null) {
            //获取角色权限
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
                        .end().build();
                List<ResourcePermission> rolePermissions = permissionMapper.selectByExample(example);
                if (rolePermissions != null) {
                    permissions.addAll(rolePermissions);
                }
            }
        }
        return permissions;
    }


    /**
     * 获取租户私有授权
     *
     * @param tenantId 租户ID
     * @return
     */
    @Override
    public List<ResourcePermission> getTenantPrivatePermission(Long tenantId) {
        List<ResourcePermission> permissions = Lists.newArrayList();
        // 租户私有权限
        ExampleBuilder builder = new ExampleBuilder(ResourcePermission.class);
        Example example = builder.criteria()
                .andEqualTo("identityPrefix", RbacConstans.PERMISSION_IDENTITY_PREFIX_USER)
                .andEqualTo("identityId", tenantId)
                .end().build();
        List<ResourcePermission> userPermissions = permissionMapper.selectByExample(example);
        if (userPermissions != null) {
            permissions.addAll(userPermissions);
        }
        return permissions;
    }

    /**
     * 获取所有授权列表
     *
     * @return
     */
    @Override
    public List<ResourcePermission> getPermissionList() {
        return permissionMapper.selectAll();
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
    public Boolean addPermission(Long identityId, String identityPrefix, String resourceType, Long... resourceIds) {
        if (!RbacConstans.PERMISSION_IDENTITY_PREFIX_USER.equals(identityPrefix) && !RbacConstans.PERMISSION_IDENTITY_PREFIX_ROLE.equals(identityPrefix)) {
            throw new OpenMessageException(String.format("%s所有者类型暂不支持!", identityPrefix));
        }
        CrudMapper crudMapper = getMapper(resourceType);
        if (crudMapper == null) {
            throw new OpenMessageException(String.format("%s资源类型暂不支持!", resourceType));
        }
        List<ResourcePermission> permissions = Lists.newArrayList();
        for (Long resource : resourceIds) {
            Object object = crudMapper.selectByPrimaryKey(resource);
            ResourcePermission permission = buildPermission(resourceType, identityPrefix, identityId, object);
            if (permission != null) {
                permissions.add(permission);
            }
        }
        if (permissions.isEmpty()) {
            return false;
        }
        //先清空所有者的权限
        ExampleBuilder builder = new ExampleBuilder(ResourcePermission.class);
        Example example = builder.criteria()
                .andEqualTo("identityPrefix", identityPrefix)
                .andEqualTo("identityId", identityId)
                .end().build();
        permissionMapper.deleteByExample(example);
        // 再重新批量授权
        int result = permissionMapper.insertList(permissions);
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
    public Boolean updatePermission(String resourceType, Long resourceId) {
        // 判断资源类型
        CrudMapper crudMapper = getMapper(resourceType);
        if (crudMapper == null) {
            return false;
        }
        Object object = crudMapper.selectByPrimaryKey(resourceId);
        if (object != null) {
            ResourcePermission permission = buildPermission(resourceType, null, null, object);
            if (permission != null) {
                ExampleBuilder builder = new ExampleBuilder(ResourcePermission.class);
                Example example = builder.criteria().andEqualTo("resourceId", resourceId)
                        .andEqualTo("resourceType", resourceType).end().build();
                ResourcePermission updateObj = new ResourcePermission();
                updateObj.setCode(permission.getCode());
                updateObj.setServiceId(permission.getServiceId());
                updateObj.setResourcePid(permission.getResourcePid());
                updateObj.setName(permission.getName());
                updateObj.setUrl(permission.getUrl());
                int count = permissionMapper.updateByExampleSelective(updateObj, example);
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
        ExampleBuilder builder = new ExampleBuilder(ResourcePermission.class);
        Example example = builder.criteria()
                .andEqualTo("resourceId", resourceId)
                .andEqualTo("resourceType", resourceType)
                .end().build();
        int count = permissionMapper.selectCountByExample(example);
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
    protected ResourcePermission buildPermission(String resourceType, String identityPrefix, Long identityId, Object object) {
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
        ResourcePermission permission = null;
        if (object instanceof ResourceMenu) {
            ResourceMenu menu = (ResourceMenu) object;
            code = menu.getMenuCode();
            url = menu.getUrl();
            resourceId = menu.getMenuId();
            resourcePid = menu.getParentId();
            serviceId = DEFAULT_SERVICE_ID;
            name = menu.getMenuName();
        }
        if (object instanceof ResourceAction) {
            ResourceAction action = (ResourceAction) object;
            code = action.getActionCode();
            url = action.getUrl();
            resourceId = action.getActionId();
            resourcePid = action.getMenuId();
            serviceId = DEFAULT_SERVICE_ID;
            name = action.getActionName();
        }
        if (object instanceof ResourceApi) {
            ResourceApi api = (ResourceApi) object;
            code = api.getApiCode();
            url = api.getUrl();
            resourceId = api.getApiId();
            resourcePid = 0L;
            serviceId = api.getServiceId();
            name = api.getApiName();
        }
        if (object != null) {
            //授权编码=资源类型_资源编码
            code = resourceType + RbacConstans.PERMISSION_SEPARATOR + code;
            if (identityPrefix != null) {
                if (RbacConstans.PERMISSION_IDENTITY_PREFIX_ROLE.equals(identityPrefix)) {
                    Roles role = roleService.getRole(identityId);
                    // 角色授权标识=ROLE_角色编码
                    identityCode = identityPrefix + role.getRoleCode();
                } else {
                    // 个人授权特殊标识=USER_资源编码_用户标识
                    identityCode = identityPrefix + code + identityId;
                }
            }
            permission = new ResourcePermission();
            permission.setCode(code.toUpperCase());
            permission.setServiceId(serviceId);
            permission.setResourceId(resourceId);
            permission.setResourcePid(resourcePid);
            permission.setUrl(url);
            permission.setName(name);
            permission.setIdentityCode(identityCode);
            permission.setIdentityPrefix(identityPrefix);
            permission.setIdentityId(identityId);
        }
        return permission;
    }
}
