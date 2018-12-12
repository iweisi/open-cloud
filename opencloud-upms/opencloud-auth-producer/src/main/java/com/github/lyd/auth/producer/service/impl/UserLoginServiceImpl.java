package com.github.lyd.auth.producer.service.impl;

import com.github.lyd.auth.producer.service.feign.UserAccountRemoteServiceClient;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.security.OpenAuth;
import com.github.lyd.rbac.client.constans.RbacConstans;
import com.github.lyd.rbac.client.dto.UserAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 为了安全这里直接用jdbc 访问用户表, 由于安全限制不能直接使用feign方式获取用户信息
 *
 * @author liuyadu
 */
@Service("userDetailService")
public class UserLoginServiceImpl implements UserDetailsService {

    @Autowired
    private UserAccountRemoteServiceClient userAccountRemoteServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        ResultBody<UserAccountDto> resp = userAccountRemoteServiceClient.login(username);
        UserAccountDto account = resp.getData();
        if (account == null) {
            throw new UsernameNotFoundException("用户 " + username + " 不存在!");
        }
        boolean accountNonLocked = account.getUserProfile().getState().intValue() != RbacConstans.USER_STATE_LOCKED;
        boolean credentialsNonExpired = true;
        boolean enable = account.getUserProfile().getState().intValue() == RbacConstans.USER_STATE_NORMAL ? true : false;
        boolean accountNonExpired = true;
        return new OpenAuth(account.getAccountType(), account.getUserId(), account.getAccount(),account.getUserProfile().getNickName(), account.getPassword(), account.getAuthorities(), accountNonLocked, accountNonExpired, enable, credentialsNonExpired);
    }
}