package com.github.lyd.rbac.client.entity;

import com.github.lyd.common.gen.SnowflakeId;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 资源授权表
 *
 * @author: liuyadu
 * @date: 2018/10/24 16:21
 * @description:
 */
@Table(name = "platform_resource_permission")
public class ResourcePermission implements Serializable {
    private static final long serialVersionUID = 3218590056425312760L;
    @Id
    @KeySql(genId = SnowflakeId.class)
    private Long id;
    /**
     * 授权编码: 资源类型+资源名称  API_INFO
     */
    private String code;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 显示名称
     */
    private String name;

    /**
     * 资源ID
     */
    @Column(name = "resource_id")
    private Long resourceId;

    /**
     * 资源父级节点,默认为0
     */
    @Column(name = "resource_pid")
    private Long resourcePid;

    /**
     * 资源类型:api,menu,action
     */
    @Column(name = "resource_type")
    private String resourceType;
    /**
     * 服务ID
     */
    @Column(name = "service_id")
    private String serviceId;
    /**
     * 授权身份ID
     */
    @Column(name = "identity_id")
    private Long identityId;

    /**
     * 授权身份编码
     */
    @Column(name = "identity_code")
    private String identityCode;

    /**
     * 授权身份前缀:用户(USER_) 、角色(ROLE_)
     */
    @Column(name = "identity_prefix")
    private String identityPrefix;

    /**
     * 获取授权编码: {权限拥有者}+{资源类型}+{资源名称}  user:api:getInfo
     *
     * @return code - 授权编码: {权限拥有者}+{资源类型}+{资源名称}  user:api:getInfo
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置授权编码: {权限拥有者}+{资源类型}+{资源名称}  user:api:getInfo
     *
     * @param code 授权编码: {权限拥有者}+{资源类型}+{资源名称}  user:api:getInfo
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取资源ID
     *
     * @return resource_id - 资源ID
     */
    public Long getResourceId() {
        return resourceId;
    }

    /**
     * 设置资源ID
     *
     * @param resourceId 资源ID
     */
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * 获取资源类型:api,menu,button
     *
     * @return resource_type - 资源类型:api,menu,button
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * 设置资源类型:api,menu,button
     *
     * @param resourceType 资源类型:api,menu,button
     */
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Long getIdentityId() {
        return identityId;
    }

    public void setIdentityId(Long identityId) {
        this.identityId = identityId;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public String getIdentityPrefix() {
        return identityPrefix;
    }

    public void setIdentityPrefix(String identityPrefix) {
        this.identityPrefix = identityPrefix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getResourcePid() {
        return resourcePid;
    }

    public void setResourcePid(Long resourcePid) {
        this.resourcePid = resourcePid;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}