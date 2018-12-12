package com.github.lyd.msg.producer.configuration;

import com.github.lyd.common.test.BaseTest;
import com.github.lyd.msg.producer.locator.MailSenderLocator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: liuyadu
 * @date: 2018/11/27 14:45
 * @description:
 */
public class MailTest extends BaseTest {
    @Autowired
    private MailSenderLocator mailConfig;

    @Test
    public void test163() {
    }
}