package com.github.lyd.gateway.producer.event;

import com.github.lyd.gateway.producer.locator.PermissionLocator;
import com.github.lyd.gateway.producer.locator.RateLimitLocator;
import com.github.lyd.gateway.producer.locator.ZuulRoutesLocator;
import org.springframework.context.ApplicationListener;

/**
 * 自定义动态限流刷新事件监听
 *
 * @author liuyadu
 */
public class GatewayRefreshRemoteListener implements ApplicationListener<GatewayRefreshRemoteApplicationEvent> {
    /**
     * 限流加载器
     */
    private RateLimitLocator rateLimitLocator;
    /**
     * 路由加载器
     */
    private ZuulRoutesLocator routesLocator;

    /**
     * 权限加载器
     */
    private PermissionLocator permissionLocator;

    public GatewayRefreshRemoteListener(RateLimitLocator rateLimitLocator, ZuulRoutesLocator routesLocator,PermissionLocator permissionLocator) {
        this.rateLimitLocator = rateLimitLocator;
        this.routesLocator = routesLocator;
        this.permissionLocator = permissionLocator;
    }

    public RateLimitLocator getRateLimitLocator() {
        return rateLimitLocator;
    }

    public void setRateLimitLocator(RateLimitLocator rateLimitLocator) {
        this.rateLimitLocator = rateLimitLocator;
    }

    public ZuulRoutesLocator getRoutesLocator() {
        return routesLocator;
    }

    public void setRoutesLocator(ZuulRoutesLocator routesLocator) {
        this.routesLocator = routesLocator;
    }

    @Override
    public void onApplicationEvent(GatewayRefreshRemoteApplicationEvent event) {
        //重载路由
        routesLocator.doRefresh();
        //重载限流
        rateLimitLocator.doRefresh();
        //重载权限
        permissionLocator.doRefresh();
    }
}