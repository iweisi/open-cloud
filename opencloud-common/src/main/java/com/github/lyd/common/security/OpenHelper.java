package com.github.lyd.common.security;

import com.github.lyd.common.utils.BeanUtils;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

import java.util.Map;

/**
 * @author liuyadu
 */
public class OpenHelper {
    /**
     * 获取登陆系统用户
     *
     * @return
     */
    public static OpenAuth getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() != null) {
            if (authentication.getPrincipal() instanceof OpenAuth) {
                return (OpenAuth) authentication.getPrincipal();
            }
            if (authentication.getPrincipal() instanceof Map) {
                return BeanUtils.mapToBean((Map) authentication.getPrincipal(), OpenAuth.class);
            }
        }
        return null;
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
