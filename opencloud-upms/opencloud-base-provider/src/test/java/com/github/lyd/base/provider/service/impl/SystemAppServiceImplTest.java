package com.github.lyd.base.provider.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.lyd.base.provider.service.SystemAppService;
import com.github.lyd.common.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SystemAppServiceImplTest extends BaseTest {
    @Autowired
    private SystemAppService systemAppService;

    @Test
    public void getAppInfo() {
        System.out.println(JSONObject.toJSONString(systemAppService.getAppInfo("gateway")));
    }
}
