package com.github.lyd.common.autoconfigure;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;

/**
 * Feign OAuth2 request interceptor.
 * @author  liuyadu
 */
@Slf4j
@Configuration
public class OAuth2FeignAutoConfiguration {

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(OAuth2ClientContext oauth2ClientContext) {
       OAuth2FeignRequestInterceptor interceptor = new OAuth2FeignRequestInterceptor(oauth2ClientContext);
        log.debug("注入OAuth2FeignRequestInterceptor:{}", interceptor);
        return  interceptor;
    }
}