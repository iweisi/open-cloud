package com.github.lyd.sys.producer.service.impl;

import com.github.lyd.common.test.BaseTest;
import com.github.lyd.sys.client.dto.SystemUserDto;
import com.github.lyd.sys.producer.service.SystemLoginAccountService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAccountServiceImplTest extends BaseTest {
    @Autowired
    private SystemLoginAccountService systemLoginAccountService;
    @Test
    public void register() {
        SystemUserDto user = new SystemUserDto();
        user.setUserName("admin");
        user.setPassword("123456");
        user.setEmail("515608851@qq.com");
        user.setMobile("18518226890");
        systemLoginAccountService.register(user);
    }
}