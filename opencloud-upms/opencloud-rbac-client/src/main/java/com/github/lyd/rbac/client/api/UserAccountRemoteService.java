package com.github.lyd.rbac.client.api;

import com.github.lyd.common.model.ResultBody;
import com.github.lyd.rbac.client.dto.UserAccountDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liuyadu
 */
public interface UserAccountRemoteService {
    /**
     * 登录
     *
     * @param username 登录名
     * @return
     */
    @GetMapping("/users/account/login")
    ResultBody<UserAccountDto> login(@RequestParam(value = "username") String username);

    /**
     * 登录成功
     * @param userId
     * @param ipAddress
     * @param userAgent
     * @return
     */
    @PostMapping("/users/account/logs/add")
    ResultBody addLoginLog(@RequestParam(value = "userId") Long userId, @RequestParam(value = "ipAddress") String ipAddress, @RequestParam(value = "userAgent") String userAgent);

}
