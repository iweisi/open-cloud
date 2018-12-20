package com.github.lyd.base.producer.controller;

import com.github.lyd.base.producer.service.SystemUserService;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.base.client.api.SystemUserRemoteService;
import com.github.lyd.base.client.dto.SystemUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/users")
    @Override
    public ResultBody<PageList<SystemUserDto>> users(
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
     * @param state
     * @return
     */
    @ApiOperation(value = "添加系统用户")
    @PostMapping("/users/add")
    @Override
    public ResultBody<SystemUserDto> addUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "state") Integer state
    ) {
        return null;
    }

    /**
     * 更新系统用户
     *
     * @param username
     * @param password
     * @param state
     * @return
     */
    @ApiOperation(value = "更新系统用户")
    @PostMapping("/users/update")
    @Override
    public ResultBody<SystemUserDto> updateUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "state") Integer state
    ) {
        return null;
    }


}
