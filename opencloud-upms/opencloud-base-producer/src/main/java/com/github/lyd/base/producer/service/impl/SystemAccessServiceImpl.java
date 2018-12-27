package com.github.lyd.base.producer.service.impl;

import com.github.lyd.base.client.entity.*;
import com.github.lyd.base.producer.mapper.SystemGrantAccessMapper;
import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.mapper.CrudMapper;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.base.client.constants.BaseConstants;
import com.github.lyd.base.producer.mapper.SystemActionMapper;
import com.github.lyd.base.producer.mapper.SystemApiMapper;
import com.github.lyd.base.producer.mapper.SystemMenuMapper;
import com.github.lyd.base.producer.service.SystemGrantAccessService;
import com.github.lyd.base.producer.service.SystemRoleService;
import com.github.lyd.common.utils.StringUtils;
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
public class SystemAccessServiceImpl implements SystemGrantAccessService {
    @Autowired
    private SystemGrantAccessMapper systemAccessMapper;
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
    private String DEFAULT_PREFIX = "/";
    private String DEFAULT_TARGET = "_self";

    /**
     * 获取mapper
     *
     * @param resourceType
     * @return
     */
    private CrudMapper getMapper(String resourceType) {
        // 判断资源类型
        if (BaseConstants.RESOURCE_TYPE_MENU.equals(resourceType)) {
            return systemMenuMapper;
        }
        if (BaseConstants.RESOURCE_TYPE_ACTION.equals(resourceType)) {
            return systemActionMapper;
        }
        if (BaseConstants.RESOURCE_TYPE_API.equals(resourceType)) {
            return systemApiMapper;
        }
        return null;
    }

    /**
     * 获取系统用户授权列表
     *
     * @param userId       系统用户ID
     * @param resourceType 资源类型
     * @return
     */
    @Override
    public List<SystemGrantAccess> getUserAccess(Long userId, String resourceType) {
        List<SystemRole> roles = systemRoleService.getUserRoles(userId);
        List<Long> roleIds = Lists.newArrayList();
        List<SystemGrantAccess> permissions = Lists.newArrayList();
        // 系统用户私有权限
        ExampleBuilder builder = new ExampleBuilder(SystemGrantAccess.class);
        Example example = builder.criteria()
                .andEqualTo("grantOwnerType", BaseConstants.PERMISSION_IDENTITY_PREFIX_USER)
                .andEqualTo("resourceType", resourceType)
                .andEqualTo("grantOwnerId", userId)
                .andEqualTo("status", BaseConstants.ENABLED)
                .end().build();
        List<SystemGrantAccess> userAccesss = systemAccessMapper.selectByExample(example);
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
                        .andEqualTo("grantOwnerType", BaseConstants.PERMISSION_IDENTITY_PREFIX_ROLE)
                        .andEqualTo("resourceType", resourceType)
                        .andIn("grantOwnerId", roleIds)
                        .andEqualTo("status", BaseConstants.ENABLED)
                        .end().build();
                List<SystemGrantAccess> roleAccesss = systemAccessMapper.selectByExample(example);
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
    public List<SystemGrantAccess> getUserPrivateAccess(Long userId) {
        List<SystemGrantAccess> permissions = Lists.newArrayList();
        // 系统用户私有权限
        ExampleBuilder builder = new ExampleBuilder(SystemGrantAccess.class);
        Example example = builder.criteria()
                .andEqualTo("grantOwnerType", BaseConstants.PERMISSION_IDENTITY_PREFIX_USER)
                .andEqualTo("grantOwnerId", userId)
                .andEqualTo("status", BaseConstants.ENABLED)
                .end().build();
        List<SystemGrantAccess> userAccesss = systemAccessMapper.selectByExample(example);
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
    public List<SystemGrantAccess> getAccessList() {
        ExampleBuilder builder = new ExampleBuilder(SystemGrantAccess.class);
        Example example = builder.criteria()
                .andEqualTo("status", BaseConstants.ENABLED)
                .andEqualTo("prefix", DEFAULT_PREFIX)
                .end().build();
        return systemAccessMapper.selectByExample(example);
    }

    /**
     * 添加授权
     *
     * @param grantOwnerId     所有者ID
     * @param grantOwnerType 所有者类型
     * @param resourceType   资源类型:
     * @param resourceIds
     * @return
     */
    @Override
    public Boolean addAccess(Long grantOwnerId, String grantOwnerType, String resourceType, Long... resourceIds) {
        if (!BaseConstants.PERMISSION_IDENTITY_PREFIX_USER.equals(grantOwnerType) && !BaseConstants.PERMISSION_IDENTITY_PREFIX_ROLE.equals(grantOwnerType)) {
            throw new OpenMessageException(String.format("%s所有者类型暂不支持!", grantOwnerType));
        }
        CrudMapper crudMapper = getMapper(resourceType);
        if (crudMapper == null) {
            throw new OpenMessageException(String.format("%s资源类型暂不支持!", resourceType));
        }
        List<SystemGrantAccess> permissions = Lists.newArrayList();
        for (Long resource : resourceIds) {
            Object object = crudMapper.selectByPrimaryKey(resource);
            SystemGrantAccess permission = buildAccess(resourceType, grantOwnerType, grantOwnerId, object);
            if (permission != null) {
                permissions.add(permission);
            }
        }
        if (permissions.isEmpty()) {
            return false;
        }
        //先清空所有者的权限
        ExampleBuilder builder = new ExampleBuilder(SystemGrantAccess.class);
        Example example = builder.criteria()
                .andEqualTo("grantOwnerType", grantOwnerType)
                .andEqualTo("grantOwnerId", grantOwnerId)
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
            SystemGrantAccess permission = buildAccess(resourceType, null, null, object);
            if (permission != null) {
                ExampleBuilder builder = new ExampleBuilder(SystemGrantAccess.class);
                Example example = builder.criteria().andEqualTo("resourceId", resourceId)
                        .andEqualTo("resourceType", resourceType).end().build();
                SystemGrantAccess updateObj = new SystemGrantAccess();
                updateObj.setCode(permission.getCode());
                updateObj.setStatus(permission.getStatus());
                updateObj.setServiceId(permission.getServiceId());
                updateObj.setResourcePid(permission.getResourcePid());
                updateObj.setName(permission.getName());
                updateObj.setIcon(permission.getIcon());
                updateObj.setPath(permission.getPath());
                updateObj.setPrefix(permission.getPrefix());
                updateObj.setTarget(permission.getTarget());
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
        ExampleBuilder builder = new ExampleBuilder(SystemGrantAccess.class);
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
     * @param resourceType 资源类型
     * @param grantOwnerType    授权所有者类型
     * @param grantOwnerId      授权所有者ID
     * @param object       资源对象
     * @return
     */
    protected SystemGrantAccess buildAccess(String resourceType, String grantOwnerType, Long grantOwnerId, Object object) {
        if (object == null) {
            return null;
        }
        String code = null;
        String path = null;
        String prefix = null;
        String target = null;
        Long resourceId = null;
        Long resourcePid = null;
        String serviceId = "";
        String name = "";
        String ownerCode = null;
        Integer status = 0;
        String icon = null;
        SystemGrantAccess permission = null;
        if (object instanceof SystemMenu) {
            SystemMenu menu = (SystemMenu) object;
            code = menu.getMenuCode();
            path = menu.getPath();
            prefix = menu.getPrefix();
            target = menu.getTarget();
            resourceId = menu.getMenuId();
            resourcePid = menu.getParentId();
            serviceId = DEFAULT_SERVICE_ID;
            status = menu.getStatus();
            name = menu.getMenuName();
            icon = menu.getIcon();
        }
        if (object instanceof SystemAction) {
            SystemAction action = (SystemAction) object;
            code = action.getActionCode();
            path = action.getPath();
            prefix = DEFAULT_PREFIX;
            target = DEFAULT_TARGET;
            resourceId = action.getActionId();
            resourcePid = action.getMenuId();
            serviceId = DEFAULT_SERVICE_ID;
            name = action.getActionName();
            status = action.getStatus();
        }
        if (object instanceof SystemApi) {
            SystemApi api = (SystemApi) object;
            code = api.getApiCode();
            path = api.getPath();
            prefix = DEFAULT_PREFIX;
            target = DEFAULT_TARGET;
            resourceId = api.getApiId();
            resourcePid = 0L;
            serviceId = api.getServiceId();
            name = api.getApiName();
            status = api.getStatus();
        }
        if (object != null) {
            //授权编码=资源类型_资源编码
            code = resourceType + BaseConstants.PERMISSION_SEPARATOR + code;
            if (grantOwnerType != null) {
                if (BaseConstants.PERMISSION_IDENTITY_PREFIX_ROLE.equals(grantOwnerType)) {
                    SystemRole role = systemRoleService.getRole(grantOwnerId);
                    // 角色授权标识=ROLE_角色编码
                    ownerCode = grantOwnerType + role.getRoleCode();
                } else {
                    // 个人授权特殊标识=USER_资源编码_用户标识
                    ownerCode = grantOwnerType + code + grantOwnerId;
                }
            }
            permission = new SystemGrantAccess();
            permission.setCode(code);
            permission.setServiceId(serviceId);
            permission.setResourceId(resourceId);
            permission.setResourcePid(resourcePid);
            if (StringUtils.isNotBlank(path)) {
                if (path.startsWith(DEFAULT_PREFIX)) {
                    path = path.substring(1);
                }
            }
            permission.setPath(path);
            permission.setPrefix(prefix);
            permission.setTarget(target);
            permission.setName(name);
            permission.setIcon(icon);
            permission.setStatus(status);
            permission.setGrantOwnerCode(ownerCode);
            permission.setGrantOwnerType(grantOwnerType);
            permission.setGrantOwnerId(grantOwnerId);
        }
        return permission;
    }
}
