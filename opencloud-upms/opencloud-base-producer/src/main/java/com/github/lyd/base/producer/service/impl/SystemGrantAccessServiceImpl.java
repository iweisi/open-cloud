package com.github.lyd.base.producer.service.impl;

import com.github.lyd.base.client.constants.BaseConstants;
import com.github.lyd.base.client.entity.*;
import com.github.lyd.base.producer.mapper.SystemActionMapper;
import com.github.lyd.base.producer.mapper.SystemApiMapper;
import com.github.lyd.base.producer.mapper.SystemGrantAccessMapper;
import com.github.lyd.base.producer.mapper.SystemMenuMapper;
import com.github.lyd.base.producer.service.SystemGrantAccessService;
import com.github.lyd.base.producer.service.SystemRoleService;
import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.mapper.CrudMapper;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.common.utils.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;


/**
 * 资源授权
 * 对菜单、操作、API等进行权限分配操作
 *
 * @author liuyadu
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemGrantAccessServiceImpl implements SystemGrantAccessService {
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

    private final List<String> AUTH_PREFIX_LIST = Arrays.asList(new String[]{
            BaseConstants.AUTHORITY_PREFIX_ROLE,
            BaseConstants.AUTHORITY_PREFIX_USER,
            BaseConstants.AUTHORITY_PREFIX_APP});

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
    public List<SystemGrantAccess> getUserGrantAccess(Long userId, String resourceType) {
        List<SystemRole> roles = systemRoleService.getUserRoles(userId);
        List<Long> roleIds = Lists.newArrayList();
        List<SystemGrantAccess> permissions = Lists.newArrayList();
        // 系统用户私有权限
        ExampleBuilder builder = new ExampleBuilder(SystemGrantAccess.class);
        Example example = builder.criteria()
                .andEqualTo("authorityPrefix", BaseConstants.AUTHORITY_PREFIX_USER)
                .andEqualTo("resourceType", resourceType)
                .andEqualTo("authorityOwner", userId)
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
                        .andEqualTo("authorityPrefix", BaseConstants.AUTHORITY_PREFIX_ROLE)
                        .andEqualTo("resourceType", resourceType)
                        .andIn("authorityOwner", roleIds)
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
    public List<SystemGrantAccess> getUserPrivateGrantAccess(Long userId) {
        List<SystemGrantAccess> permissions = Lists.newArrayList();
        // 系统用户私有权限
        ExampleBuilder builder = new ExampleBuilder(SystemGrantAccess.class);
        Example example = builder.criteria()
                .andEqualTo("authorityPrefix", BaseConstants.AUTHORITY_PREFIX_USER)
                .andEqualTo("authorityOwner", userId)
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
    public List<SystemGrantAccess> getGrantAccessList() {
        ExampleBuilder builder = new ExampleBuilder(SystemGrantAccess.class);
        Example example = builder.criteria()
                .andEqualTo("status", BaseConstants.ENABLED)
                .end().build();
        return systemAccessMapper.selectByExample(example);
    }

    /**
     * 添加授权
     *
     * @param authorityOwner  权限所有者ID
     * @param authorityPrefix 所有者类型
     * @param resourceType    资源类型:
     * @param resourceIds
     * @return
     */
    @Override
    public Boolean addGrantAccess(String authorityOwner, String authorityPrefix, String resourceType, Long... resourceIds) {
        if (!AUTH_PREFIX_LIST.contains(authorityPrefix)) {
            throw new OpenMessageException(String.format("%s授权类型暂不支持!", authorityPrefix));
        }
        CrudMapper crudMapper = getMapper(resourceType);
        if (crudMapper == null) {
            throw new OpenMessageException(String.format("%s资源类型暂不支持!", resourceType));
        }
        List<SystemGrantAccess> permissions = Lists.newArrayList();
        for (Long resource : resourceIds) {
            Object object = crudMapper.selectByPrimaryKey(resource);
            SystemGrantAccess permission = buildGrantAccess(resourceType, authorityPrefix, authorityOwner, object);
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
                .andEqualTo("authorityPrefix", authorityPrefix)
                .andEqualTo("authorityOwner", authorityOwner)
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
    public Boolean updateGrantAccess(String resourceType, Long resourceId) {
        // 判断资源类型
        CrudMapper crudMapper = getMapper(resourceType);
        if (crudMapper == null) {
            return false;
        }
        Object object = crudMapper.selectByPrimaryKey(resourceId);
        if (object != null) {
            SystemGrantAccess grantAccess = buildGrantAccess(resourceType, null, null, object);
            if (grantAccess != null) {
                ExampleBuilder builder = new ExampleBuilder(SystemGrantAccess.class);
                Example example = builder.criteria().andEqualTo("resourceId", resourceId)
                        .andEqualTo("resourceType", resourceType).end().build();
                SystemGrantAccess updateObj = new SystemGrantAccess();
                updateObj.setStatus(grantAccess.getStatus());
                updateObj.setServiceId(grantAccess.getServiceId());
                updateObj.setResourcePid(grantAccess.getResourcePid());
                updateObj.setPath(grantAccess.getPath());
                updateObj.setResourceInfo(grantAccess.getResourceInfo());
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
     * @param resourceType    资源类型
     * @param authorityPrefix 授权所有者类型
     * @param authorityOwner  授权权限所有者ID
     * @param object          资源对象
     * @return
     */
    protected SystemGrantAccess buildGrantAccess(String resourceType, String authorityPrefix, String authorityOwner, Object object) {
        if (object == null) {
            return null;
        }
        String path = null;
        String code = "";
        Long resourceId = null;
        Long resourcePid = null;
        String serviceId = "";
        String authority = "";
        Integer status = 0;
        SystemGrantAccess grantAccess = null;
        if (object instanceof SystemMenu) {
            SystemMenu menu = (SystemMenu) object;
            path = menu.getPath();
            resourceId = menu.getMenuId();
            resourcePid = menu.getParentId();
            serviceId = DEFAULT_SERVICE_ID;
            status = menu.getStatus();
            code = menu.getMenuCode();
        }
        if (object instanceof SystemAction) {
            SystemAction action = (SystemAction) object;
            path = action.getPath();
            resourceId = action.getActionId();
            resourcePid = action.getMenuId();
            serviceId = DEFAULT_SERVICE_ID;
            status = action.getStatus();
            code = action.getActionCode();
        }
        if (object instanceof SystemApi) {
            SystemApi api = (SystemApi) object;
            path = api.getPath();
            resourceId = api.getApiId();
            resourcePid = 0L;
            serviceId = api.getServiceId();
            status = api.getStatus();
            code = api.getApiCode();
        }
        if (object != null) {
            if (authorityPrefix != null) {
                if (BaseConstants.AUTHORITY_PREFIX_ROLE.equals(authorityPrefix)) {
                    SystemRole role = systemRoleService.getRole(Long.parseLong(authorityOwner));
                    // 角色授权标识=ROLE_角色编码
                    authority = authorityPrefix + role.getRoleCode();
                } else if (BaseConstants.AUTHORITY_PREFIX_USER.equals(authorityPrefix)) {
                    // 个人授权特殊标识=USER_用户ID_资源类型_资源编码 => USER_1_MENU_LIST
                    authority = authorityPrefix + authorityOwner + BaseConstants.AUTHORITY_SEPARATOR + resourceType + BaseConstants.AUTHORITY_SEPARATOR + resourceId;
                } else {
                    // 默认权限标识:前缀+资源编码
                    authority = authorityPrefix + code;
                }
            }
            grantAccess = new SystemGrantAccess();
            grantAccess.setServiceId(serviceId);
            grantAccess.setResourceId(resourceId);
            grantAccess.setResourcePid(resourcePid);
            if (StringUtils.isNotBlank(path)) {
                // 去掉/
                if (path.startsWith(DEFAULT_PREFIX)) {
                    path = path.substring(1);
                }
            }
            grantAccess.setPath(path);
            grantAccess.setStatus(status);
            grantAccess.setAuthority(authority);
            grantAccess.setAuthorityOwner(authorityOwner);
            grantAccess.setAuthorityPrefix(authorityPrefix);
            grantAccess.setResourceInfo(object);
        }
        return grantAccess;
    }
}
