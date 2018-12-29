package com.github.lyd.base.producer.controller;

import com.github.lyd.base.client.api.SystemAccountRemoteService;
import com.github.lyd.base.client.dto.SystemAccountDto;
import com.github.lyd.base.client.entity.SystemAccountLogs;
import com.github.lyd.base.producer.service.SystemAccountService;
import com.github.lyd.common.model.ResultBody;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号管理
 *
 * @author liuyadu
 */
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
        return ResultBody.success(systemAccountService.login(username));
    }

    /**
     * 添加登录日志
     *
     * @param userId
     * @param ipAddress
     * @param userAgent
     * @return
     */
    @PostMapping("/account/logs/add")
    @Override
    public ResultBody addLoginLog(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "account") String account,
            @RequestParam(value = "accountType") String accountType,
            @RequestParam(value = "ipAddress") String ipAddress,
            @RequestParam(value = "userAgent") String userAgent) {
        SystemAccountLogs log = new SystemAccountLogs();
        log.setUserId(userId);
        log.setAccount(account);
        log.setAccountType(accountType);
        log.setLoginIp(ipAddress);
        log.setLoginAgent(userAgent);
        systemAccountService.addLoginLog(log);
        return ResultBody.success();
    }

}
