package com.github.lyd.rbac.producer.service;

import com.github.lyd.rbac.client.dto.UserAccountDto;

/**
 * 用户登录账号管理
 * 支持多账号登陆
 *
 * @author liuyadu
 */
public interface UserAccountService {

    /**
     * 支持密码、手机号、email登陆
     * 其他方式没有规则，无法自动识别。需要单独开发
     *
     * @param account 登陆账号
     * @return
     */
    UserAccountDto login(String account);

    /**
     * 更新用户密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    boolean resetPassword(Long userId, String oldPassword, String newPassword);

    /**
     * 添加登录日志
     *
     * @param userId
     * @param userAgent
     * @param ipAddress
     */
    void addLoginLog(Long userId, String ipAddress, String userAgent);

    /**
     * 检查账号是否存在
     *
     * @param userId
     * @param account
     * @param accountType
     * @return
     */
    boolean isExist(Long userId, String account, String accountType);

    /**
     * 绑定用户名账户
     *
     * @param userId
     * @param username
     * @param password
     * @return
     */
    boolean bindUsernameOnAccount(Long userId, String username, String password);

    /**
     * 绑定email账号
     *
     * @param email
     * @param userId
     * @param password
     * @return
     */
    boolean bindEmailOnAccount(Long userId, String email, String password);

    /**
     * 绑定手机账号
     *
     * @param userId
     * @param password
     * @param mobile
     * @return
     */
    boolean bindMobileOnAccount(Long userId, String mobile, String password);

    /**
     * 解绑email账号
     *
     * @param email
     * @param userId
     * @return
     */
    boolean unbindEmailOnAccount(Long userId, String email);

    /**
     * 解绑手机账号
     *
     * @param userId
     * @param mobile
     * @return
     */
    boolean unbindMobileOnAccount(Long userId, String mobile);
}
