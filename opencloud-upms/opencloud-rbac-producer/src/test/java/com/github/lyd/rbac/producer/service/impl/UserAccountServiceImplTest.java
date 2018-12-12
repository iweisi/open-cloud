package com.github.lyd.rbac.producer.service.impl;

import com.github.lyd.common.test.BaseTest;
import com.github.lyd.rbac.client.dto.UserProfileDto;
import com.github.lyd.rbac.producer.service.UserProfileService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAccountServiceImplTest extends BaseTest {
    @Autowired
    private UserProfileService userProfileService;
    @Test
    public void register() {
        UserProfileDto profile = new UserProfileDto();
        profile.setUserName("admin");
        profile.setPassword("123456");
        profile.setEmail("515608851@qq.com");
        profile.setMobile("18518226890");
        userProfileService.addUser(profile);
    }
}