package com.github.lyd.rbac.producer.controller;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.rbac.client.api.UserProfileRemoteService;
import com.github.lyd.rbac.client.dto.UserProfileDto;
import com.github.lyd.rbac.producer.service.UserProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息
 *
 * @author liuyadu
 */
@Api(tags = "用户管理")
@RestController
public class UserProfileController implements UserProfileRemoteService {
    @Autowired
    private UserProfileService userService;

    /**
     * 用户列表
     *
     * @return
     */
    @ApiOperation(value = "用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "显示条数:最大999", paramType = "form"),
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/users")
    @Override
    public ResultBody<PageList<UserProfileDto>> users(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        return ResultBody.success(userService.findListPage(new PageParams(page, limit), keyword));
    }

    /**
     * 添加用户
     *
     * @param username
     * @param password
     * @param state
     * @return
     */
    @ApiOperation(value = "添加用户")
    @PostMapping("/users/add")
    @Override
    public ResultBody<UserProfileDto> addUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "state") Integer state
    ) {
        return null;
    }

    /**
     * 更新用户
     *
     * @param username
     * @param password
     * @param state
     * @return
     */
    @ApiOperation(value = "更新用户")
    @PostMapping("/users/update")
    @Override
    public ResultBody<UserProfileDto> updateUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "state") Integer state
    ) {
        return null;
    }


}
