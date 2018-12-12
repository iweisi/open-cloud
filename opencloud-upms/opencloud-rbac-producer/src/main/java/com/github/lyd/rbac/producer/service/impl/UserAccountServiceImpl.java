package com.github.lyd.rbac.producer.service.impl;

import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.common.utils.StringUtils;
import com.github.lyd.rbac.client.constans.RbacConstans;
import com.github.lyd.rbac.client.dto.UserAccountDto;
import com.github.lyd.rbac.client.entity.*;
import com.github.lyd.rbac.producer.mapper.UserAccountLogsMapper;
import com.github.lyd.rbac.producer.mapper.UserAccountMapper;
import com.github.lyd.rbac.producer.mapper.UserProfileMapper;
import com.github.lyd.rbac.producer.service.PermissionService;
import com.github.lyd.rbac.producer.service.RoleService;
import com.github.lyd.rbac.producer.service.UserAccountService;
import com.github.lyd.rbac.producer.service.UserProfileService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author liuyadu
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountMapper userAccountMapper;
    @Autowired
    private UserProfileMapper userProfileMapper;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserAccountLogsMapper userLoginLogsMapper;
    @Autowired
    private PermissionService permissionService;

    /**
     * 支持用户名、手机号、email登陆
     *
     * @param account
     * @return
     */
    @Override
    public UserAccountDto login(String account) {
        if (StringUtils.isBlank(account)) {
            return null;
        }
        UserAccount userAccount = null;
        UserAccountDto userAccountDto = null;
        ExampleBuilder builder = new ExampleBuilder(UserAccount.class);
        Example example = builder.criteria()
                .andEqualTo("account", account)
                .andEqualTo("accountType", RbacConstans.USER_ACCOUNT_TYPE_USERNAME)
                .end().build();
        //默认用户名登录
        userAccount = userAccountMapper.selectOneByExample(example);

        if (userAccount == null && StringUtils.matchMobile(account)) {
            //强制清空
            example.clear();
            //  尝试邮箱获取
            example = builder.criteria()
                    .andEqualTo("account", account)
                    .andEqualTo("accountType", RbacConstans.USER_ACCOUNT_TYPE_MOBILE)
                    .end().build();
            userAccount = userAccountMapper.selectOneByExample(example);
        }

        if (userAccount == null && StringUtils.matchEmail(account)) {
            //强制清空
            example.clear();
            //  尝试邮箱获取
            example = builder.criteria()
                    .andEqualTo("account", account)
                    .andEqualTo("accountType", RbacConstans.USER_ACCOUNT_TYPE_EMAIL)
                    .end().build();
            userAccount = userAccountMapper.selectOneByExample(example);
        }

        if (userAccount != null) {
            userAccountDto = new UserAccountDto();
            BeanUtils.copyProperties(userAccount, userAccountDto);
            List<String> authorities = Lists.newArrayList();
            //查询角色
            List<Role> rolesList = roleService.getUserRoles(userAccount.getUserId());
            if (rolesList != null) {
                for (Role role : rolesList) {
                    authorities.add(RbacConstans.PERMISSION_IDENTITY_PREFIX_ROLE + role.getRoleCode());
                }
            }
            //获取用户私有权限
            List<ResourcePermission> selfList = permissionService.getUserSelfPermission(userAccount.getUserId());
            if (selfList != null) {
                for (ResourcePermission self : selfList) {
                    authorities.add(self.getIdentityCode());
                }
            }
            userAccountDto.setRoles(rolesList);
            userAccountDto.setAuthorities(authorities);
            //查询用户资料
            userAccountDto.setUserProfile(userProfileService.getUserProfile(userAccount.getUserId()));
        }
        return userAccountDto;
    }

    /**
     * 更新用户密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @Override
    public boolean resetPassword(Long userId, String oldPassword, String newPassword) {
        if (userId == null || StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)) {
            return false;
        }
        UserProfile userProfile = userProfileService.getUserProfile(userId);
        if (userProfile == null) {
            throw new OpenMessageException("用户不存在");
        }
        ExampleBuilder builder = new ExampleBuilder(UserAccount.class);
        Example example = builder.criteria()
                .andEqualTo("userId", userId)
                .andEqualTo("account", userProfile.getUserName())
                .andEqualTo("accountType", RbacConstans.USER_ACCOUNT_TYPE_USERNAME)
                .end().build();
        UserAccount userAccount = userAccountMapper.selectOneByExample(example);
        if (userAccount == null) {
            return false;
        }
        String oldPasswordEncoder = passwordEncoder.encode(oldPassword);
        if (!passwordEncoder.matches(userAccount.getPassword(), oldPasswordEncoder)) {
            throw new OpenMessageException("原密码不正确");
        }
        userAccount.setPassword(passwordEncoder.encode(newPassword));
        int count = userAccountMapper.updateByPrimaryKey(userAccount);
        return count > 0;
    }

    /**
     * 更新用户登录Ip
     *
     * @param userId
     * @param ipAddress
     */
    @Override
    public void addLoginLog(Long userId, String ipAddress, String agent) {
        if (userId == null) {
            return;
        }
        ExampleBuilder builder = new ExampleBuilder(UserAccountLogs.class);
        Example example = builder.criteria().andEqualTo("userId", userId).end().build();
        int count = userLoginLogsMapper.selectCountByExample(example);

        UserAccountLogs logs = new UserAccountLogs();
        logs.setUserId(userId);
        logs.setLoginTime(new Date());
        logs.setLoginIp(ipAddress);
        logs.setLoginAgent(agent);
        logs.setLoginNums(count + 1);
        userLoginLogsMapper.insertSelective(logs);
    }

    /**
     * 检查是否已绑定账号
     *
     * @param userId
     * @param account
     * @param accountType
     * @return
     */
    @Override
    public boolean isExist(Long userId, String account, String accountType) {
        ExampleBuilder builder = new ExampleBuilder(UserAccount.class);
        Example example = builder.criteria()
                .andEqualTo("userId", userId)
                .andEqualTo("account", account)
                .andEqualTo("accountType", accountType)
                .end().build();
        int count = userAccountMapper.selectCountByExample(example);
        return count > 0 ? true : false;
    }

    /**
     * 绑定用户名账户
     *
     * @param userId
     * @param username
     * @param password
     */
    @Override
    public boolean bindUsernameOnAccount(Long userId, String username, String password) {
        if (isExist(userId, username, RbacConstans.USER_ACCOUNT_TYPE_USERNAME)) {
            //已经绑定
            return false;
        }
        UserAccount userAccount = new UserAccount(userId, username, password, RbacConstans.USER_ACCOUNT_TYPE_USERNAME);
        int result = userAccountMapper.insertSelective(userAccount);
        return result > 0;
    }

    /**
     * 绑定email账号
     *
     * @param userId
     * @param email
     * @param password
     */
    @Override
    public boolean bindEmailOnAccount(Long userId, String email, String password) {
        if (!StringUtils.matchEmail(email)) {
            return false;
        }
        if (isExist(userId, email, RbacConstans.USER_ACCOUNT_TYPE_EMAIL)) {
            //已经绑定
            return false;
        }
        UserAccount userAccount = new UserAccount(userId, email, password, RbacConstans.USER_ACCOUNT_TYPE_EMAIL);
        int result = userAccountMapper.insertSelective(userAccount);
        return result > 0;
    }

    /**
     * 绑定手机账号
     *
     * @param userId
     * @param mobile
     * @param password
     */
    @Override
    public boolean bindMobileOnAccount(Long userId, String mobile, String password) {
        if (!StringUtils.matchMobile(mobile)) {
            return false;
        }
        if (isExist(userId, mobile, RbacConstans.USER_ACCOUNT_TYPE_MOBILE)) {
            //已经绑定
            return false;
        }
        UserAccount userAccount = new UserAccount(userId, mobile, password, RbacConstans.USER_ACCOUNT_TYPE_MOBILE);
        int result = userAccountMapper.insertSelective(userAccount);
        return result > 0;
    }

    /**
     * 解绑email账号
     *
     * @param userId
     * @param email
     * @return
     */
    @Override
    public boolean unbindEmailOnAccount(Long userId, String email) {
        ExampleBuilder builder = new ExampleBuilder(UserAccount.class);
        Example example = builder.criteria()
                .andEqualTo("userId", userId)
                .andEqualTo("account", email)
                .andEqualTo("accountType", RbacConstans.USER_ACCOUNT_TYPE_EMAIL)
                .end().build();
        int count = userAccountMapper.deleteByExample(example);
        return count > 0 ? true : false;
    }

    /**
     * 解绑手机账号
     *
     * @param userId
     * @param mobile
     * @return
     */
    @Override
    public boolean unbindMobileOnAccount(Long userId, String mobile) {
        ExampleBuilder builder = new ExampleBuilder(UserAccount.class);
        Example example = builder.criteria()
                .andEqualTo("userId", userId)
                .andEqualTo("account", mobile)
                .andEqualTo("accountType", RbacConstans.USER_ACCOUNT_TYPE_MOBILE)
                .end().build();
        int count = userAccountMapper.deleteByExample(example);
        return count > 0 ? true : false;
    }

}
