package com.github.lyd.rbac.producer.controller;

import com.github.lyd.common.model.ResultBody;
import com.github.lyd.rbac.client.api.TenantAccountRemoteService;
import com.github.lyd.rbac.client.dto.TenantAccountDto;
import com.github.lyd.rbac.producer.service.TenantAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 租户信息
 *
 * @author liuyadu
 */
@RestController
public class TenantAccountController implements TenantAccountRemoteService {
    @Autowired
    private TenantAccountService tenantAccountService;

    /**
     * 登录
     *
     * @param username 登录名
     * @return
     */
    @GetMapping("/tenants/account/login")
    @Override
    public ResultBody<TenantAccountDto> login(@RequestParam(value = "username") String username) {
        return ResultBody.success(tenantAccountService.login(username));
    }

    /**
     * 登录成功
     *
     * @param tenantId
     * @param ipAddress
     * @param userAgent
     * @return
     */
    @PostMapping("/tenants/account/logs/add")
    @Override
    public ResultBody addLoginLog(@RequestParam(value = "tenantId") Long tenantId, @RequestParam(value = "ipAddress") String ipAddress, @RequestParam(value = "userAgent") String userAgent) {
        tenantAccountService.addLoginLog(tenantId, ipAddress, userAgent);
        return ResultBody.success();
    }


}
