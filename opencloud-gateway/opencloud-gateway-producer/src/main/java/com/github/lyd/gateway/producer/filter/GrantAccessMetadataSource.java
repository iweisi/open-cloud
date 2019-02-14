package com.github.lyd.gateway.producer.filter;

import com.github.lyd.common.autoconfigure.GatewayProperties;
import com.github.lyd.gateway.producer.locator.GrantAccessLocator;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 自定义动态权限元数据
 *
 * @author liuyadu
 */
public class GrantAccessMetadataSource implements
        FilterInvocationSecurityMetadataSource {

    private GrantAccessLocator accessLocator;

    /**
     * 默认的FilterInvocationSecurityMetadataSource
     */
    private FilterInvocationSecurityMetadataSource expressionSecurityMetadataSource;

    private GatewayProperties gatewayProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public GrantAccessMetadataSource(GrantAccessLocator accessLocator, GatewayProperties gatewayProperties, FilterInvocationSecurityMetadataSource expressionSecurityMetadataSource) {
        this.expressionSecurityMetadataSource = expressionSecurityMetadataSource;
        this.gatewayProperties = gatewayProperties;
        this.accessLocator = accessLocator;
    }

    /**
     * 此方法是为了判定系统用户请求的url 是否在权限表中，如果在权限表中，
     * 返回null或empty,不进入decide验证,
     * 网关接口访问安全性较高,如表达式方式未放行,将返回默认自定义权限"ROLE_ANONYMOUS", "USER_ANONYMOUS", "APP_ANONYMOUS" 给decide方法，强制投票验证访问权限。
     * ROLE_ANONYMOUS-角色匿名权限标识,USER_ANONYMOUS-用户匿名权限标识,APP_ANONYMOUS-应用匿名权限标识
     *
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        Collection<ConfigAttribute> attributes = new ArrayList<>();
        HashMap<String, Collection<ConfigAttribute>> map = accessLocator.getMap();
        FilterInvocation fi = (FilterInvocation) object;
        // 请求路径path
        String requestUri = fi.getRequest().getRequestURI();
        // 动态权限验证开启
        if (gatewayProperties.getEnabledValidateAccess()) {
            // 匹配动态权限
            for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
                String url = iter.next();
                if (antPathMatcher.match(url, requestUri)) {
                    Collection<ConfigAttribute> attrs = map.get(url);
                    if (attrs != null && attrs.size() > 0) {
                        // 动态权限匹配结果
                        attributes.addAll(attrs);
                    }
                    break;
                }
            }
        }
        // 表达式匹配结果
        Collection<ConfigAttribute> expressionAttributes = expressionSecurityMetadataSource.getAttributes(object);
        if (expressionAttributes != null && expressionAttributes.size() > 0) {
            attributes.addAll(expressionAttributes);
        }
        // 表达式匹配未放行.则返回默认权限.
        if (!attributes.toString().contains("permitAll") && !attributes.toString().contains("anonymous")) {
            attributes.addAll(SecurityConfig.createList("ROLE_ANONYMOUS", "USER_ANONYMOUS", "APP_ANONYMOUS"));
        }
        return attributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
