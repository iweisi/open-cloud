package com.github.lyd.rbac.client.api;

import com.github.lyd.common.model.ResultBody;
import com.github.lyd.rbac.client.dto.TenantAccountDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liuyadu
 */
public interface TenantAccountRemoteService {
    /**
     * 登录
     *
     * @param username 登录名
     * @return
     */
    @GetMapping("/tenants/account/login")
    ResultBody<TenantAccountDto> login(@RequestParam(value = "username") String username);

    /**
     * 添加登录日志
     *
     * @param tenantId
     * @param ipAddress
     * @param userAgent
     * @return
     */
    @PostMapping("/tenants/account/logs/add")
    ResultBody addLoginLog(@RequestParam(value = "tenantId") Long tenantId, @RequestParam(value = "ipAddress") String ipAddress, @RequestParam(value = "userAgent") String userAgent);

}
