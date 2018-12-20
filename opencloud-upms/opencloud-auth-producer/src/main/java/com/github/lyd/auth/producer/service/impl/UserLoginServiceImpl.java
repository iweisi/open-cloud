package com.github.lyd.auth.producer.service.impl;

import com.github.lyd.auth.producer.service.feign.SystemLoginAccountApi;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.security.OpenAuth;
import com.github.lyd.base.client.constans.RbacConstans;
import com.github.lyd.base.client.dto.SystemLoginAccountDto;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 为了安全这里直接用jdbc 访问系统用户表, 由于安全限制不能直接使用feign方式获取系统用户信息
 *
 * @author liuyadu
 */
@Service("userDetailService")
public class UserLoginServiceImpl implements UserDetailsService {

    @Autowired
    private SystemLoginAccountApi systemLoginAccountApi;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        ResultBody<SystemLoginAccountDto> resp = systemLoginAccountApi.login(username);
        SystemLoginAccountDto account = resp.getData();
        if (account == null) {
            throw new UsernameNotFoundException("系统用户 " + username + " 不存在!");
        }
        boolean accountNonLocked = account.getUserProfile().getState().intValue() != RbacConstans.USER_STATE_LOCKED;
        boolean credentialsNonExpired = true;
        boolean enable = account.getUserProfile().getState().intValue() == RbacConstans.USER_STATE_NORMAL ? true : false;
        boolean accountNonExpired = true;
        List<Map> roles = Lists.newArrayList();
        if (account.getRoles() != null) {
            account.getRoles().forEach(role -> {
                Map map = Maps.newHashMap();
                map.put("code", role.getRoleCode());
                map.put("name", role.getRoleName());
                roles.add(map);
            });
        }
        return new OpenAuth(account.getAccountType(), account.getUserId(),account.getUserProfile().getAvatar(), account.getAccount(), account.getUserProfile().getNickName(), account.getPassword(), roles, account.getAuthorities(), accountNonLocked, accountNonExpired, enable, credentialsNonExpired);
    }
}