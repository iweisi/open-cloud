package com.github.lyd.rbac.producer.controller;

import com.github.lyd.common.model.ResultBody;
import com.github.lyd.rbac.client.api.UserAccountRemoteService;
import com.github.lyd.rbac.client.dto.UserAccountDto;
import com.github.lyd.rbac.producer.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息
 *
 * @author liuyadu
 */
@RestController
public class UserAccountController implements UserAccountRemoteService {
    @Autowired
    private UserAccountService userAccountService;

    /**
     * 登录
     *
     * @param username 登录名
     * @return
     */
    @GetMapping("/users/account/login")
    @Override
    public ResultBody<UserAccountDto> login(@RequestParam(value = "username") String username) {
        return ResultBody.success(userAccountService.login(username));
    }

    /**
     * 登录成功
     *
     * @param userId
     * @param ipAddress
     * @param userAgent
     * @return
     */
    @PostMapping("/users/account/logs/add")
    @Override
    public ResultBody addLoginLog(@RequestParam(value = "userId") Long userId, @RequestParam(value = "ipAddress") String ipAddress, @RequestParam(value = "userAgent") String userAgent) {
        userAccountService.addLoginLog(userId, ipAddress, userAgent);
        return ResultBody.success();
    }


}
