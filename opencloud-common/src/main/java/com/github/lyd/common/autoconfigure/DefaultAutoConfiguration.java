package com.github.lyd.common.autoconfigure;

import com.github.lyd.common.annotation.AnnotationScan;
import com.github.lyd.common.exception.OpenExceptionHandler;
import com.github.lyd.common.gen.SnowflakeIdGenerator;
import com.github.lyd.common.http.OpenRestTemplate;
import com.github.lyd.common.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 默认配置类
 *
 * @author liuyadu
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({GatewayProperties.class, IdGenProperties.class})
public class DefaultAutoConfiguration {

    /**
     * 默认加密配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(BCryptPasswordEncoder.class)
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Spring上下文工具配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SpringContextHolder.class)
    public SpringContextHolder springContextHolder() {
        SpringContextHolder holder = new SpringContextHolder();
        return holder;
    }

    /**
     * 统一异常处理配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(OpenExceptionHandler.class)
    public OpenExceptionHandler exceptionHandler() {
        OpenExceptionHandler exceptionHandler = new OpenExceptionHandler();
        log.debug("注入全局异常处理器:{}", exceptionHandler);
        return exceptionHandler;
    }

    /**
     * ID生成器配置
     *
     * @param properties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(IdGenProperties.class)
    public SnowflakeIdGenerator snowflakeIdWorker(IdGenProperties properties) {
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(properties.getWorkId(), properties.getCenterId());
        log.debug("注入snowflakeIdGenerator:{},{}", properties, snowflakeIdGenerator);
        return snowflakeIdGenerator;
    }


    /**
     * 自定义注解扫描
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AnnotationScan.class)
    public AnnotationScan annotationScan() {
        return new AnnotationScan();
    }

    /**
     * 自定义Oauth2请求类
     *
     * @param gatewayProperties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(OpenRestTemplate.class)
    public OpenRestTemplate openRestTemplate(GatewayProperties gatewayProperties) {
        OpenRestTemplate restTemplate = new OpenRestTemplate(gatewayProperties);
        return restTemplate;
    }


}