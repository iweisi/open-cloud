package com.github.lyd.gateway.producer.filter;

import com.github.lyd.common.autoconfigure.GatewayProperties;
import com.github.lyd.gateway.producer.locator.GrantAccessLocator;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
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
     * 网关接口访问安全性较高,如表达式方式没有直接放行,将返回默认自定义权限"ROLE_ANONYMOUS", "USER_ANONYMOUS", "APP_ANONYMOUS" 给decide 方法，强制投票验证访问权限。
     * ROLE_ANONYMOUS-角色匿名权限标识,USER_ANONYMOUS-用户匿名权限标识,APP_ANONYMOUS-应用匿名权限标识
     *
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HashMap<String, Collection<ConfigAttribute>> map = accessLocator.getMap();
        //object 中包含系统用户请求的request 信息
        FilterInvocation fi = (FilterInvocation) object;
        String requestUrl = fi.getRequestUrl();
        // 动态权限验证
        if (gatewayProperties.getEnabledValidateAccess()) {
            for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
                String url = iter.next();
                if (antPathMatcher.match(url, requestUrl)) {
                    // 返回匹配到权限
                    return map.get(url);
                }
            }
        }
        Collection<ConfigAttribute> attributes = expressionSecurityMetadataSource.getAttributes(object);
        // 表达式匹配未直接放行,并且开启动态权限验证.则返回默认权限.
        if (!attributes.toString().contains("permitAll")) {
            attributes = SecurityConfig.createList("ROLE_ANONYMOUS", "USER_ANONYMOUS", "APP_ANONYMOUS");
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
