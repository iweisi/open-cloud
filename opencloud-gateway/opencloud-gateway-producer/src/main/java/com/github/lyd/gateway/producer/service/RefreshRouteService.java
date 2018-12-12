package com.github.lyd.gateway.producer.service;

import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 动态路由刷新服务
 * @author liuyadu
 */
@Component
public class RefreshRouteService {
   private ApplicationEventPublisher publisher;

    private  RouteLocator routeLocator;

    public RefreshRouteService(ApplicationEventPublisher publisher, RouteLocator routeLocator) {
        this.publisher = publisher;
        this.routeLocator = routeLocator;
    }

    public void refreshRoute() {
        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
        publisher.publishEvent(routesRefreshedEvent);
    }
}