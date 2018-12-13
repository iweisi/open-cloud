package com.github.lyd.rbac.client.entity;

import com.github.lyd.common.gen.SnowflakeId;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author liuyadu
 */
@Table(name = "platform_tenant_account")
public class TenantAccount implements Serializable {
    private static final long serialVersionUID = -4484479600033295192L;
    @Id
    @Column(name = "account_id")
    @KeySql(genId = SnowflakeId.class)
    private Long accountId;

    /**
     * 租户Id
     */
    @Column(name = "tenant_id")
    private Long tenantId;

    /**
     * 标识：手机号、邮箱、 租户名、或第三方应用的唯一标识
     */
    private String account;

    /**
     * 密码凭证：站内的保存密码、站外的不保存或保存token）
     */
    private String password;

    /**
     * 登录类型:password-密码、mobile-手机号、email-邮箱、weixin-微信、weibo-微博、qq-等等
     */
    @Column(name = "account_type")
    private String accountType;


    public TenantAccount() {
    }

    public TenantAccount(Long tenantId, String account, String password, String accountType) {
        this.tenantId = tenantId;
        this.account = account;
        this.password = password;
        this.accountType = accountType;
    }

    /**
     * @return account_id
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * @param accountId
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

}