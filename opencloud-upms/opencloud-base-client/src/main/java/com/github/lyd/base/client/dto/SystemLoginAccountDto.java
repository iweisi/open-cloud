package com.github.lyd.base.client.dto;

import com.github.lyd.base.client.entity.SystemRole;
import com.github.lyd.base.client.entity.SystemLoginAccount;
import com.github.lyd.base.client.entity.SystemUser;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * @author: liuyadu
 * @date: 2018/11/12 11:35
 * @description:
 */
public class SystemLoginAccountDto extends SystemLoginAccount implements Serializable {
    private static final long serialVersionUID = 6717800085953996702L;

    /**
     * 系统用户资料
     */
    private SystemUser userProfile;

    /**
     * 系统用户权限
     */
    private List<String> authorities = Lists.newArrayList();

    /**
     * 系统用户角色
     */
    private List<SystemRole> roles = Lists.newArrayList();


    public SystemUser getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(SystemUser userProfile) {
        this.userProfile = userProfile;
    }

    public List<SystemRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SystemRole> roles) {
        this.roles = roles;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
