package com.github.lyd.auth.producer.controller;

import com.github.lyd.common.model.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: liuyadu
 * @date: 2018/11/9 15:43
 * @description:
 */
@Api(tags = "认证租户")
@RestController
public class UserController {
    /**
     * 获取当前登录租户
     * @return
     */
    @ApiOperation(value = "获取当前登录租户")
    @GetMapping("/principal")
    public ResultBody principal() {
        return ResultBody.success(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
