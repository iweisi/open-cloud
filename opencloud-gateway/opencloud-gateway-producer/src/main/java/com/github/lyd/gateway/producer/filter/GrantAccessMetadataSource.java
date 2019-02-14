package com.github.lyd.gateway.producer.filter;

import com.github.lyd.common.autoconfigure.GatewayProperties;
import com.github.lyd.gateway.producer.locator.GrantAccessLocator;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;

/**
 * 自定义动态权限元数据
 *
 * @author liuyadu
 */
public class GrantAccessMetadataSource implements
        FilterInvocationSecurityMetadataSource {

    private GrantAccessLocator accessLocator;

    private PathMatcher pathMatcher = new AntPathMatcher();
    /**
     * 忽略鉴权
     */
    private List<String> ignores = new ArrayList<>(Arrays.asList(new String[]{
            "/auth/user",
            "/base/grant/login/menus",
            "base/grant/login/actions"
    }));

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
        HashMap<String, Collection<ConfigAttribute>> map = accessLocator.getMap();
        FilterInvocation fi = (FilterInvocation) object;
        // 请求路径path
        String requestUri = fi.getRequest().getRequestURI();
        if (isIgnore(requestUri)) {
            // 返回null，表示不需要鉴权
            return null;
        }
        // 动态权限验证开启
        if (gatewayProperties.getEnabledValidateAccess()) {
            // 匹配动态权限
            for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
                String url = iter.next();
                if (antPathMatcher.match(url, requestUri)) {
                    // 返回匹配到权限
                    return map.get(url);
                }
            }
        }
        // 默认返回表达式权限
        Collection<ConfigAttribute> attributes = expressionSecurityMetadataSource.getAttributes(object);
        // 表达式匹配未放行.则返回默认权限.
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

    protected boolean isIgnore(String requestPath) {
        for (String path : ignores) {
            if (pathMatcher.match(requestPath, path)) {
                return true;
            }
        }
        return false;
    }
}
