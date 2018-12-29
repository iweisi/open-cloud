package com.github.lyd.base.producer.controller;

import com.github.lyd.base.client.api.SystemAccountRemoteService;
import com.github.lyd.base.client.dto.SystemAccountDto;
import com.github.lyd.base.client.entity.SystemAccountLogs;
import com.github.lyd.base.producer.service.SystemAccountService;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.utils.WebUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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
        if (account != null) {
            try {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //添加登录日志
                SystemAccountLogs log = new SystemAccountLogs();
                log.setUserId(account.getUserId());
                log.setAccount(account.getAccount());
                log.setAccountType(account.getAccountType());
                log.setLoginIp(WebUtils.getIpAddr(request));
                log.setLoginAgent(request.getHeader("User-Agent"));
                systemAccountService.addLoginLog(log);
            } catch (Exception e) {
                log.error("添加登录日志失败");
            }
        }
        return ResultBody.success(account);
    }

}
