package com.github.lyd.gateway.producer.configuration;

import com.github.lyd.common.autoconfigure.GatewayProperties;
import com.github.lyd.common.exception.OpenAccessDeniedHandler;
import com.github.lyd.common.exception.OpenAuthenticationEntryPoint;
import com.github.lyd.common.security.OpenHelper;
import com.github.lyd.gateway.producer.filter.*;
import com.github.lyd.gateway.producer.locator.PermissionLocator;
import com.github.lyd.gateway.producer.service.feign.AppDetailsRemoteServiceClient;
import com.github.lyd.rbac.client.constans.RbacConstans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源服务配置
 *
 * @author: liuyadu
 * @date: 2018/10/23 10:31
 * @description:
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Autowired
    private ResourceServerProperties properties;
    @Autowired
    private PermissionLocator permissionLocator;
    @Autowired
    private GatewayProperties gatewayProperties;
    @Autowired
    private AppDetailsRemoteServiceClient appInfoRemoteServiceClient;


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(OpenHelper.buildRemoteTokenServices(properties));
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                //放行自定义Oauth2登录
                .antMatchers("/platform/login").permitAll()
                //只有超级管理员角色可执行远程端点
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasAuthority(RbacConstans.SUPER_AUTHORITY)
                .anyRequest().authenticated()
                .and().logout()
                .logoutSuccessUrl(gatewayProperties.getAuthServerAddr() + "/logout")
                .and()
                //认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .exceptionHandling()
                .accessDeniedHandler(new OpenAccessDeniedHandler())
                .authenticationEntryPoint(new OpenAuthenticationEntryPoint())
                .and()
                .csrf().disable();
        /**
         * 增加签名过滤器
         */
        http.addFilterAfter(new SignatureFilter(appInfoRemoteServiceClient, gatewayProperties), AbstractPreAuthenticatedProcessingFilter.class);
        /**
         * 自定义动态权限过滤器
         */
        http.addFilterBefore(new AccessUrlFilter(accessDecisionManager(), new AccessUrlMetadataSource(permissionLocator)), FilterSecurityInterceptor.class);
    }

    /**
     * 权限管理,投票器
     */
    private AbstractAccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList();
        decisionVoters.add(new AuthenticatedVoter());
        //自定义URL投票器
        AccessUrlVoter accessUrlVoter = new AccessUrlVoter(gatewayProperties);
        decisionVoters.add(accessUrlVoter);
        //自定义角色投票器,默认前缀为ROLE_
        decisionVoters.add(new AccessRoleVoter());
        //特殊权限投票器,修改前缀为USER_
        AccessRoleVoter accessRoleVoter = new AccessRoleVoter();
        accessRoleVoter.setRolePrefix(RbacConstans.PERMISSION_IDENTITY_PREFIX_USER);
        decisionVoters.add(accessRoleVoter);
        AbstractAccessDecisionManager accessDecisionManager = new AffirmativeBased(decisionVoters);
        return accessDecisionManager;
    }

}

