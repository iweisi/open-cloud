package com.github.lyd.rbac.producer.service.impl;

import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.common.utils.StringUtils;
import com.github.lyd.rbac.client.constans.RbacConstans;
import com.github.lyd.rbac.client.dto.TenantAccountDto;
import com.github.lyd.rbac.client.entity.*;
import com.github.lyd.rbac.producer.mapper.TenantAccountLogsMapper;
import com.github.lyd.rbac.producer.mapper.TenantAccountMapper;
import com.github.lyd.rbac.producer.service.PermissionService;
import com.github.lyd.rbac.producer.service.RolesService;
import com.github.lyd.rbac.producer.service.TenantAccountService;
import com.github.lyd.rbac.producer.service.TenantProfileService;
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
public class TenantAccountServiceImpl implements TenantAccountService {

    @Autowired
    private TenantAccountMapper tenantAccountMapper;

    @Autowired
    private TenantProfileService tenantProfileService;
    @Autowired
    private RolesService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TenantAccountLogsMapper tenantAccountLogsMapper;
    @Autowired
    private PermissionService permissionService;

    /**
     * 支持租户名、手机号、email登陆
     *
     * @param account
     * @return
     */
    @Override
    public TenantAccountDto login(String account) {
        if (StringUtils.isBlank(account)) {
            return null;
        }
        TenantAccount tenantAccount = null;
        TenantAccountDto accountDto = null;
        ExampleBuilder builder = new ExampleBuilder(TenantAccount.class);
        Example example = builder.criteria()
                .andEqualTo("account", account)
                .andEqualTo("accountType", RbacConstans.USER_ACCOUNT_TYPE_USERNAME)
                .end().build();
        //默认租户名登录
        tenantAccount = tenantAccountMapper.selectOneByExample(example);

        if (tenantAccount == null && StringUtils.matchMobile(account)) {
            //强制清空
            example.clear();
            //  尝试邮箱获取
            example = builder.criteria()
                    .andEqualTo("account", account)
                    .andEqualTo("accountType", RbacConstans.USER_ACCOUNT_TYPE_MOBILE)
                    .end().build();
            tenantAccount = tenantAccountMapper.selectOneByExample(example);
        }

        if (tenantAccount == null && StringUtils.matchEmail(account)) {
            //强制清空
            example.clear();
            //  尝试邮箱获取
            example = builder.criteria()
                    .andEqualTo("account", account)
                    .andEqualTo("accountType", RbacConstans.USER_ACCOUNT_TYPE_EMAIL)
                    .end().build();
            tenantAccount = tenantAccountMapper.selectOneByExample(example);
        }

        if (tenantAccount != null) {
            accountDto = new TenantAccountDto();
            BeanUtils.copyProperties(tenantAccount, accountDto);
            List<String> authorities = Lists.newArrayList();
            //查询角色
            List<Roles> rolesList = roleService.getTenantRoles(tenantAccount.getTenantId());
            if (rolesList != null) {
                for (Roles role : rolesList) {
                    authorities.add(RbacConstans.PERMISSION_IDENTITY_PREFIX_ROLE + role.getRoleCode());
                }
            }
            //获取租户私有权限
            List<ResourcePermission> selfList = permissionService.getTenantPrivatePermission(tenantAccount.getTenantId());
            if (selfList != null) {
                for (ResourcePermission self : selfList) {
                    authorities.add(self.getIdentityCode());
                }
            }
            accountDto.setRoles(rolesList);
            accountDto.setAuthorities(authorities);
            //查询租户资料
            accountDto.setTenantProfile(tenantProfileService.getProfile(tenantAccount.getTenantId()));
        }
        return accountDto;
    }

    /**
     * 更新租户密码
     *
     * @param tenantId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @Override
    public boolean resetPassword(Long tenantId, String oldPassword, String newPassword) {
        if (tenantId == null || StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)) {
            return false;
        }
        TenantProfile userProfile = tenantProfileService.getProfile(tenantId);
        if (userProfile == null) {
            throw new OpenMessageException("租户不存在");
        }
        ExampleBuilder builder = new ExampleBuilder(TenantAccount.class);
        Example example = builder.criteria()
                .andEqualTo("tenantId", tenantId)
                .andEqualTo("account", userProfile.getUserName())
                .andEqualTo("accountType", RbacConstans.USER_ACCOUNT_TYPE_USERNAME)
                .end().build();
        TenantAccount userAccount = tenantAccountMapper.selectOneByExample(example);
        if (userAccount == null) {
            return false;
        }
        String oldPasswordEncoder = passwordEncoder.encode(oldPassword);
        if (!passwordEncoder.matches(userAccount.getPassword(), oldPasswordEncoder)) {
            throw new OpenMessageException("原密码不正确");
        }
        userAccount.setPassword(passwordEncoder.encode(newPassword));
        int count = tenantAccountMapper.updateByPrimaryKey(userAccount);
        return count > 0;
    }

    /**
     * 更新租户登录Ip
     *
     * @param tenantId
     * @param ipAddress
     */
    @Override
    public void addLoginLog(Long tenantId, String ipAddress, String agent) {
        if (tenantId == null) {
            return;
        }
        ExampleBuilder builder = new ExampleBuilder(TenantAccountLogs.class);
        Example example = builder.criteria().andEqualTo("tenantId", tenantId).end().build();
        int count = tenantAccountLogsMapper.selectCountByExample(example);

        TenantAccountLogs logs = new TenantAccountLogs();
        logs.setTenantId(tenantId);
        logs.setLoginTime(new Date());
        logs.setLoginIp(ipAddress);
        logs.setLoginAgent(agent);
        logs.setLoginNums(count + 1);
        tenantAccountLogsMapper.insertSelective(logs);
    }

    /**
     * 检查是否已绑定账号
     *
     * @param tenantId
     * @param account
     * @param accountType
     * @return
     */
    @Override
    public boolean isExist(Long tenantId, String account, String accountType) {
        ExampleBuilder builder = new ExampleBuilder(TenantAccount.class);
        Example example = builder.criteria()
                .andEqualTo("tenantId", tenantId)
                .andEqualTo("account", account)
                .andEqualTo("accountType", accountType)
                .end().build();
        int count = tenantAccountMapper.selectCountByExample(example);
        return count > 0 ? true : false;
    }

    /**
     * 绑定租户名账户
     *
     * @param tenantId
     * @param username
     * @param password
     */
    @Override
    public boolean bindUsernameOnAccount(Long tenantId, String username, String password) {
        if (isExist(tenantId, username, RbacConstans.USER_ACCOUNT_TYPE_USERNAME)) {
            //已经绑定
            return false;
        }
        TenantAccount userAccount = new TenantAccount(tenantId, username, password, RbacConstans.USER_ACCOUNT_TYPE_USERNAME);
        int result = tenantAccountMapper.insertSelective(userAccount);
        return result > 0;
    }

    /**
     * 绑定email账号
     *
     * @param tenantId
     * @param email
     * @param password
     */
    @Override
    public boolean bindEmailOnAccount(Long tenantId, String email, String password) {
        if (!StringUtils.matchEmail(email)) {
            return false;
        }
        if (isExist(tenantId, email, RbacConstans.USER_ACCOUNT_TYPE_EMAIL)) {
            //已经绑定
            return false;
        }
        TenantAccount userAccount = new TenantAccount(tenantId, email, password, RbacConstans.USER_ACCOUNT_TYPE_EMAIL);
        int result = tenantAccountMapper.insertSelective(userAccount);
        return result > 0;
    }

    /**
     * 绑定手机账号
     *
     * @param tenantId
     * @param mobile
     * @param password
     */
    @Override
    public boolean bindMobileOnAccount(Long tenantId, String mobile, String password) {
        if (!StringUtils.matchMobile(mobile)) {
            return false;
        }
        if (isExist(tenantId, mobile, RbacConstans.USER_ACCOUNT_TYPE_MOBILE)) {
            //已经绑定
            return false;
        }
        TenantAccount userAccount = new TenantAccount(tenantId, mobile, password, RbacConstans.USER_ACCOUNT_TYPE_MOBILE);
        int result = tenantAccountMapper.insertSelective(userAccount);
        return result > 0;
    }

    /**
     * 解绑email账号
     *
     * @param tenantId
     * @param email
     * @return
     */
    @Override
    public boolean unbindEmailOnAccount(Long tenantId, String email) {
        ExampleBuilder builder = new ExampleBuilder(TenantAccount.class);
        Example example = builder.criteria()
                .andEqualTo("tenantId", tenantId)
                .andEqualTo("account", email)
                .andEqualTo("accountType", RbacConstans.USER_ACCOUNT_TYPE_EMAIL)
                .end().build();
        int count = tenantAccountMapper.deleteByExample(example);
        return count > 0 ? true : false;
    }

    /**
     * 解绑手机账号
     *
     * @param tenantId
     * @param mobile
     * @return
     */
    @Override
    public boolean unbindMobileOnAccount(Long tenantId, String mobile) {
        ExampleBuilder builder = new ExampleBuilder(TenantAccount.class);
        Example example = builder.criteria()
                .andEqualTo("tenantId", tenantId)
                .andEqualTo("account", mobile)
                .andEqualTo("accountType", RbacConstans.USER_ACCOUNT_TYPE_MOBILE)
                .end().build();
        int count = tenantAccountMapper.deleteByExample(example);
        return count > 0 ? true : false;
    }

}
