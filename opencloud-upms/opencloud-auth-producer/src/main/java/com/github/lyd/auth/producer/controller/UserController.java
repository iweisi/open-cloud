package com.github.lyd.auth.producer.controller;

import com.github.lyd.auth.producer.service.impl.GiteeAuthServiceImpl;
import com.github.lyd.auth.producer.service.impl.QQAuthServiceImpl;
import com.github.lyd.auth.producer.service.impl.WechatAuthServiceImpl;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.security.OpenHelper;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: liuyadu
 * @date: 2018/11/9 15:43
 * @description:
 */
@Api(tags = "已认证用户")
@RestController
public class UserController {
    @Autowired
    private QQAuthServiceImpl qqAuthService;
    @Autowired
    private WechatAuthServiceImpl wechatAuthService;
    @Autowired
    private GiteeAuthServiceImpl giteeAuthService;
    /**
     * 平台登录信息
     * @return
     */
    @ApiOperation(value = "平台登录信息")
    @GetMapping("/user")
    public ResultBody userProfile() {
        return ResultBody.success(OpenHelper.getUserProfile());
    }

    /**
     * 登录配置
     * @return
     */
    @ApiOperation(value = "第三方登录配置")
    @GetMapping("/login/config")
    @ResponseBody
    public ResultBody loginConfig() {
        Map<String, String> map = Maps.newHashMap();
        map.put("qq", qqAuthService.getAuthorizationUrl());
        map.put("wechat", wechatAuthService.getAuthorizationUrl());
        map.put("gitee", giteeAuthService.getAuthorizationUrl());
        return ResultBody.success(map);
    }

}
