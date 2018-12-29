package com.github.lyd.auth.producer.service.impl;

import com.github.lyd.auth.producer.service.feign.SystemAccountApi;
import com.github.lyd.base.client.constants.BaseConstants;
import com.github.lyd.base.client.dto.SystemAccountDto;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.security.OpenUserAuth;
import com.github.lyd.common.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author liuyadu
 */
@Slf4j
@Service("userDetailService")
public class UserLoginServiceImpl implements UserDetailsService {

    @Autowired
    private SystemAccountApi systemAccountApi;
    /**
     * 认证中心名称
     */
    @Value("${spring.application.name}")
    private String AUTH_SERVICE_ID;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        ResultBody<SystemAccountDto> resp = systemAccountApi.login(username);
        SystemAccountDto account = resp.getData();
        if (account == null) {
            throw new UsernameNotFoundException("系统用户 " + username + " 不存在!");
        }
        boolean accountNonLocked = account.getUserProfile().getStatus().intValue() != BaseConstants.USER_STATE_LOCKED;
        boolean credentialsNonExpired = true;
        boolean enable = account.getUserProfile().getStatus().intValue() == BaseConstants.USER_STATE_NORMAL ? true : false;
        boolean accountNonExpired = true;
        Map userProfile =  BeanUtils.beanToMap(account.getUserProfile());
        return new OpenUserAuth(AUTH_SERVICE_ID, account.getUserId(), account.getAccount(), account.getPassword(),account.getUserProfile().getAuthorities(), accountNonLocked, accountNonExpired, enable, credentialsNonExpired,userProfile);
    }
}
