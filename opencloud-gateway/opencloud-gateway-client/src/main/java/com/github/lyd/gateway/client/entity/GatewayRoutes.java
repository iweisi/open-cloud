package com.github.lyd.gateway.client.entity;

import com.github.lyd.common.gen.SnowflakeId;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 网关动态路由
 *
 * @author: liuyadu
 * @date: 2018/10/24 16:21
 * @description:
 */
@Table(name = "platform_gateway_routes")
public class GatewayRoutes implements Serializable {
    private static final long serialVersionUID = -2952097064941740301L;

    @Id
    @KeySql(genId = SnowflakeId.class)
    private Long id;
    /**
     * 路由ID
     */
    @Column(name = "route_id")
    private String routeId;
    /**
     * 路径
     */
    private String path;

    /**
     * 服务ID
     */
    @Column(name = "service_id")
    private String serviceId;

    /**
     * 完整地址
     */
    private String url;

    /**
     * 忽略前缀
     */
    @Column(name = "strip_prefix")
    private Boolean stripPrefix;

    /**
     * 0-不重试 1-重试
     */
    private Boolean retryable;

    /**
     * 0-禁用 1-启用
     */
    private Boolean enabled;

    /**
     * 获取路由ID
     *
     * @return id - 路由ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置路由ID
     *
     * @param id 路由ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取路径
     *
     * @return path - 路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置路径
     *
     * @param path 路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取服务ID
     *
     * @return service_id - 服务ID
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * 设置服务ID
     *
     * @param serviceId 服务ID
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * 获取完整地址
     *
     * @return url - 完整地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置完整地址
     *
     * @param url 完整地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取忽略前缀
     *
     * @return strip_prefix - 忽略前缀
     */
    public Boolean getStripPrefix() {
        return stripPrefix;
    }

    /**
     * 设置忽略前缀
     *
     * @param stripPrefix 忽略前缀
     */
    public void setStripPrefix(Boolean stripPrefix) {
        this.stripPrefix = stripPrefix;
    }

    /**
     * 获取0-不重试 1-重试
     *
     * @return retryable - 0-不重试 1-重试
     */
    public Boolean getRetryable() {
        return retryable;
    }

    /**
     * 设置0-不重试 1-重试
     *
     * @param retryable 0-不重试 1-重试
     */
    public void setRetryable(Boolean retryable) {
        this.retryable = retryable;
    }

    /**
     * 获取0-禁用 1-启用
     *
     * @return enabled - 0-禁用 1-启用
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * 设置0-禁用 1-启用
     *
     * @param enabled 0-禁用 1-启用
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }
}