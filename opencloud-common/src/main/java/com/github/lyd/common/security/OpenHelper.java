package com.github.lyd.common.security;

import com.github.lyd.common.utils.BeanConvertUtils;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

import java.util.Map;

/**
 * 认证信息帮助类
 * @author liuyadu
 */
public class OpenHelper {
    /**
     * 获取登录用户认证信息
     *
     * @return
     */
    public static OpenUserAuth getUserAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() != null) {
            if (authentication.getPrincipal() instanceof OpenUserAuth) {
                return (OpenUserAuth) authentication.getPrincipal();
            }
            if (authentication.getPrincipal() instanceof Map) {
                return BeanConvertUtils.mapToObject((Map) authentication.getPrincipal(), OpenUserAuth.class);
            }
        }
        return null;
    }

    /**
     * 获取登录用户详细资料
     *
     * @return
     */
    public static Map getUserProfile() {
        OpenUserAuth userAuth = getUserAuth();
        if (userAuth != null) {
            return userAuth.getUserProfile();
        }
        return null;
    }

    public static Boolean hasAuthority(String authority) {
        OpenUserAuth auth = getUserAuth();
        if (auth == null) {
            return false;
        }
        if (AuthorityUtils.authorityListToSet(auth.getAuthorities()).contains(authority)) {
            return true;
        }
        return false;
    }

    /**
     * 构建token转换器
     *
     * @return
     */
    public static DefaultAccessTokenConverter buildAccessTokenConverter() {
        OpenUserAuthenticationConverter userAuthenticationConverter = new OpenUserAuthenticationConverter();
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
        return accessTokenConverter;
    }

    /**
     * 构建自定义远程Token服务类
     *
     * @param properties
     * @return
     */
    public static RemoteTokenServices buildRemoteTokenServices(ResourceServerProperties properties) {
        // 使用自定义系统用户凭证转换器
        DefaultAccessTokenConverter accessTokenConverter = buildAccessTokenConverter();
        RemoteTokenServices services = new RemoteTokenServices();
        services.setCheckTokenEndpointUrl(properties.getTokenInfoUri());
        services.setClientId(properties.getClientId());
        services.setClientSecret(properties.getClientSecret());
        services.setAccessTokenConverter(accessTokenConverter);
        return services;
    }
}
