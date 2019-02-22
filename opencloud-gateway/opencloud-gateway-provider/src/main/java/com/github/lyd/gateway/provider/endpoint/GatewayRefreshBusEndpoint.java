package com.github.lyd.gateway.provider.endpoint;

import com.github.lyd.common.model.ResultBody;
import com.github.lyd.gateway.provider.event.GatewayRefreshRemoteApplicationEvent;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.cloud.bus.endpoint.AbstractBusEndpoint;
import org.springframework.context.ApplicationEventPublisher;

/**
 * 自定义网关刷新端点
 * 支持重新加载路由配置、限流配置、签名白名单配置
 * @author liuyadu
 */
@Endpoint(
        id = "refresh-gateway"
)
public class GatewayRefreshBusEndpoint extends AbstractBusEndpoint {

    public GatewayRefreshBusEndpoint(ApplicationEventPublisher context, String id) {
        super(context, id);
    }

    /**
     * 支持灰度发布
     * /actuator/refresh-gateway?destination = customers：**
     *
     * @param destination
     */
    @WriteOperation
    public ResultBody busRefreshWithDestination(@Selector String destination) {
        this.publish(new GatewayRefreshRemoteApplicationEvent(this, this.getInstanceId(), destination));
        return ResultBody.success("刷新成功",null);
    }

    /**
     * 刷新所有
     */
    @WriteOperation
    public ResultBody busRefresh() {
        this.publish(new GatewayRefreshRemoteApplicationEvent(this, this.getInstanceId(), (String) null));
        return ResultBody.success("刷新成功",null);
    }
}
