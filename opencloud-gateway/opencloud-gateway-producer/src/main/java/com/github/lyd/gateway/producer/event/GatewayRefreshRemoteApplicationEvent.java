package com.github.lyd.gateway.producer.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 自定义网关刷新远程事件
 * @author liuyadu
 */
public class GatewayRefreshRemoteApplicationEvent extends RemoteApplicationEvent {

    private GatewayRefreshRemoteApplicationEvent() {
    }

    public GatewayRefreshRemoteApplicationEvent(Object source, String originService, String destinationService) {
        super(source, originService, destinationService);
    }
}
