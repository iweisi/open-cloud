package com.github.lyd.rbac.client.dto;

import com.github.lyd.rbac.client.entity.Roles;
import com.github.lyd.rbac.client.entity.TenantAccount;
import com.github.lyd.rbac.client.entity.TenantProfile;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * @author: liuyadu
 * @date: 2018/11/12 11:35
 * @description:
 */
public class TenantAccountDto extends TenantAccount implements Serializable {
    private static final long serialVersionUID = 6717800085953996702L;

    /**
     * 租户资料
     */
    private TenantProfile tenantProfile;

    /**
     * 租户权限
     */
    private List<String> authorities = Lists.newArrayList();

    /**
     * 租户角色
     */
    private List<Roles> roles = Lists.newArrayList();


    public TenantProfile getTenantProfile() {
        return tenantProfile;
    }

    public void setTenantProfile(TenantProfile tenantProfile) {
        this.tenantProfile = tenantProfile;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
