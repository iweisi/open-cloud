package com.github.lyd.base.producer.service.impl;

import com.github.lyd.base.client.entity.*;
import com.github.lyd.base.producer.mapper.SystemAccountLogsMapper;
import com.github.lyd.base.producer.service.SystemAccountService;
import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.common.utils.StringUtils;
import com.github.lyd.base.client.constants.BaseConstants;
import com.github.lyd.base.client.dto.SystemAccountDto;
import com.github.lyd.base.client.dto.SystemUserDto;
import com.github.lyd.base.producer.mapper.SystemAccountMapper;
import com.github.lyd.base.producer.service.SystemGrantAccessService;
import com.github.lyd.base.producer.service.SystemRoleService;
import com.github.lyd.base.producer.service.SystemUserService;
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
public class SystemAccountServiceImpl implements SystemAccountService {

    @Autowired
    private SystemAccountMapper systemAccountMapper;
    @Autowired
    private SystemAccountLogsMapper systemAccountLogsMapper;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SystemRoleService roleService;
    @Autowired
    private SystemGrantAccessService systemAccessService;

    /**
     * 添加系统用户
     *
     * @param profileDto
     * @return
     */
    @Override
    public Boolean register(SystemUserDto profileDto) {
        if (profileDto == null) {
            return false;
        }
        if (StringUtils.isBlank(profileDto.getUserName())) {
            throw new OpenMessageException("账号不能为空!");
        }
        if (StringUtils.isBlank(profileDto.getPassword())) {
            throw new OpenMessageException("密码不能为空!");
        }
        SystemUser saved = systemUserService.getProfile(profileDto.getUserName());
        if (saved != null) {
            // 已注册
            throw new OpenMessageException("登录名已经被注册!");
        }
        //未注册
        saved = new SystemUser();
        BeanUtils.copyProperties(profileDto, saved);
        //加密
        String encodePassword = passwordEncoder.encode(profileDto.getPassword());
        if (StringUtils.isBlank(saved.getNickName())) {
            saved.setNickName(saved.getUserName());
        }
        saved.setStatus(BaseConstants.USER_STATE_DISABLE);
        saved.setCreateTime(new Date());
        saved.setUpdateTime(saved.getCreateTime());
        saved.setRegisterTime(saved.getCreateTime());
        //保存系统用户信息
        systemUserService.addProfile(saved);
        //默认注册用户名账户
        Boolean suceess = this.registerUsernameAccount(saved.getUserId(), saved.getUserName(), encodePassword);
        if (suceess && StringUtils.isNotBlank(saved.getEmail())) {
            //注册email账号登陆
            this.registerMobileAccount(saved.getUserId(), saved.getEmail(), encodePassword);
        }
        if (suceess && StringUtils.isNotBlank(saved.getMobile())) {
            //注册手机号账号登陆
            this.registerMobileAccount(saved.getUserId(), saved.getMobile(), encodePassword);
        }
        return suceess;
    }

    /**
     * 支持系统用户名、手机号、email登陆
     *
     * @param account
     * @return
     */
    @Override
    public SystemAccountDto login(String account) {
        if (StringUtils.isBlank(account)) {
            return null;
        }
        SystemAccount userAccount = null;
        SystemAccountDto accountDto = null;
        ExampleBuilder builder = new ExampleBuilder(SystemAccount.class);
        Example example = builder.criteria()
                .andEqualTo("account", account)
                .andEqualTo("accountType", BaseConstants.USER_ACCOUNT_TYPE_USERNAME)
                .end().build();
        //默认用户名登录
        userAccount = systemAccountMapper.selectOneByExample(example);

        if (userAccount == null && StringUtils.matchMobile(account)) {
            //强制清空
            example.clear();
            //  尝试手机号登录
            example = builder.criteria()
                    .andEqualTo("account", account)
                    .andEqualTo("accountType", BaseConstants.USER_ACCOUNT_TYPE_MOBILE)
                    .end().build();
            userAccount = systemAccountMapper.selectOneByExample(example);
        }

        if (userAccount == null && StringUtils.matchEmail(account)) {
            //强制清空
            example.clear();
            //  尝试邮箱登录
            example = builder.criteria()
                    .andEqualTo("account", account)
                    .andEqualTo("accountType", BaseConstants.USER_ACCOUNT_TYPE_EMAIL)
                    .end().build();
            userAccount = systemAccountMapper.selectOneByExample(example);
        }

        if (userAccount != null) {
            accountDto = new SystemAccountDto();
            BeanUtils.copyProperties(userAccount, accountDto);
            List<String> authorities = Lists.newArrayList();
            //查询角色权限
            List<SystemRole> rolesList = roleService.getUserRoles(userAccount.getUserId());
            if (rolesList != null) {
                for (SystemRole role : rolesList) {
                    authorities.add(BaseConstants.AUTHORITY_PREFIX_ROLE + role.getRoleCode());
                }
            }
            //获取系统用户私有权限
            List<SystemGrantAccess> selfList = systemAccessService.getUserPrivateGrantAccessList(userAccount.getUserId());
            if (selfList != null) {
                for (SystemGrantAccess self : selfList) {
                    authorities.add(self.getAuthority());
                }
            }
            accountDto.setRoles(rolesList);
            accountDto.setAuthorities(authorities);
            //查询系统用户资料
            accountDto.setUserProfile(systemUserService.getProfile(userAccount.getUserId()));
        }
        return accountDto;
    }


    /**
     * 注册系统用户名账户
     *
     * @param userId
     * @param username
     * @param password
     */
    @Override
    public Boolean registerUsernameAccount(Long userId, String username, String password) {
        if (isExist(userId, username, BaseConstants.USER_ACCOUNT_TYPE_USERNAME)) {
            //已经注册
            return false;
        }
        SystemAccount userAccount = new SystemAccount(userId, username, password, BaseConstants.USER_ACCOUNT_TYPE_USERNAME);
        int result = systemAccountMapper.insertSelective(userAccount);
        return result > 0;
    }

    /**
     * 注册email账号
     *
     * @param userId
     * @param email
     * @param password
     */
    @Override
    public Boolean registerEmailAccount(Long userId, String email, String password) {
        if (!StringUtils.matchEmail(email)) {
            return false;
        }
        if (isExist(userId, email, BaseConstants.USER_ACCOUNT_TYPE_EMAIL)) {
            //已经注册
            return false;
        }
        SystemAccount userAccount = new SystemAccount(userId, email, password, BaseConstants.USER_ACCOUNT_TYPE_EMAIL);
        int result = systemAccountMapper.insertSelective(userAccount);
        return result > 0;
    }

    /**
     * 注册手机账号
     *
     * @param userId
     * @param mobile
     * @param password
     */
    @Override
    public Boolean registerMobileAccount(Long userId, String mobile, String password) {
        if (!StringUtils.matchMobile(mobile)) {
            return false;
        }
        if (isExist(userId, mobile, BaseConstants.USER_ACCOUNT_TYPE_MOBILE)) {
            //已经注册
            return false;
        }
        SystemAccount userAccount = new SystemAccount(userId, mobile, password, BaseConstants.USER_ACCOUNT_TYPE_MOBILE);
        int result = systemAccountMapper.insertSelective(userAccount);
        return result > 0;
    }


    /**
     * 更新系统用户密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @Override
    public Boolean resetPassword(Long userId, String oldPassword, String newPassword) {
        if (userId == null || StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)) {
            return false;
        }
        SystemUser userProfile = systemUserService.getProfile(userId);
        if (userProfile == null) {
            throw new OpenMessageException("系统用户不存在");
        }
        ExampleBuilder builder = new ExampleBuilder(SystemAccount.class);
        Example example = builder.criteria()
                .andEqualTo("userId", userId)
                .andEqualTo("account", userProfile.getUserName())
                .andEqualTo("accountType", BaseConstants.USER_ACCOUNT_TYPE_USERNAME)
                .end().build();
        SystemAccount userAccount = systemAccountMapper.selectOneByExample(example);
        if (userAccount == null) {
            return false;
        }
        String oldPasswordEncoder = passwordEncoder.encode(oldPassword);
        if (!passwordEncoder.matches(userAccount.getPassword(), oldPasswordEncoder)) {
            throw new OpenMessageException("原密码不正确");
        }
        userAccount.setPassword(passwordEncoder.encode(newPassword));
        int count = systemAccountMapper.updateByPrimaryKey(userAccount);
        return count > 0;
    }

    /**
     * 更新系统用户登录Ip
     *
     * @param log
     */
    @Override
    public void addLoginLog(SystemAccountLogs log) {
        ExampleBuilder builder = new ExampleBuilder(SystemAccountLogs.class);
        Example example = builder.criteria().andEqualTo("userId", log.getUserId()).end().build();
        int count = systemAccountLogsMapper.selectCountByExample(example);
        log.setLoginTime(new Date());
        log.setLoginNums(count + 1);
        systemAccountLogsMapper.insertSelective(log);
    }

    /**
     * 检查是否已注册账号
     *
     * @param userId
     * @param account
     * @param accountType
     * @return
     */
    @Override
    public Boolean isExist(Long userId, String account, String accountType) {
        ExampleBuilder builder = new ExampleBuilder(SystemAccount.class);
        Example example = builder.criteria()
                .andEqualTo("userId", userId)
                .andEqualTo("account", account)
                .andEqualTo("accountType", accountType)
                .end().build();
        int count = systemAccountMapper.selectCountByExample(example);
        return count > 0 ? true : false;
    }

    /**
     * 解绑email账号
     *
     * @param userId
     * @param email
     * @return
     */
    @Override
    public Boolean removeEmailAccount(Long userId, String email) {
        ExampleBuilder builder = new ExampleBuilder(SystemAccount.class);
        Example example = builder.criteria()
                .andEqualTo("userId", userId)
                .andEqualTo("account", email)
                .andEqualTo("accountType", BaseConstants.USER_ACCOUNT_TYPE_EMAIL)
                .end().build();
        int count = systemAccountMapper.deleteByExample(example);
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
    public Boolean removeMobileAccount(Long userId, String mobile) {
        ExampleBuilder builder = new ExampleBuilder(SystemAccount.class);
        Example example = builder.criteria()
                .andEqualTo("userId", userId)
                .andEqualTo("account", mobile)
                .andEqualTo("accountType", BaseConstants.USER_ACCOUNT_TYPE_MOBILE)
                .end().build();
        int count = systemAccountMapper.deleteByExample(example);
        return count > 0 ? true : false;
    }

}
