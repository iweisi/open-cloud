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
@Api(tags = "认证系统用户")
@RestController
public class UserController {
    /**
     * 获取当前登录系统用户
     * @return
     */
    @ApiOperation(value = "获取当前登录系统用户")
    @GetMapping("/user")
    public ResultBody user() {
        return ResultBody.success(OpenHelper.getPrincipal());
    }
}
