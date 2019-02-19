package com.github.lyd.msg.producer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.lyd.msg.client.constants.MessageConstants;
import com.github.lyd.msg.client.dto.HttpNotification;
import com.github.lyd.msg.client.entity.MessageHttpNotifyLogs;
import com.github.lyd.msg.producer.service.HttpNotifyLogsService;
import com.github.lyd.msg.producer.service.MessageSender;
import com.google.common.collect.Maps;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * 消息发送实现类
 *
 * @author liuyadu
 */
@Service
public class MessageSenderImpl implements MessageSender {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private HttpNotifyLogsService httpNotifyLogsService;

    /**
     * 发送延迟消息
     * 默认队列msg_queue
     *
     * @param msg   消息内容
     * @param times 延迟时间 毫秒
     */
    @Override
    public void send(String msg, long times) throws Exception {
        send(null, msg, null, times);
    }

    /**
     * 延迟消息放入延迟队列中
     *
     * @param msg     消息内容
     * @param msgType 消息类型
     * @param times   延迟时间 毫秒
     */
    @Override
    public void send(String msgId, String msg, String msgType, long times) throws Exception {
        String exp = String.valueOf(times);
        amqpTemplate.convertAndSend(MessageConstants.EXCHANGE_DELAY, MessageConstants.RK_MSG_DELAY, msg, message -> {
            String messageId = msgId;
            if (StringUtils.isEmpty(messageId)) {
                messageId = UUID.randomUUID().toString().replace("-", "");
            }
            message.getMessageProperties().setMessageId(messageId);
            message.getMessageProperties().setTimestamp(new Date());
            message.getMessageProperties().setType(msgType);
            // 设置消息属性-过期时间
            message.getMessageProperties().setExpiration(exp);
            return message;
        });
    }

    /**
     * 发送非延迟消息
     * 默认队列msg_queue
     *
     * @param msg
     */
    @Override
    public void send(String msg) throws Exception {
        send(null, msg, null);
    }

    /**
     * 非延迟消息放入待消费队列
     *
     * @param msg     消息内容
     * @param msgType 消息类型
     */
    @Override
    public void send(String msgId, String msg, String msgType) throws Exception {
        amqpTemplate.convertAndSend(MessageConstants.EXCHANGE, MessageConstants.RK_MSG, msg, message -> {
            String messageId = msgId;
            if (StringUtils.isEmpty(messageId)) {
                messageId = UUID.randomUUID().toString().replace("-", "");
            }
            message.getMessageProperties().setType(msgType);
            message.getMessageProperties().setTimestamp(new Date());
            message.getMessageProperties().setMessageId(messageId);
            return message;
        });
    }

    /**
     * 发送Http通知
     * 首次是即时推送，重试通知时间间隔为 5s、10s、2min、5min、10min、30min、1h、2h、6h、15h，直到你正确回复状态 200 并且返回 success 或者超过最大重发次数
     *
     * @param url  通知地址
     * @param type 通知类型:自定义字符串,可以为空
     * @param data 请求数据
     */
    @Override
    public void sendHttp(String url, String type, Map<String, String> data) throws Exception {
        if (StringUtils.isEmpty(url)) {
            throw new Exception("url is not empty");
        }
        if (data == null) {
            data = Maps.newHashMap();
        }
        HttpNotification msg = new HttpNotification(url, type, data);
        send(null, JSONObject.toJSONString(msg), MessageConstants.HTTP_NOTIFY_TYPE);
    }

    /**
     * 手动重新通知
     *
     * @param msgId
     */
    @Override
    public void sendHttp(String msgId) throws Exception {
        MessageHttpNotifyLogs log = httpNotifyLogsService.getLog(msgId);
        if (log == null) {
            throw new Exception("消息msgId={}不存在！");
        }
        Map<String, String> data = JSONObject.parseObject(log.getData(), Map.class);
        HttpNotification msg = new HttpNotification(log.getUrl(), log.getType(), data);
        send(log.getMsgId(), JSONObject.toJSONString(msg), MessageConstants.HTTP_NOTIFY_TYPE);
    }
}