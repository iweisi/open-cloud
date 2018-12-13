package com.github.lyd.gateway.producer.configuration;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author: liuyadu
 * @date: 2018/10/23 10:31
 * @description:
 */
@EnableOAuth2Sso
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/", "/**/v2/api-docs/**", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/**/favicon.ico");
    }

}