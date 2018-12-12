package com.github.lyd.gateway.client.entity;

import com.github.lyd.common.gen.SnowflakeId;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 网关流量限制
 *
 * @author: liuyadu
 * @date: 2018/10/24 16:21
 * @description:
 */
@Table(name = "platform_gateway_rate_limit")
public class GatewayRateLimit implements Serializable {
    private static final long serialVersionUID = 3692306737735802410L;
    @Id
    @KeySql(genId = SnowflakeId.class)
    private Long id;
    /**
     * 限制数量
     */
    private Long limit;

    /**
     * 时间间隔(秒)
     */
    private Long interval;

    @Column(name = "service_id")
    private String serviceId;

    /**
     * 0-禁用 1-启用
     */
    private Boolean enabled;

    /**
     * 限流规则内容
     */
    private String type;

    private String rules;

    /**
     * 获取限制数量
     *
     * @return limit - 限制数量
     */
    public Long getLimit() {
        return limit;
    }

    /**
     * 设置限制数量
     *
     * @param limit 限制数量
     */
    public void setLimit(Long limit) {
        this.limit = limit;
    }

    /**
     * 获取时间间隔(秒)
     *
     * @return interval - 时间间隔(秒)
     */
    public Long getInterval() {
        return interval;
    }

    /**
     * 设置时间间隔(秒)
     *
     * @param interval 时间间隔(秒)
     */
    public void setInterval(Long interval) {
        this.interval = interval;
    }

    /**
     * @return service_id
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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

    /**
     * 获取限流规则内容
     *
     * @return type - 限流规则内容
     */
    public String getType() {
        return type;
    }

    /**
     * 设置限流规则内容
     *
     * @param type 限流规则内容
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return rules
     */
    public String getRules() {
        return rules;
    }

    /**
     * @param rules
     */
    public void setRules(String rules) {
        this.rules = rules;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}