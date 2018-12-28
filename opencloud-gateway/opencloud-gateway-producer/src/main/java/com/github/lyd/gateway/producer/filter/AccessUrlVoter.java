package com.github.lyd.gateway.producer.filter;

import com.github.lyd.common.autoconfigure.GatewayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 自定义URL投票器
 *
 * @author liuyadu
 */
@Slf4j
public class AccessUrlVoter extends WebExpressionVoter {

    private GatewayProperties gatewayProperties;

    public AccessUrlVoter(GatewayProperties gatewayProperties) {
        this.gatewayProperties = gatewayProperties;
    }

    public Collection<? extends GrantedAuthority> extractAuthorities(Authentication authentication) {
        return authentication.getAuthorities();
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation filterInvocation, Collection<ConfigAttribute> attributes) {
        HttpServletRequest request = filterInvocation.getHttpRequest();
        if (gatewayProperties != null && !gatewayProperties.getEnabledValidateAccess()) {
            log.debug("enabledValidateAccess:{}", gatewayProperties.getEnabledValidateAccess());
            return 1;
        }
        // 获取当前系统用户,认证通过就可以访问
        AntPathRequestMatcher pathMatcher = new AntPathRequestMatcher("/auth/user");
        if (authentication.isAuthenticated() && pathMatcher.matches(request)) {
            return 1;
        }
        return super.vote(authentication, filterInvocation, attributes);
    }
}
