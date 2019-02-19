package com.github.lyd.msg.producer.service;

import java.util.Map;

/**
 * @author: liuyadu
 * @date: 2019/2/13 14:39
 * @description:
 */
public interface MessageSender {

    /**
     * 发送延迟消息
     * 默认队列msg_queue
     *
     * @param msg   消息内容
     * @param times 延迟时间 毫秒
     */
    void send(String msg, long times) throws Exception;


    /**
     * 发送延迟消息
     * 默认队列msg_queue
     *
     * @param msg     消息内容
     * @param msgType 消息类型
     * @param times   延迟时间 毫秒
     */
    void send(String msgId, String msg, String msgType, long times) throws Exception;

    /**
     * 发送非延迟消息
     * 默认队列msg_queue
     *
     * @param msg
     */
    void send(String msg) throws Exception;

    /**
     * 发送非延迟消息
     * 默认队列msg_queue
     *
     * @param msg
     * @param msgType 消息类型
     */
    void send(String msgId, String msg, String msgType) throws Exception;


    /**
     * 发送Http通知
     * 首次是即时推送，重试通知时间间隔为 5s、10s、2min、5min、10min、30min、1h、2h、6h、15h，直到你正确回复状态 200 并且返回 success 或者超过最大重发次数
     *
     * @param url  通知地址
     * @param type 通知类型:自定义字符串,可以为空
     * @param data 请求数据
     */
    void sendHttp(String url, String type, Map<String, String> data) throws Exception;


    /**
     * 手动重新通知
     * @param msgId
     */
    void sendHttp(String msgId) throws Exception;
}
