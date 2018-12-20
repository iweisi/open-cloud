package com.github.lyd.base.producer.controller;

import com.github.lyd.base.client.api.SystemLoginAccountRemoteService;
import com.github.lyd.base.client.constans.RbacConstans;
import com.github.lyd.base.client.dto.SystemLoginAccountDto;
import com.github.lyd.base.client.entity.SystemAccess;
import com.github.lyd.base.producer.service.SystemAccessService;
import com.github.lyd.base.producer.service.SystemLoginAccountService;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.security.OpenHelper;
import io.swagger.annotations.Api;
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
@Api(tags = "登录用户")
@RestController
public class SystemLoginController implements SystemLoginAccountRemoteService {
    @Autowired
    private SystemLoginAccountService systemLoginAccountService;
    @Autowired
    private SystemAccessService permissionService;

    /**
     * 登录
     *
     * @param username 登录名
     * @return
     */
    @GetMapping("/users/login")
    @Override
    public ResultBody<SystemLoginAccountDto> login(@RequestParam(value = "username") String username) {
        return ResultBody.success(systemLoginAccountService.login(username));
    }

    /**
     * 添加登录日志
     *
     * @param userId
     * @param ipAddress
     * @param userAgent
     * @return
     */
    @PostMapping("/users/logs/add")
    @Override
    public ResultBody addLoginLog(@RequestParam(value = "userId") Long userId, @RequestParam(value = "ipAddress") String ipAddress, @RequestParam(value = "userAgent") String userAgent) {
        systemLoginAccountService.addLoginLog(userId, ipAddress, userAgent);
        return ResultBody.success();
    }


    @ApiOperation(value = "当前用户可访问菜单资源")
    @GetMapping("/users/current/menus")
    public ResultBody<List<SystemAccess>> userMenus() {
        return ResultBody.success(permissionService.getUserAccess(OpenHelper.getPrincipal().getUserId(), RbacConstans.RESOURCE_TYPE_MENU));
    }

    @ApiOperation(value = "当前用户可访问操作资源")
    @GetMapping("/users/current/actions")
    public ResultBody<List<SystemAccess>> userActions() {
        return ResultBody.success(permissionService.getUserAccess(OpenHelper.getPrincipal().getUserId(), RbacConstans.RESOURCE_TYPE_ACTION));
    }

    @ApiOperation(value = "当前用户可访问API资源")
    @GetMapping("/users/current/apis")
    public ResultBody<List<SystemAccess>> userApis() {
        return ResultBody.success(permissionService.getUserAccess(OpenHelper.getPrincipal().getUserId(), RbacConstans.RESOURCE_TYPE_API));
    }


}
