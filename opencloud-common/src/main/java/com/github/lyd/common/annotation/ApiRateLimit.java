package com.github.lyd.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * API限流注解
 * 详细参考
 * https://github.com/marcosbarbero/spring-cloud-zuul-ratelimit
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiRateLimit {
    /**
     * 不指定类型,将作为全局限流配置
     * 限流规则内容
     * user=anonymous
     * origin=somemachine.com
     * url=/api #url prefix
     * role=user
     * @return
     */
    String[] types() default {};

    /**
     * 单位时间内允许访问的个数限制数量
     *
     * @return
     */
    int limit() default 10;

    /**
     * 单位时间内允许访问的总时间(秒)
     */
    int quota() default 1000;

    /**
     * 时间间隔(秒)
     *
     * @return
     */
    int interval() default 60;
}
