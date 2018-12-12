package com.github.lyd.rbac.client.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.lyd.rbac.client.entity.Role;
import com.github.lyd.rbac.client.entity.UserProfile;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * @author: liuyadu
 * @date: 2018/11/12 11:35
 * @description:
 */
public class UserProfileDto extends UserProfile implements Serializable {
    private static final long serialVersionUID = 6717800085953996702L;

    private List<Role> roles;
    /**
     * 密码凭证
     */
    @Transient
    @JsonIgnore
    @JSONField(serialize = false)
    private String password;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
