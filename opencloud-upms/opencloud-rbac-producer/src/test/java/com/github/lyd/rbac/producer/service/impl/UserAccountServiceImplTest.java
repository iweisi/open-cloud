package com.github.lyd.rbac.producer.service.impl;

import com.github.lyd.common.test.BaseTest;
import com.github.lyd.rbac.client.dto.TenantProfileDto;
import com.github.lyd.rbac.producer.service.TenantAccountService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAccountServiceImplTest extends BaseTest {
    @Autowired
    private TenantAccountService accountService;
    @Test
    public void register() {
        TenantProfileDto profile = new TenantProfileDto();
        profile.setUserName("admin");
        profile.setPassword("123456");
        profile.setEmail("515608851@qq.com");
        profile.setMobile("18518226890");
        accountService.register(profile);
    }
}