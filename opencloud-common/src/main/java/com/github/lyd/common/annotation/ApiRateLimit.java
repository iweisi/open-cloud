package com.github.lyd.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * API限流注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiRateLimit {
    /**
     * 限流规则内容
     * type = RateLimitType.URL ,不需要填写自动获取controller方法上的路径
     * type = RateLimitType.IP  ,填写ip地址
     * type = RateLimitType.USER ,填写用户信息
     *
     * @return
     */
    String value() default "";

    /**
     * 限制数量
     *
     * @return
     */
    int limit() default 10;

    /**
     * 时间间隔(秒)
     *
     * @return
     */
    int interval() default 1;

    /**
     * 限流类型
     *
     * @return
     */
    ApiRateLimitType type() default ApiRateLimitType.URL;
}
