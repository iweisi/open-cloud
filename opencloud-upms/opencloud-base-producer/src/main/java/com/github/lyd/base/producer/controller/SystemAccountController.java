package com.github.lyd.base.producer.controller;

import com.github.lyd.base.client.api.SystemAccountRemoteService;
import com.github.lyd.base.client.dto.SystemAccountDto;
import com.github.lyd.base.producer.service.SystemAccountService;
import com.github.lyd.common.model.ResultBody;
import io.swagger.annotations.Api;
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
     * 登录
     *
     * @param username 登录名
     * @return
     */
    @GetMapping("/account/login")
    @Override
    public ResultBody<SystemAccountDto> login(@RequestParam(value = "username") String username) {
        SystemAccountDto account = systemAccountService.login(username);
        return ResultBody.success(account);
    }

}
