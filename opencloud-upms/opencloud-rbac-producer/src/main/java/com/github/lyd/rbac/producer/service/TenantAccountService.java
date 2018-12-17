package com.github.lyd.rbac.producer.service;

import com.github.lyd.rbac.client.dto.TenantAccountDto;
import com.github.lyd.rbac.client.dto.TenantProfileDto;

/**
 * 租户登录账号管理
 * 支持多账号登陆
 *
 * @author liuyadu
 */
public interface TenantAccountService {

    /**
     * 注册账户
     *
     * @param profileDto
     * @return
     */
    Boolean register(TenantProfileDto profileDto);


    /**
     * 绑定租户名账户
     *
     * @param tenantId
     * @param username
     * @param password
     * @return
     */
    Boolean registerUsernameAccount(Long tenantId, String username, String password);

    /**
     * 绑定email账号
     *
     * @param email
     * @param tenantId
     * @param password
     * @return
     */
    Boolean registerEmailAccount(Long tenantId, String email, String password);

    /**
     * 绑定手机账号
     *
     * @param tenantId
     * @param password
     * @param mobile
     * @return
     */
    Boolean registerMobileAccount(Long tenantId, String mobile, String password);

    /**
     * 支持密码、手机号、email登陆
     * 其他方式没有规则，无法自动识别。需要单独开发
     *
     * @param account 登陆账号
     * @return
     */
    TenantAccountDto login(String account);

    /**
     * 更新租户密码
     *
     * @param tenantId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    Boolean resetPassword(Long tenantId, String oldPassword, String newPassword);

    /**
     * 添加登录日志
     *
     * @param tenantId
     * @param userAgent
     * @param ipAddress
     */
    void addLoginLog(Long tenantId, String ipAddress, String userAgent);

    /**
     * 检查账号是否存在
     *
     * @param tenantId
     * @param account
     * @param accountType
     * @return
     */
    Boolean isExist(Long tenantId, String account, String accountType);


    /**
     * 解绑email账号
     *
     * @param email
     * @param tenantId
     * @return
     */
    Boolean removeEmailAccount(Long tenantId, String email);

    /**
     * 解绑手机账号
     *
     * @param tenantId
     * @param mobile
     * @return
     */
    Boolean removeMobileAccount(Long tenantId, String mobile);
}
