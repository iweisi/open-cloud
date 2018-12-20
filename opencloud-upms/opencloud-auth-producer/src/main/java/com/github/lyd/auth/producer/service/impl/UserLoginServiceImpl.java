package com.github.lyd.auth.producer.service.impl;

import com.github.lyd.auth.producer.service.feign.SystemAccountApi;
import com.github.lyd.base.client.constants.BaseConstants;
import com.github.lyd.base.client.dto.SystemAccountDto;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.security.OpenAuth;
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
 * @author liuyadu
 */
@Service("userDetailService")
public class UserLoginServiceImpl implements UserDetailsService {

    @Autowired
    private SystemAccountApi systemAccountApi;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        ResultBody<SystemAccountDto> resp = systemAccountApi.login(username);
        SystemAccountDto account = resp.getData();
        if (account == null) {
            throw new UsernameNotFoundException("系统用户 " + username + " 不存在!");
        }
        boolean accountNonLocked = account.getUserProfile().getState().intValue() != BaseConstants.USER_STATE_LOCKED;
        boolean credentialsNonExpired = true;
        boolean enable = account.getUserProfile().getState().intValue() == BaseConstants.USER_STATE_NORMAL ? true : false;
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