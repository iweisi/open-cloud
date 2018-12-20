package com.github.lyd.sys.client.entity;

import com.github.lyd.common.gen.SnowflakeId;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * API资源表
 *
 * @author: liuyadu
 * @date: 2018/10/24 16:21
 * @description:
 */
@Table(name = "system_api")
public class SystemApi implements Serializable {
    private static final long serialVersionUID = -9099562653030770650L;
    /**
     * 资源ID
     */
    @Id
    @Column(name = "api_id")
    @KeySql(genId = SnowflakeId.class)
    private Long apiId;

    /**
     * 资源编码
     */
    @Column(name = "api_code")
    private String apiCode;

    /**
     * 资源名称
     */
    @Column(name = "api_name")
    private String apiName;

    /**
     * 服务ID
     */
    @Column(name = "service_id")
    private String serviceId;

    /**
     * 资源路径
     */
    private String url;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 资源描述
     */
    @Column(name = "api_desc")
    private String apiDesc;

    /**
     * 是否可用
     */
    private Boolean enabled;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取资源ID
     *
     * @return api_id - 资源ID
     */
    public Long getApiId() {
        return apiId;
    }

    /**
     * 设置资源ID
     *
     * @param apiId 资源ID
     */
    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    /**
     * 获取资源编码
     *
     * @return api_code - 资源编码
     */
    public String getApiCode() {
        return apiCode;
    }

    /**
     * 设置资源编码
     *
     * @param apiCode 资源编码
     */
    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    /**
     * 获取资源名称
     *
     * @return api_name - 资源名称
     */
    public String getApiName() {
        return apiName;
    }

    /**
     * 设置资源名称
     *
     * @param apiName 资源名称
     */
    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    /**
     * 获取服务ID
     *
     * @return server_id - 服务ID
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
     * 获取资源路径
     *
     * @return url - 资源路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置资源路径
     *
     * @param url 资源路径
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取优先级
     *
     * @return priority - 优先级
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * 设置优先级
     *
     * @param priority 优先级
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getApiDesc() {
        return apiDesc;
    }

    public void setApiDesc(String apiDesc) {
        this.apiDesc = apiDesc;
    }

    /**
     * 获取是否可用
     *
     * @return enabled - 是否可用
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * 设置是否可用
     *
     * @param enabled 是否可用
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}