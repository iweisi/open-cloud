package com.github.lyd.base.client.entity;

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
@Table(name = "system_grant_access")
public class SystemGrantAccess implements Serializable {
    private static final long serialVersionUID = 3218590056425312760L;
    @Id
    @KeySql(genId = SnowflakeId.class)
    private Long id;
    /**
     * 授权编码: 资源类型+资源名称  API_INFO
     */
    private String code;

    /**
     * 路径前缀:/,http://,https://
     */
    private String prefix;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 打开方式:_self窗口内,_blank新窗口
     */
    private String target;

    /**
     * 显示名称
     */
    private String name;
    /**
     * 图标
     */
    private String icon;
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
     * 授权所有者ID
     */
    @Column(name = "grant_owner_id")
    private Long grantOwnerId;

    /**
     * 授权所有者编码
     */
    @Column(name = "grant_owner_code")
    private String grantOwnerCode;

    /**
     * 授权所有者类型:系统用户(USER_) 、角色(ROLE_)
     */
    @Column(name = "grant_owner_type")
    private String grantOwnerType;
    /**
     * 状态:0-无效 1-有效
     */
    private Integer status;
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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
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

    public Long getGrantOwnerId() {
        return grantOwnerId;
    }

    public void setGrantOwnerId(Long grantOwnerId) {
        this.grantOwnerId = grantOwnerId;
    }

    public String getGrantOwnerCode() {
        return grantOwnerCode;
    }

    public void setGrantOwnerCode(String grantOwnerCode) {
        this.grantOwnerCode = grantOwnerCode;
    }

    public String getGrantOwnerType() {
        return grantOwnerType;
    }

    public void setGrantOwnerType(String grantOwnerType) {
        this.grantOwnerType = grantOwnerType;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
