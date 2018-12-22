package com.github.lyd.base.producer.controller;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.base.client.api.SystemRoleRemoteService;
import com.github.lyd.base.client.entity.SystemRole;
import com.github.lyd.base.producer.service.SystemRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuyadu
 */
@Api(tags = "角色管理")
@RestController
public class SystemRoleController implements SystemRoleRemoteService {
    @Autowired
    private SystemRoleService roleService;

    /**
     * 角色列表
     *
     * @return
     */
    @ApiOperation(value = "角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "显示条数:最大999", paramType = "form"),
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/role")
    @Override
    public ResultBody<PageList<SystemRole>> role(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        return ResultBody.success(roleService.findListPage(new PageParams(page, limit), keyword));
    }

    /**
     * 获取角色信息
     *
     * @param roleId
     * @return
     */
    @ApiOperation(value = "获取角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = true, paramType = "path")
    })
    @GetMapping("/role/{roleId}")
    @Override
    public ResultBody<SystemRole> getRole(@PathVariable(value = "roleId") Long roleId) {
        SystemRole result = roleService.getRole(roleId);
        return ResultBody.success(result);
    }

    /**
     * 添加角色
     *
     * @param roleCode    角色编码
     * @param roleName    角色显示名称
     * @param roleDesc 描述
     * @param status      启用禁用
     * @return
     */
    @ApiOperation(value = "添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleCode", value = "角色编码", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "roleName", value = "角色显示名称", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "roleDesc", value = "描述", defaultValue = "", required = false, paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, defaultValue = "1", allowableValues = "0,1", value = "是否启用", paramType = "form")
    })
    @PostMapping("/role/add")
    @Override
    public ResultBody<Boolean> addRole(
            @RequestParam(value = "roleCode") String roleCode,
            @RequestParam(value = "roleName") String roleName,
            @RequestParam(value = "roleDesc", required = false) String roleDesc,
            @RequestParam(value = "status",defaultValue = "1", required = false) Integer status
    ) {
        SystemRole role = new SystemRole();
        role.setRoleCode(roleCode);
        role.setRoleName(roleName);
        role.setStatus(status);
        role.setRoleDesc(roleDesc);
        boolean result = roleService.addRole(role);
        return ResultBody.success(result);
    }

    /**
     * 更新角色
     *
     * @param roleId      角色ID
     * @param roleCode    角色编码
     * @param roleName    角色显示名称
     * @param roleDesc 描述
     * @param status      启用禁用
     * @return
     */
    @ApiOperation(value = "更新角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "roleCode", value = "角色编码", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "roleName", value = "角色显示名称", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "roleDesc", value = "描述", defaultValue = "", required = false, paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, defaultValue = "1", allowableValues = "0,1", value = "是否启用", paramType = "form")
    })
    @PostMapping("/role/update")
    @Override
    public ResultBody<Boolean> updateRole(
            @RequestParam(value = "roleId") Long roleId,
            @RequestParam(value = "roleCode") String roleCode,
            @RequestParam(value = "roleName") String roleName,
            @RequestParam(value = "roleDesc", required = false) String roleDesc,
            @RequestParam(value = "status",defaultValue = "1", required = false) Integer status
    ) {
        SystemRole role = new SystemRole();
        role.setRoleId(roleId);
        role.setRoleCode(roleCode);
        role.setRoleName(roleName);
        role.setStatus(status);
        role.setRoleDesc(roleDesc);
        boolean result = roleService.updateRole(role);
        return ResultBody.success(result);
    }


    /**
     * 更新状态
     *
     * @param roleId 菜单ID
     * @return
     */
    @ApiOperation(value = "更新状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", required = true, value = "roleId", paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, defaultValue = "1", allowableValues = "0,1", value = "是否启用", paramType = "form")
    })
    @PostMapping("/role/update/status")
    @Override
    public ResultBody<Boolean> updateStatus(
            @RequestParam("roleId") Long roleId,
            @RequestParam(value = "status", defaultValue = "1") Integer status
    ) {
        return ResultBody.success(roleService.updateStatus(roleId, status));
    }

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return
     */
    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = true, paramType = "form")
    })
    @PostMapping("/role/remove")
    @Override
    public ResultBody<Boolean> removeRole(
            @RequestParam(value = "roleId") Long roleId
    ) {
        boolean result = roleService.removeRole(roleId);
        return ResultBody.success(result);
    }
}
