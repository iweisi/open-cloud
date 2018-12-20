package com.github.lyd.base.client.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.lyd.base.client.entity.SystemUser;
import com.github.lyd.base.client.entity.SystemRole;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * @author: liuyadu
 * @date: 2018/11/12 11:35
 * @description:
 */
public class SystemUserDto extends SystemUser implements Serializable {
    private static final long serialVersionUID = 6717800085953996702L;

    private List<SystemRole> roles;
    /**
     * 密码凭证
     */
    @Transient
    @JsonIgnore
    @JSONField(serialize = false)
    private String password;

    public List<SystemRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SystemRole> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
