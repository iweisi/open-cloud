package com.github.lyd.common.security;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * 公共认证用户信息
 *
 * @author liuyadu
 */
public class OpenUserAuth implements UserDetails {
    private static final long serialVersionUID = -123308657146774881L;
    /**
     * 登录账号类型
     */
    private String accountType;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 登录名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 用户角色
     */
    private Collection<Map> roles;
    /**
     * 用户权限
     */
    private Collection<String> authorities;
    /**
     * 是否已锁定
     */
    private boolean accountNonLocked;
    /**
     * 是否已过期
     */
    private boolean accountNonExpired;
    /**
     * 是否启用
     */
    private boolean enabled;
    /**
     * 密码是否已过期
     */
    private boolean credentialsNonExpired;
    /**
     * 认证客户端ID
     */
    private String authAppId;
    /**
     * 认证中心ID,适用于区分多用户源,多认证中心
     */
    private String authCenterId;

    public OpenUserAuth() {
    }

    public OpenUserAuth(String authCenterId, String accountType, Long userId, String avatar, String username, String nickName, String password, Collection<Map> roles, Collection<String> authorities, boolean accountNonLocked, boolean accountNonExpired, boolean enabled, boolean credentialsNonExpired) {
        this.authCenterId = authCenterId;
        this.accountType = accountType;
        this.userId = userId;
        this.avatar = avatar;
        this.username = username;
        this.nickName = nickName;
        this.roles = roles;
        this.password = password;
        this.authorities = authorities;
        this.accountNonLocked = accountNonLocked;
        this.accountNonExpired = accountNonExpired;
        this.enabled = enabled;
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            return Collections.EMPTY_LIST;
        }
        return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                .collectionToCommaDelimitedString(authorities));
    }

    @JsonIgnore
    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Map> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Map> roles) {
        this.roles = roles;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAuthAppId() {
        return authAppId;
    }

    public void setAuthAppId(String authAppId) {
        this.authAppId = authAppId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setAuthorities(Collection<String> authorities) {
        this.authorities = authorities;
    }

    public String getAuthCenterId() {
        return authCenterId;
    }

    public void setAuthCenterId(String authCenterId) {
        this.authCenterId = authCenterId;
    }
}