package com.github.lyd.base.producer.service.impl;

import com.github.lyd.base.producer.service.SystemAccountService;
import com.github.lyd.common.test.BaseTest;
import com.github.lyd.base.client.dto.SystemUserDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAccountServiceImplTest extends BaseTest {
    @Autowired
    private SystemAccountService systemAccountService;
    @Test
    public void register() {
        SystemUserDto user = new SystemUserDto();
        user.setUserName("admin");
        user.setPassword("123456");
        user.setEmail("515608851@qq.com");
        user.setMobile("18518226890");
        systemAccountService.register(user);
    }
}