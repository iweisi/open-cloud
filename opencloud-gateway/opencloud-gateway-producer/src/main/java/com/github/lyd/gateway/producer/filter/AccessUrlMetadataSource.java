package com.github.lyd.gateway.producer.filter;

import com.github.lyd.gateway.producer.locator.PermissionLocator;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author liuyadu
 */
public class AccessUrlMetadataSource implements
        FilterInvocationSecurityMetadataSource {

    private PermissionLocator permissionLocator;

    public AccessUrlMetadataSource(PermissionLocator permissionLocator) {
        this.permissionLocator = permissionLocator;
    }

    /**
     * 此方法是为了判定租户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定租户是否有此权限。
     *
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HashMap<String, Collection<ConfigAttribute>> map = permissionLocator.getMap();
        //object 中包含租户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        // 防止为空,否则不进入,accessDecisionManager
        // 随便给一个无效的权限,让投票器决策.
        Collection<ConfigAttribute> array = new ArrayList<>();
        array.add(new SecurityConfig("PERMISSION_NOT_FOUND"));
        return array;
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
