package com.github.lyd.gateway.producer.filter;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.servlet.*;
import java.io.IOException;

/**
 * 自定义资源访问权限过滤器
 *
 * @author liuyadu
 */
public class AccessUrlFilter extends FilterSecurityInterceptor implements Filter {

    public AccessUrlFilter(AccessDecisionManager accessDecisionManager, FilterInvocationSecurityMetadataSource securityMetadataSource) {
        super();
        super.setAccessDecisionManager(accessDecisionManager);
        super.setSecurityMetadataSource(securityMetadataSource);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);
    }
}