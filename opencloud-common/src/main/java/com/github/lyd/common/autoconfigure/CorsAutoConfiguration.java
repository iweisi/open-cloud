package com.github.lyd.common.autoconfigure;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域auto-configuration
 *
 * @author admin
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(CorsProperties.class)
@ConditionalOnProperty(prefix = "opencloud.cors", name = "enabled", havingValue = "true")
public class CorsAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(FilterRegistrationBean.class)
    public FilterRegistrationBean corsFilter(CorsProperties corsProperties) {
        log.debug("跨域配置:{}", corsProperties);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(corsProperties.getAllowCredentials());
        config.setAllowedOrigins(Lists.newArrayList(corsProperties.getAllowedOrigin().split(",")));
        config.setAllowedMethods(Lists.newArrayList(corsProperties.getAllowedMethod().split(",")));
        config.setAllowedHeaders(Lists.newArrayList(corsProperties.getAllowedHeader().split(",")));
        config.setMaxAge(corsProperties.getMaxAge());
        config.addExposedHeader("Authorization");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        //最大优先级,设置0不好使
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
