package com.github.lyd.base.producer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.lyd.base.producer.service.SystemAppService;
import com.github.lyd.common.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class SystemAppServiceImplTest extends BaseTest {
    @Autowired
    private SystemAppService systemAppService;

    @Test
    public void getAppInfo() {
        System.out.println(JSONObject.toJSONString(systemAppService.getAppInfo("gateway")));
    }
}
