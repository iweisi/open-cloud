package com.github.lyd.rbac.client.dto;

import com.github.lyd.rbac.client.entity.Role;
import com.github.lyd.rbac.client.entity.UserAccount;
import com.github.lyd.rbac.client.entity.UserProfile;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * @author: liuyadu
 * @date: 2018/11/12 11:35
 * @description:
 */
public class UserAccountDto extends UserAccount implements Serializable {
    private static final long serialVersionUID = 6717800085953996702L;

    /**
     * 用户资料
     */
    private UserProfile userProfile;

    /**
     * 用户权限
     */
    private List<String> authorities = Lists.newArrayList();

    /**
     * 用户角色
     */
    private List<Role> roles = Lists.newArrayList();


    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
