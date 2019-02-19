package com.github.lyd.msg.client.constants;

/**
 * @author liuyadu
 */
public class MessageConstants {
    /**
     * 默认消息队列
     */
    public static final String QUEUE_MSG = "openCloud.msg.queue";
    /**
     * 延迟消息队列
     */
    public static final String QUEUE_MSG_DELAY = "openCloud.msg.queue.delay";
    /**
     * 默认消息队列交换器
     */
    public static final String EXCHANGE = "openCloud.msg.exchange";
    /**
     * 延迟消息队列交换器
     */
    public static final String EXCHANGE_DELAY = "openCloud.msg.exchange.delay";

    /**
     * 默认消息路由key
     */
    public static final String RK_MSG = "openCloud.msg.rk";

    /**
     * 延迟消息路由key
     */
    public static final String RK_MSG_DELAY = "openCloud.msg.rk.delay";


    /**
     * 消息类型:HTTP异步通知
     */
    public static final String HTTP_NOTIFY_TYPE = "http_notify";
}
