package com.github.lyd.base.producer.controller;

import com.github.lyd.base.client.api.SystemUserRemoteService;
import com.github.lyd.base.client.constants.BaseConstants;
import com.github.lyd.base.client.dto.SystemUserDto;
import com.github.lyd.base.client.entity.SystemGrantAccess;
import com.github.lyd.base.client.entity.SystemUser;
import com.github.lyd.base.producer.service.SystemGrantAccessService;
import com.github.lyd.base.producer.service.SystemUserService;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.security.OpenHelper;
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
 * 系统用户信息
 *
 * @author liuyadu
 */
@Api(tags = "系统用户管理")
@RestController
public class SystemUserController implements SystemUserRemoteService {
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private SystemGrantAccessService permissionService;
    /**
     * 系统用户列表
     *
     * @return
     */
    @ApiOperation(value = "系统用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "显示条数:最大999", paramType = "form"),
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/user")
    @Override
    public ResultBody<PageList<SystemUser>> user(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        return ResultBody.success(systemUserService.findListPage(new PageParams(page, limit), keyword));
    }

    /**
     * 添加系统用户
     *
     * @param username
     * @param password
     * @param status
     * @return
     */
    @ApiOperation(value = "添加系统用户")
    @PostMapping("/user/add")
    @Override
    public ResultBody<SystemUserDto> addUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "status") Integer status
    ) {
        return null;
    }

    /**
     * 更新系统用户
     *
     * @param username
     * @param password
     * @param status
     * @return
     */
    @ApiOperation(value = "更新系统用户")
    @PostMapping("/user/update")
    @Override
    public ResultBody<SystemUserDto> updateUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "status") Integer status
    ) {
        return null;
    }

    @ApiOperation(value = "当前用户可访问菜单资源")
    @GetMapping("/user/grant/menus")
    public ResultBody<List<SystemGrantAccess>> userGrantMenus() {
        return ResultBody.success(permissionService.getUserGrantAccessList(OpenHelper.getPrincipal().getUserId(), BaseConstants.RESOURCE_TYPE_MENU));
    }

    @ApiOperation(value = "当前用户可访问操作资源")
    @GetMapping("/user/grant/actions")
    public ResultBody<List<SystemGrantAccess>> userGrantActions() {
        return ResultBody.success(permissionService.getUserGrantAccessList(OpenHelper.getPrincipal().getUserId(), BaseConstants.RESOURCE_TYPE_ACTION));
    }

    @ApiOperation(value = "当前用户可访问API资源")
    @GetMapping("/user/grant/apis")
    public ResultBody<List<SystemGrantAccess>> userGrantApis() {
        return ResultBody.success(permissionService.getUserGrantAccessList(OpenHelper.getPrincipal().getUserId(), BaseConstants.RESOURCE_TYPE_API));
    }
}
