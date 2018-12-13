package com.github.lyd.rbac.producer.controller;

import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.security.OpenHelper;
import com.github.lyd.rbac.client.api.PermissionRemoteService;
import com.github.lyd.rbac.client.constans.RbacConstans;
import com.github.lyd.rbac.client.entity.ResourcePermission;
import com.github.lyd.rbac.producer.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: liuyadu
 * @date: 2018/11/26 18:20
 * @description:
 */
@Api(tags = "授权管理")
@RestController
public class PermissionsController implements PermissionRemoteService {
    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "登录租户菜单权限")
    @GetMapping("/permissions/tenant/menus")
    public ResultBody<List<ResourcePermission>> tenantMenus() {
        return ResultBody.success(permissionService.getTenantPermission(OpenHelper.getPrincipal().getTenantId(), RbacConstans.RESOURCE_TYPE_MENU));
    }

    @ApiOperation(value = "登录租户操作权限")
    @GetMapping("/permissions/tenant/actions")
    public ResultBody<List<ResourcePermission>> tenantActions() {
        return ResultBody.success(permissionService.getTenantPermission(OpenHelper.getPrincipal().getTenantId(), RbacConstans.RESOURCE_TYPE_ACTION));
    }

    @ApiOperation(value = "登录租户API权限")
    @GetMapping("/permissions/tenant/apis")
    public ResultBody<List<ResourcePermission>> tenantApis() {
        return ResultBody.success(permissionService.getTenantPermission(OpenHelper.getPrincipal().getTenantId(), RbacConstans.RESOURCE_TYPE_API));
    }

    /**
     * 获取授权列表
     *
     * @return 应用信息
     */
    @ApiOperation(value = "获取授权列表")
    @GetMapping("/permissions")
    @Override
    public ResultBody<List<ResourcePermission>> permissions() {
        return ResultBody.success(permissionService.getPermissionList());
    }
}
