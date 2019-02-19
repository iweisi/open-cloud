package com.github.lyd.msg.producer.configuration;

import com.github.lyd.common.test.BaseTest;
import com.github.lyd.msg.client.dto.EmailNotification;
import com.github.lyd.msg.producer.dispatcher.NotificationDispatcher;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: liuyadu
 * @date: 2018/11/27 14:45
 * @description:
 */
public class MailTest extends BaseTest {
    @Autowired
    private NotificationDispatcher dispatcher;

    @Test
    public void testMail() {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setTo("515608851@qq.com");
        emailNotification.setTitle("测试");
        emailNotification.setContent("测试内容");
        this.dispatcher.dispatch(emailNotification);
        try {
            Thread.sleep(50000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
