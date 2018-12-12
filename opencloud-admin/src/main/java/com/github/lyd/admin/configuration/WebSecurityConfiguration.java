package com.github.lyd.admin.configuration;

import com.github.lyd.common.autoconfigure.GatewayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author liuyadu
 */
@Configuration
@EnableOAuth2Sso
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private GatewayProperties gatewayProperties;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().logout()
                //退出SSO
                .logoutSuccessUrl(gatewayProperties.getAuthServerAddr() + "/logout")
                .and().rememberMe().alwaysRemember(true)
                //暂时禁用CSRF，否则无法提交表单
                .and().csrf().disable().headers().frameOptions().sameOrigin();
    }

}
