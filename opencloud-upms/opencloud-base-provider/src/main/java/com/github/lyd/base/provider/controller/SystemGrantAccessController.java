package com.github.lyd.base.provider.controller;

import com.github.lyd.base.client.api.SystemGrantAccessRemoteService;
import com.github.lyd.base.client.constants.BaseConstants;
import com.github.lyd.base.client.entity.SystemGrantAccess;
import com.github.lyd.base.provider.service.SystemGrantAccessService;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.security.OpenHelper;
import com.github.lyd.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: liuyadu
 * @date: 2018/11/26 18:20
 * @description:
 */
@Api(tags = "授权管理")
@RestController
public class SystemGrantAccessController implements SystemGrantAccessRemoteService {
    @Autowired
    private SystemGrantAccessService systemAccessService;
    @Autowired
    private SystemGrantAccessService systemGrantAccessService;

    /**
     * 获取已授权访问分页列表
     *
     * @return
     */
    @ApiOperation(value = "获取已授权访问分页列表", notes = "获取已授权访问分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "显示条数:最大999", paramType = "form"),
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/grant/access")
    @Override
    public ResultBody<PageList<SystemGrantAccess>> grantAccess(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        return ResultBody.success(systemAccessService.findListPage(new PageParams(page, limit), keyword));
    }

    /**
     * 获取已授权访问列表
     *
     * @return 应用信息
     */
    @ApiOperation(value = "获取已授权访问列表")
    @GetMapping("/grant/access/list")
    @Override
    public ResultBody<List<SystemGrantAccess>> grantAccessList() {
        return ResultBody.success(systemAccessService.getGrantAccessList());
    }


    /**
     * 角色菜单授权
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID.多个以,隔开
     * @return
     */
    @ApiOperation(value = "角色菜单授权", notes = "角色菜单授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "menuIds", value = "菜单ID.多个以,隔开", defaultValue = "", required = false, paramType = "form")
    })
    @PostMapping("/grant/role/menu")
    @Override
    public ResultBody grantRoleMenu(
            @RequestParam(value = "roleId") Long roleId,
            @RequestParam(value = "menuIds", required = false) String menuIds
    ) {
        systemGrantAccessService.addGrantAccess(String.valueOf(roleId), BaseConstants.AUTHORITY_PREFIX_ROLE, BaseConstants.RESOURCE_TYPE_MENU, StringUtils.isNotBlank(menuIds) ? menuIds.split(",") : new String[]{});
        return ResultBody.success();
    }

    /**
     * 角色操作授权
     *
     * @param roleId    角色ID
     * @param actionIds 操作ID.多个以,隔开
     * @return
     */
    @ApiOperation(value = "角色操作授权", notes = "角色操作授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "actionIds", value = "操作ID.多个以,隔开", defaultValue = "", required = false, paramType = "form")
    })
    @PostMapping("/grant/role/action")
    @Override
    public ResultBody grantRoleAction(
            @RequestParam(value = "roleId") Long roleId,
            @RequestParam(value = "actionIds", required = false) String actionIds
    ) {
        systemGrantAccessService.addGrantAccess(String.valueOf(roleId), BaseConstants.AUTHORITY_PREFIX_ROLE, BaseConstants.RESOURCE_TYPE_ACTION, StringUtils.isNotBlank(actionIds) ? actionIds.split(",") : new String[]{});
        return ResultBody.success();
    }

    /**
     * 角色接口授权
     *
     * @param roleId 角色ID
     * @param apiIds 接口ID.多个以,隔开
     * @return
     */
    @ApiOperation(value = "角色接口授权", notes = "角色接口授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "apiIds", value = "接口ID.多个以,隔开", defaultValue = "", required = false, paramType = "form")
    })
    @PostMapping("/grant/role/api")
    @Override
    public ResultBody grantRoleApi(
            @RequestParam(value = "roleId") Long roleId,
            @RequestParam(value = "apiIds", required = false) String apiIds
    ) {
        systemGrantAccessService.addGrantAccess(String.valueOf(roleId), BaseConstants.AUTHORITY_PREFIX_ROLE, BaseConstants.RESOURCE_TYPE_API, StringUtils.isNotBlank(apiIds) ? apiIds.split(",") : new String[]{});
        return ResultBody.success();
    }

    /**
     * 获取角色已授权菜单资源
     *
     * @param roleId 角色ID
     * @return
     */
    @ApiOperation(value = "获取角色已授权菜单资源", notes = "获取角色已授权菜单资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = true, paramType = "form")
    })
    @PostMapping("/granted/role/menu")
    @Override
    public ResultBody<PageList<SystemGrantAccess>> grantedRoleMenu(Long roleId) {
        PageList<SystemGrantAccess> result = systemGrantAccessService.findList(String.valueOf(roleId), BaseConstants.AUTHORITY_PREFIX_ROLE, BaseConstants.RESOURCE_TYPE_MENU);
        return ResultBody.success(result);
    }

    /**
     * 获取角色已授权操作资源
     *
     * @param roleId 角色ID
     * @return
     */
    @ApiOperation(value = "获取角色已授权操作资源", notes = "获取角色已授权操作资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = true, paramType = "form")
    })
    @PostMapping("/granted/role/action")
    @Override
    public ResultBody<PageList<SystemGrantAccess>> grantedRoleAction(Long roleId) {
        PageList<SystemGrantAccess> result = systemGrantAccessService.findList(String.valueOf(roleId), BaseConstants.AUTHORITY_PREFIX_ROLE, BaseConstants.RESOURCE_TYPE_ACTION);
        return ResultBody.success(result);
    }

    /**
     * 获取角色已授权接口资源
     *
     * @param roleId 角色ID
     * @return
     */
    @ApiOperation(value = "获取角色已授权接口资源", notes = "获取角色已授权接口资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", defaultValue = "", required = true, paramType = "form")
    })
    @PostMapping("/granted/role/api")
    @Override
    public ResultBody<PageList<SystemGrantAccess>> grantedRoleApi(Long roleId) {
        PageList<SystemGrantAccess> result = systemGrantAccessService.findList(String.valueOf(roleId), BaseConstants.AUTHORITY_PREFIX_ROLE, BaseConstants.RESOURCE_TYPE_API);
        return ResultBody.success(result);
    }

    /**
     * 用户菜单授权
     *
     * @param userId  用户ID
     * @param menuIds 菜单ID.多个以,隔开
     * @return
     */
    @ApiOperation(value = "用户菜单授权", notes = "用户菜单授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "用户ID", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "menuIds", value = "菜单ID.多个以,隔开", defaultValue = "", required = false, paramType = "form")
    })
    @PostMapping("/grant/user/menu")
    @Override
    public ResultBody grantUserMenu(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "menuIds", required = false) String menuIds
    ) {
        systemGrantAccessService.addGrantAccess(String.valueOf(userId), BaseConstants.AUTHORITY_PREFIX_USER, BaseConstants.RESOURCE_TYPE_MENU, StringUtils.isNotBlank(menuIds) ? menuIds.split(",") : new String[]{});
        return ResultBody.success();
    }

    /**
     * 用户操作授权
     *
     * @param userId    用户ID
     * @param actionIds 操作ID.多个以,隔开
     * @return
     */
    @ApiOperation(value = "用户操作授权", notes = "用户操作授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "用户ID", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "actionIds", value = "操作ID.多个以,隔开", defaultValue = "", required = false, paramType = "form")
    })
    @PostMapping("/grant/user/action")
    @Override
    public ResultBody grantUserAction(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "actionIds", required = false) String actionIds
    ) {
        systemGrantAccessService.addGrantAccess(String.valueOf(userId), BaseConstants.AUTHORITY_PREFIX_USER, BaseConstants.RESOURCE_TYPE_ACTION, StringUtils.isNotBlank(actionIds) ? actionIds.split(",") : new String[]{});
        return ResultBody.success();
    }

    /**
     * 用户接口授权
     *
     * @param userId 用户ID
     * @param apiIds 接口ID.多个以,隔开
     * @return
     */
    @ApiOperation(value = "用户接口授权", notes = "用户接口授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "用户ID", defaultValue = "", required = true, paramType = "form"),
            @ApiImplicitParam(name = "apiIds", value = "操作ID.多个以,隔开", defaultValue = "", required = false, paramType = "form")
    })
    @PostMapping("/grant/user/api")
    @Override
    public ResultBody grantUserApi(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "apiIds", required = false) String apiIds
    ) {
        systemGrantAccessService.addGrantAccess(String.valueOf(userId), BaseConstants.AUTHORITY_PREFIX_USER, BaseConstants.RESOURCE_TYPE_API, StringUtils.isNotBlank(apiIds) ? apiIds.split(",") : new String[]{});
        return ResultBody.success();
    }

    /**
     * 获取用户已授权菜单资源
     *
     * @param userId 用户ID
     * @return
     */
    @ApiOperation(value = "获取用户已授权菜单资源", notes = "获取用户已授权菜单资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", defaultValue = "", required = true, paramType = "form")
    })
    @PostMapping("/granted/user/menu")
    @Override
    public ResultBody<PageList<SystemGrantAccess>> grantedUserMenu(
            @RequestParam(value = "userId") Long userId
    ) {
        PageList<SystemGrantAccess> result = systemGrantAccessService.findList(String.valueOf(userId), BaseConstants.AUTHORITY_PREFIX_USER, BaseConstants.RESOURCE_TYPE_MENU);
        return ResultBody.success(result);
    }

    /**
     * 获取用户已授权操作资源
     *
     * @param userId 用户ID
     * @return
     */
    @ApiOperation(value = "获取用户已授权操作资源", notes = "获取用户已授权操作资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", defaultValue = "", required = true, paramType = "form")
    })
    @PostMapping("/granted/user/action")
    @Override
    public ResultBody<PageList<SystemGrantAccess>> grantedUserAction(
            @RequestParam(value = "userId") Long userId
    ) {
        PageList<SystemGrantAccess> result = systemGrantAccessService.findList(String.valueOf(userId), BaseConstants.AUTHORITY_PREFIX_USER, BaseConstants.RESOURCE_TYPE_ACTION);
        return ResultBody.success(result);
    }

    /**
     * 获取用户已授权接口资源
     *
     * @param userId 角色ID
     * @return
     */
    @ApiOperation(value = "获取用户已授权接口资源", notes = "获取用户已授权接口资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", defaultValue = "", required = true, paramType = "form")
    })
    @PostMapping("/granted/user/api")
    @Override
    public ResultBody<PageList<SystemGrantAccess>> grantedUserApi(
            @RequestParam(value = "userId") Long userId
    ) {
        PageList<SystemGrantAccess> result = systemGrantAccessService.findList(String.valueOf(userId), BaseConstants.AUTHORITY_PREFIX_USER, BaseConstants.RESOURCE_TYPE_API);
        return ResultBody.success(result);
    }

    /**
     * 获取当前登录用户可访问菜单资源
     *
     * @return
     */
    @ApiOperation(value = "获取当前登录用户可访问菜单资源", notes = "获取当前登录用户可访问菜单资源")
    @GetMapping("/grant/login/menus")
    public ResultBody<List<SystemGrantAccess>> grantLoginMenus() {
        return ResultBody.success(systemGrantAccessService.getUserGrantAccessList(OpenHelper.getUserAuth().getUserId(), BaseConstants.RESOURCE_TYPE_MENU));
    }

    /**
     * 获取当前登录用户可访问操作资源
     *
     * @return
     */
    @ApiOperation(value = "获取当前登录用户可访问操作资源", notes = "获取当前登录用户可访问操作资源")
    @GetMapping("/grant/login/actions")
    public ResultBody<List<SystemGrantAccess>> grantLoginActions() {
        return ResultBody.success(systemGrantAccessService.getUserGrantAccessList(OpenHelper.getUserAuth().getUserId(), BaseConstants.RESOURCE_TYPE_ACTION));
    }

    /**
     * 获取当前登录用户可访问接口资源
     *
     * @return
     */
    @ApiOperation(value = "获取当前登录用户可访问接口资源", notes = "获取当前登录用户可访问接口资源")
    @GetMapping("/grant/login/apis")
    public ResultBody<List<SystemGrantAccess>> grantLoginApis() {
        return ResultBody.success(systemGrantAccessService.getUserGrantAccessList(OpenHelper.getUserAuth().getUserId(), BaseConstants.RESOURCE_TYPE_API));
    }
}
