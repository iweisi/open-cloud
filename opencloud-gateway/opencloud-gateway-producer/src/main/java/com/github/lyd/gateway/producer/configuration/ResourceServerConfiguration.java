package com.github.lyd.gateway.producer.configuration;

import com.github.lyd.base.client.constants.BaseConstants;
import com.github.lyd.common.autoconfigure.GatewayProperties;
import com.github.lyd.common.exception.OpenAccessDeniedHandler;
import com.github.lyd.common.exception.OpenAuthenticationEntryPoint;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.security.OpenHelper;
import com.github.lyd.common.utils.WebUtils;
import com.github.lyd.gateway.producer.filter.AccessUrlFilter;
import com.github.lyd.gateway.producer.filter.AccessUrlMetadataSource;
import com.github.lyd.gateway.producer.filter.AccessUrlVoter;
import com.github.lyd.gateway.producer.filter.SignatureFilter;
import com.github.lyd.gateway.producer.locator.AccessLocator;
import com.github.lyd.gateway.producer.service.feign.SystemAppApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源服务配置
 *
 * @author: liuyadu
 * @date: 2018/10/23 10:31
 * @description:
 */
@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Autowired
    private ResourceServerProperties properties;
    @Autowired
    private AccessLocator permissionLocator;
    @Autowired
    private GatewayProperties gatewayProperties;
    @Autowired
    private SystemAppApi systemAppApi;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(OpenHelper.buildRemoteTokenServices(properties));
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                // 放行自定义Oauth2登录
                .antMatchers("/login/token").permitAll()
                // 只有超级管理员角色可执行远程端点
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole(BaseConstants.SUPER_ROLE)
                .anyRequest().authenticated()
                // SSO退出
                .and().logout().logoutSuccessHandler(new SsoLogoutSuccessHandler(gatewayProperties.getServerAddr() + "/auth/logout", restTemplate))
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
        http.addFilterAfter(new SignatureFilter(systemAppApi, gatewayProperties), AbstractPreAuthenticatedProcessingFilter.class);
        /**
         * 自定义动态权限过滤器
         */
        http.addFilterBefore(new AccessUrlFilter(accessDecisionManager(), new AccessUrlMetadataSource(permissionLocator)), FilterSecurityInterceptor.class);

    }

    static class SsoLogoutSuccessHandler implements LogoutSuccessHandler {
        private String defaultTargetUrl;
        private RestTemplate restTemplate;

        public SsoLogoutSuccessHandler(String defaultTargetUrl, RestTemplate restTemplate) {
            this.defaultTargetUrl = defaultTargetUrl;
            this.restTemplate = restTemplate;
        }

        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            try {
                restTemplate.getForEntity(defaultTargetUrl, String.class);
            } catch (Exception e) {
                log.error("sso logout error:",e);
            }
            WebUtils.writeJson(response, ResultBody.success("退出成功", null));
        }
    }

    /**
     * 权限管理,投票器
     */
    private AbstractAccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList();
        //自定义URL投票器
        AccessUrlVoter accessUrlVoter = new AccessUrlVoter(gatewayProperties);
        //默认角色投票器,默认前缀为ROLE_
        RoleVoter roleVoter = new RoleVoter();
        //用户权限投票器,修改前缀为USER_
        RoleVoter userVoter = new RoleVoter();
        userVoter.setRolePrefix(BaseConstants.AUTHORITY_PREFIX_USER);
        //应用权限投票器,修改前缀为APP_
        RoleVoter appVoter = new RoleVoter();
        appVoter.setRolePrefix(BaseConstants.AUTHORITY_PREFIX_APP);
        decisionVoters.add(roleVoter);
        decisionVoters.add(userVoter);
        decisionVoters.add(appVoter);
        decisionVoters.add(accessUrlVoter);
        AbstractAccessDecisionManager accessDecisionManager = new AffirmativeBased(decisionVoters);
        return accessDecisionManager;
    }

}

