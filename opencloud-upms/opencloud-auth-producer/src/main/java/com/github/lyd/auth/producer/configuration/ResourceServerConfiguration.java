package com.github.lyd.auth.producer.configuration;

import com.github.lyd.auth.producer.service.feign.TenantAccountRemoteServiceClient;
import com.github.lyd.auth.producer.service.impl.UserLoginServiceImpl;
import com.github.lyd.common.exception.OpenAccessDeniedHandler;
import com.github.lyd.common.exception.OpenAuthenticationEntryPoint;
import com.github.lyd.common.security.OpenAuth;
import com.github.lyd.common.security.OpenHelper;
import com.github.lyd.rbac.client.constans.RbacConstans;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

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
    private TenantAccountRemoteServiceClient tenantAccountRemoteServiceClient;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;
    @Autowired
    private UserLoginServiceImpl userDetailsService;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        // org.springframework.security.config.annotation.web.configurers.RememberMeConfigurer.tokenRepository
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 该对象里面有定义创建表的语句
        // 可以设置让该类来创建表
        // 但是该功能只用使用一次，如果数据库已经存在表则会报错
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(OpenHelper.buildRemoteTokenServices(properties));
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                //只有超级管理员角色可执行远程端点
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasAuthority(RbacConstans.SUPER_AUTHORITY)
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .successHandler(new SuccessHandler())
                .and()
                .logout().permitAll()
                // /logout退出清除cookie
                .addLogoutHandler(new CookieClearingLogoutHandler("token", "remember-me"))
                .logoutSuccessHandler(new LogoutSuccessHandler())
                .and()
                // 从这里开始配置记住我的功能
                .rememberMe()
                .userDetailsService(userDetailsService)
                .tokenRepository(persistentTokenRepository)
                // 新增过期配置，单位秒，默认配置写的60秒
                .tokenValiditySeconds(60 * 60 * 24)
                .and()
                //认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .exceptionHandling()
                .accessDeniedHandler(new OpenAccessDeniedHandler())
                .authenticationEntryPoint(new OpenAuthenticationEntryPoint())
                .and()
                .csrf().disable().httpBasic().disable();

    }


    static class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
        public LogoutSuccessHandler() {
            // 重定向到原地址
            this.setUseReferer(true);
        }

        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            super.onLogoutSuccess(request, response, authentication);
        }
    }

    /**
     * 使用SavedRequestAwareAuthenticationSuccessHandler支持跳转到请求发起页面
     */
    private class SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            try {
                OpenAuth userLoginDetails = (OpenAuth) authentication.getPrincipal();
                WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
                //添加登录日志
                tenantAccountRemoteServiceClient.addLoginLog(userLoginDetails.getTenantId(), webAuthenticationDetails.getRemoteAddress(), request.getHeader("User-Agent"));
                log.debug("添加登录日志:{} {}", userLoginDetails.getUsername(), webAuthenticationDetails.getRemoteAddress());
            } catch (Exception e) {
                log.error("invoke addLoginLog api error:{}",e.getMessage());
            }
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

}

