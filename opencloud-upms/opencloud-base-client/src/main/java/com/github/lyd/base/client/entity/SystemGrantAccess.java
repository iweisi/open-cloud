package com.github.lyd.base.client.entity;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.lyd.common.gen.SnowflakeId;
import com.github.lyd.common.utils.StringUtils;
import com.google.common.collect.Maps;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Map;

/**
 * 资源授权表
 *
 * @author: liuyadu
 * @date: 2018/10/24 16:21
 * @description:
 */
@Table(name = "system_grant_access")
public class SystemGrantAccess implements Serializable {
    private static final long serialVersionUID = 3218590056425312760L;
    @Id
    @KeySql(genId = SnowflakeId.class)
    private Long id;
    /**
     * 请求路径
     */
    private String path;

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
     * 资源详情:resource_info,必须为JSON字符串
     */
    @JsonIgnore
    @Column(name = "resource_info")
    private String resourceInfo;

    /**
     * 服务ID
     */
    @Column(name = "service_id")
    private String serviceId;
    /**
     * 授权权限所有者ID
     */
    @Column(name = "authority_owner")
    private String authorityOwner;

    /**
     * 权限标识
     */
    @Column(name = "authority")
    private String authority;

    /**
     * 权限前缀:用户(USER_) 、角色(ROLE_)、APP(APP_)
     */
    @Column(name = "authority_prefix")
    private String authorityPrefix;

    /**
     * 状态:0-无效 1-有效
     */
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getResourcePid() {
        return resourcePid;
    }

    public void setResourcePid(Long resourcePid) {
        this.resourcePid = resourcePid;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceInfo() {
        return resourceInfo;
    }

    @Transient
    public Map getResource() {
        if (StringUtils.isNotBlank(this.resourceInfo)) {
            try {
                return JSONObject.parseObject(this.resourceInfo, Map.class);
            } catch (Exception e) {
                return Maps.newHashMap();
            }
        }
        return Maps.newHashMap();
    }

    public void setResourceInfo(String resourceInfo) {
        this.resourceInfo = resourceInfo;
    }

    @Transient
    public void setResourceInfo(Object resourceInfo) {
        try {
            this.resourceInfo = JSONObject.toJSONString(resourceInfo);
        } catch (Exception e) {
        }
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getAuthorityOwner() {
        return authorityOwner;
    }

    public void setAuthorityOwner(String authorityOwner) {
        this.authorityOwner = authorityOwner;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthorityPrefix() {
        return authorityPrefix;
    }

    public void setAuthorityPrefix(String authorityPrefix) {
        this.authorityPrefix = authorityPrefix;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
