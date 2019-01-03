package com.github.lyd.gateway.client.api;

import com.github.lyd.base.client.entity.SystemGatewayRateLimit;
import com.github.lyd.base.client.entity.SystemGatewayRoute;
import com.github.lyd.base.client.entity.SystemGrantAccess;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.ResultBody;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: liuyadu
 * @date: 2019/1/3 11:18
 * @description:
 */
public interface ApiGatewayRemoteService {

    /**
     * 获取网关缓存的访问限制列表
     *
     * @return
     */
    @GetMapping(value = "/access/cache")
    ResultBody<PageList<SystemGrantAccess>> accessCache();

    /**
     * 获取网关缓存的限流列表
     *
     * @return
     */
    @GetMapping(value = "/limit/cache")
    ResultBody<PageList<SystemGatewayRateLimit>> limitCache();

    /**
     * 获取网关缓存的路由列表
     *
     * @return
     */
    @GetMapping(value = "/route/cache")
    ResultBody<PageList<SystemGatewayRoute>> routeCache();
}
