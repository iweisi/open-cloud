package com.github.lyd.base.producer.controller;

import com.github.lyd.base.client.api.SystemAccountRemoteService;
import com.github.lyd.base.client.dto.SystemAccountDto;
import com.github.lyd.base.producer.service.SystemAccountService;
import com.github.lyd.common.model.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号管理
 *
 * @author liuyadu
 */
@Slf4j
@Api(tags = "账号管理")
@RestController
public class SystemAccountController implements SystemAccountRemoteService {
    @Autowired
    private SystemAccountService systemAccountService;

    /**
     * 获取账号登录信息
     *
     * @param username 登录名
     * @return
     */
    @ApiOperation(value = "获取账号登录信息",notes = "获取账号登录信息,仅限系统内部调用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", required = true, value = "登录名", paramType = "path"),
    })
    @GetMapping("/account/login")
    @Override
    public ResultBody<SystemAccountDto> login(@RequestParam(value = "username") String username) {
        SystemAccountDto account = systemAccountService.login(username);
        return ResultBody.success(account);
    }

}
