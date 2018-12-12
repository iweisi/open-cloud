package com.github.lyd.auth.producer.controller;

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
@Api(tags = "认证用户")
@RestController
public class UserController {
    /**
     * 获取当前登录用户
     * @return
     */
    @ApiOperation(value = "获取当前登录用户")
    @GetMapping("/me")
    public Object getUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
