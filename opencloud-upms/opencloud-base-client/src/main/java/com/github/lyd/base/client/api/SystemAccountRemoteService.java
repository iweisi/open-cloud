package com.github.lyd.base.client.api;

import com.github.lyd.base.client.dto.SystemAccountDto;
import com.github.lyd.common.model.ResultBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liuyadu
 */
public interface SystemAccountRemoteService {
    /**
     * 登录
     *
     * @param username 登录名
     * @return
     */
    @GetMapping("/account/login")
    ResultBody<SystemAccountDto> login(@RequestParam(value = "username") String username);


    /**
     * 注册账号
     * @param account
     * @param password
     * @param accountType
     * @return
     */
    @PostMapping("/account/register")
    ResultBody accountRegister(
            @RequestParam(value = "account") String account,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "accountType")  String accountType
    );
}
