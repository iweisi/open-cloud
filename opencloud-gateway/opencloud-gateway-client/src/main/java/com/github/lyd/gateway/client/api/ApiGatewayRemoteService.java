package com.github.lyd.gateway.client.api;

import com.github.lyd.common.model.ResultBody;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: liuyadu
 * @date: 2019/1/3 11:18
 * @description:
 */
public interface ApiGatewayRemoteService {

    /**
     * 获取网关缓存的访问限制
     *
     * @return
     */
    @GetMapping(value = "/access/cache")
    ResultBody accessCache();

}
