package com.github.lyd.base.client.api;

import com.github.lyd.common.model.ResultBody;
import com.github.lyd.base.client.dto.SystemAccountDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liuyadu
 */
public interface SystemAccountRemoteService {
    /**
     * 登录
     *
     * @param username 登录名
     * @return
     */
    @GetMapping("/account/login")
    ResultBody<SystemAccountDto> login(@RequestParam(value = "username") String username);
}
