package com.github.lyd.base.client.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.lyd.base.client.entity.SystemUser;
import com.google.common.collect.Lists;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author: liuyadu
 * @date: 2018/11/12 11:35
 * @description:
 */
public class SystemUserDto extends SystemUser implements Serializable {
    private static final long serialVersionUID = 6717800085953996702L;
    /**
     * 用户角色
     */
    private Collection<Map> roles = Lists.newArrayList();
    /**
     * 用户角色ID
     */
    private List<Long> roleIds = Lists.newArrayList();
    /**
     * 用户权限
     */
    private Collection<String> authorities = Lists.newArrayList();

    /**
     * 第三方账号
     */
    private String thirdParty;
    /**
     * 密码凭证
     */
    @Transient
    @JsonIgnore
    @JSONField(serialize = false)
    private String password;

    public Collection<Map> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Map> roles) {
        this.roles = roles;
    }

    public Collection<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<String> authorities) {
        this.authorities = authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public String getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(String thirdParty) {
        this.thirdParty = thirdParty;
    }
}
