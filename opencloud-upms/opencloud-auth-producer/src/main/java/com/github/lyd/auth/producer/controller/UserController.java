package com.github.lyd.auth.producer.controller;

import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.security.OpenHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: liuyadu
 * @date: 2018/11/9 15:43
 * @description:
 */
@Api(tags = "已认证用户")
@RestController
public class UserController {
    /**
     * 平台登录信息
     * @return
     */
    @ApiOperation(value = "平台登录信息")
    @GetMapping("/user")
    public ResultBody userProfile() {
        return ResultBody.success(OpenHelper.getUserProfile());
    }
}
