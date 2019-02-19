package com.github.lyd.msg.producer.configuration;

import com.alibaba.fastjson.JSONObject;
import com.github.lyd.common.test.BaseTest;
import com.github.lyd.msg.producer.service.MessageSender;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * @author: liuyadu
 * @date: 2019/2/19 15:23
 * @description:
 */
public class MessageTest extends BaseTest {
    @Autowired
    private MessageSender messageSender;

    @Test
    public void delayMsg() throws Exception {
        JSONObject msg = new JSONObject();
        msg.put("content", "延迟消息");
        messageSender.send(msg.toJSONString(), 10 * 10000);
        System.out.println("发送成功");
        Thread.sleep(500000);
    }

    @Test
    public void httpNotify() throws Exception {
        messageSender.sendHttp("http://www.baidu.com/notity/callback", "order_pay", new HashMap<>());
        System.out.println("发送成功");
        Thread.sleep(500000);
    }


    @Test
    public void retryHttpNotify() throws Exception {
        messageSender.sendHttp("73513fabfe3b4b9d8ae5f50fb96b00b8");
        System.out.println("发送成功");
        Thread.sleep(500000);
    }
}
