package com.github.lyd.base.producer.controller;

import com.github.lyd.base.client.api.SystemRoleRemoteService;
import com.github.lyd.base.client.constants.BaseConstants;
import com.github.lyd.base.client.entity.SystemRole;
import com.github.lyd.base.producer.service.SystemGrantAccessService;
import com.github.lyd.base.producer.service.SystemRoleService;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.utils.StringUtils;
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
    private SystemRoleService systemRoleService;
    @Autowired
    private SystemGrantAccessService systemGrantAccessService;
    /**
     * 获取角色分页列表
     *
     * @return
     */
    @ApiOperation(value = "获取角色分页列表",notes = "获取角色分页列表")
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
        return ResultBody.success(systemRoleService.findListPage(new PageParams(page, limit), keyword));
    }

    /**
     * 获取角色信息
     *
     * @param roleId
     * @return
     */
    @ApiOperation(value = "获取角色信息",notes = "获取角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = true, paramType = "path")
    })
    @GetMapping("/role/{roleId}")
    @Override
    public ResultBody<SystemRole> getRole(@PathVariable(value = "roleId") Long roleId) {
        SystemRole result = systemRoleService.getRole(roleId);
        return ResultBody.success(result);
    }

    /**
     * 添加角色
     *
     * @param roleCode 角色编码
     * @param roleName 角色显示名称
     * @param roleDesc 描述
     * @param status   启用禁用
     * @return
     */
    @ApiOperation(value = "添加角色",notes = "添加角色")
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
            @RequestParam(value = "status", defaultValue = "1", required = false) Integer status
    ) {
        SystemRole role = new SystemRole();
        role.setRoleCode(roleCode);
        role.setRoleName(roleName);
        role.setStatus(status);
        role.setRoleDesc(roleDesc);
        boolean result = systemRoleService.addRole(role);
        return result ? ResultBody.success() : ResultBody.failed();
    }

    /**
     * 编辑角色
     *
     * @param roleId   角色ID
     * @param roleCode 角色编码
     * @param roleName 角色显示名称
     * @param roleDesc 描述
     * @param status   启用禁用
     * @return
     */
    @ApiOperation(value = "编辑角色",notes = "编辑角色")
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
            @RequestParam(value = "status", defaultValue = "1", required = false) Integer status
    ) {
        SystemRole role = new SystemRole();
        role.setRoleId(roleId);
        role.setRoleCode(roleCode);
        role.setRoleName(roleName);
        role.setStatus(status);
        role.setRoleDesc(roleDesc);
        boolean result = systemRoleService.updateRole(role);
        return result ? ResultBody.success() : ResultBody.failed();
    }


    /**
     * 更新角色状态
     *
     * @param roleId 菜单ID
     * @return
     */
    @ApiOperation(value = "更新角色状态",notes = "更新角色状态")
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
        boolean result = systemRoleService.updateStatus(roleId, status);
        return result ? ResultBody.success() : ResultBody.failed();
    }

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return
     */
    @ApiOperation(value = "删除角色",notes = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = true, paramType = "form")
    })
    @PostMapping("/role/remove")
    @Override
    public ResultBody<Boolean> removeRole(
            @RequestParam(value = "roleId") Long roleId
    ) {
        boolean result = systemRoleService.removeRole(roleId);
        return result ? ResultBody.success() : ResultBody.failed();
    }

    /**
     * 菜单授权
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID.多个以,隔开
     * @return
     */
    @ApiOperation(value = "菜单授权",notes = "菜单授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "menuIds", value = "菜单ID.多个以,隔开", defaultValue = "", required = true, paramType = "form")
    })
    @PostMapping("/role/grant/menu")
    @Override
    public ResultBody<Boolean> roleGrantMenu(
            @RequestParam(value = "roleId") Long roleId,
            @RequestParam("menuIds") String menuIds
    ) {
       String result =  systemGrantAccessService.addGrantAccess(String.valueOf(roleId), BaseConstants.AUTHORITY_PREFIX_ROLE,BaseConstants.RESOURCE_TYPE_MENU, menuIds.split(","));
        return StringUtils.isNotBlank(result)?ResultBody.success().setData(true):ResultBody.failed().setData(false);
    }

    /**
     * 操作授权
     *
     * @param roleId    角色ID
     * @param actionIds 操作ID.多个以,隔开
     * @return
     */
    @ApiOperation(value = "操作授权",notes = "操作授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "actionIds", value = "操作ID.多个以,隔开", defaultValue = "", required = true, paramType = "form")
    })
    @PostMapping("/role/grant/action")
    @Override
    public ResultBody<Boolean> roleGrantAction(
            @RequestParam(value = "roleId") Long roleId,
            @RequestParam("actionIds") String actionIds
    ) {
        String result =  systemGrantAccessService.addGrantAccess(String.valueOf(roleId), BaseConstants.AUTHORITY_PREFIX_ROLE,BaseConstants.RESOURCE_TYPE_MENU, actionIds.split(","));
        return StringUtils.isNotBlank(result)?ResultBody.success().setData(true):ResultBody.failed().setData(false);
    }

    /**
     * 接口授权
     *
     * @param roleId 角色ID
     * @param apiIds 接口ID.多个以,隔开
     * @return
     */
    @ApiOperation(value = "接口授权",notes = "接口授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "apiIds", value = "接口ID.多个以,隔开", defaultValue = "", required = true, paramType = "form")
    })
    @PostMapping("/role/grant/api")
    @Override
    public ResultBody<Boolean> roleGrantApi(
            @RequestParam(value = "roleId") Long roleId,
            @RequestParam("apiIds") String apiIds
    ) {
        String result =  systemGrantAccessService.addGrantAccess(String.valueOf(roleId), BaseConstants.AUTHORITY_PREFIX_ROLE,BaseConstants.RESOURCE_TYPE_MENU, apiIds.split(","));
        return StringUtils.isNotBlank(result)?ResultBody.success().setData(true):ResultBody.failed().setData(false);
    }


}
