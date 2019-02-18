package com.github.lyd.msg.producer.exchanger;

import com.github.lyd.msg.client.model.Notification;
import com.github.lyd.msg.client.model.SmsNotification;
import com.github.lyd.msg.client.model.sms.SmsParameter;
import com.github.lyd.msg.client.model.sms.SmsSendResult;
import com.github.lyd.msg.client.model.sms.SmsSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * @author woodev
 */
@Slf4j
public class SmsNotificationExchanger implements NotificationExchanger {

    private SmsSender smsSender;

    private final static String STATUS_OK = "OK";

    public SmsNotificationExchanger(SmsSender smsSender) {
        if (smsSender != null) {
            log.info("初始化短信通知组件");
        }
        this.smsSender = smsSender;
    }

    private String signName = "签名";

    @Override
    public boolean support(Object notification) {
        return notification.getClass().equals(SmsNotification.class);
    }

    @Override
    public boolean exchange(Notification notification) {

        Assert.notNull(smsSender, "短信接口没有初始化");

        SmsNotification smsNotification = (SmsNotification) notification;
        SmsParameter parameter = new SmsParameter();
        parameter.setPhoneNumbers(Arrays.asList(smsNotification.getPhoneNumber()));
        parameter.setTemplateCode(smsNotification.getTemplateCode());

        if(StringUtils.isEmpty(smsNotification.getSignName())){
            smsNotification.setSignName(this.signName);
        }

        Assert.notNull(smsNotification.getSignName(),"短信签名不能为空");

        parameter.setSignName(smsNotification.getSignName());
        parameter.setParams(smsNotification.getParams());
        SmsSendResult smsSendResult = smsSender.send(parameter);
        return STATUS_OK.equals(smsSendResult.getCode());
    }
}
