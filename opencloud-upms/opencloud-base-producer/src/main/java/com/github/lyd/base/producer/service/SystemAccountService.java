package com.github.lyd.base.producer.service;

import com.github.lyd.base.client.dto.SystemAccountDto;
import com.github.lyd.base.client.dto.SystemUserDto;
import com.github.lyd.base.client.entity.SystemAccountLogs;

/**
 * 系统用户登录账号管理
 * 支持多账号登陆
 *
 * @author liuyadu
 */
public interface SystemAccountService {

    /**
     * 注册账户
     *
     * @param profileDto
     * @return
     */
    Long register(SystemUserDto profileDto);


    /**
     * 绑定系统用户名账户
     *
     * @param userId
     * @param username
     * @param password
     * @return
     */
    Long registerUsernameAccount(Long userId, String username, String password);

    /**
     * 绑定email账号
     *
     * @param email
     * @param userId
     * @param password
     * @return
     */
    void registerEmailAccount(Long userId, String email, String password);

    /**
     * 绑定手机账号
     *
     * @param userId
     * @param password
     * @param mobile
     * @return
     */
    void registerMobileAccount(Long userId, String mobile, String password);

    /**
     * 支持密码、手机号、email登陆
     * 其他方式没有规则，无法自动识别。需要单独开发
     *
     * @param account 登陆账号
     * @return
     */
    SystemAccountDto login(String account);

    /**
     * 更新系统用户密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    void resetPassword(Long userId, String oldPassword, String newPassword);

    /**
     * 添加登录日志
     *
     * @param log
     */
    void addLoginLog(SystemAccountLogs log);

    /**
     * 检查账号是否存在
     *
     * @param userId
     * @param account
     * @param accountType
     * @return
     */
    Boolean isExist(Long userId, String account, String accountType);


    /**
     * 解绑email账号
     *
     * @param email
     * @param userId
     * @return
     */
    void removeEmailAccount(Long userId, String email);

    /**
     * 解绑手机账号
     *
     * @param userId
     * @param mobile
     * @return
     */
    void removeMobileAccount(Long userId, String mobile);
}
