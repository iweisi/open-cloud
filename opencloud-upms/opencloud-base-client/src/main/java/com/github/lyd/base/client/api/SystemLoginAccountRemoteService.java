package com.github.lyd.base.client.api;

import com.github.lyd.common.model.ResultBody;
import com.github.lyd.base.client.dto.SystemLoginAccountDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liuyadu
 */
public interface SystemLoginAccountRemoteService {
    /**
     * 登录
     *
     * @param username 登录名
     * @return
     */
    @GetMapping("/users/login")
    ResultBody<SystemLoginAccountDto> login(@RequestParam(value = "username") String username);

    /**
     * 添加登录日志
     *
     * @param userId
     * @param ipAddress
     * @param userAgent
     * @return
     */
    @PostMapping("/users/logs/add")
    ResultBody addLoginLog(@RequestParam(value = "userId") Long userId, @RequestParam(value = "ipAddress") String ipAddress, @RequestParam(value = "userAgent") String userAgent);

}
