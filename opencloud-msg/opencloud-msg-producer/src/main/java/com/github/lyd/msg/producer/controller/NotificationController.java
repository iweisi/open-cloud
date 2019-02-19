package com.github.lyd.msg.producer.controller;

import com.github.lyd.common.model.ResultBody;
import com.github.lyd.msg.client.api.NotificationRemoteService;
import com.github.lyd.msg.client.dto.EmailNotification;
import com.github.lyd.msg.client.dto.HttpNotification;
import com.github.lyd.msg.client.dto.SmsNotification;
import com.github.lyd.msg.producer.dispatcher.NotificationDispatcher;
import com.github.lyd.msg.producer.service.MessageSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author woodev
 */
@RestController
@Api(value = "通知管理", tags = "通知管理")
public class NotificationController implements NotificationRemoteService {

    @Autowired
    private NotificationDispatcher dispatcher;
    @Autowired
    private MessageSender messageSender;

    @ApiOperation("短信通知")
    @PostMapping("/notification/send/sms")
    @Override
    public ResultBody<String> sendSms(
            @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
            @RequestParam(value = "templateCode", required = true) String templateCode,
            @RequestParam(value = "signName", required = false) String signName,
            @RequestParam(value = "params", required = false) String params
    ) {
        SmsNotification smsNotification = new SmsNotification();
        smsNotification.setPhoneNumber(phoneNumber);
        smsNotification.setTemplateCode(templateCode);
        smsNotification.setSignName(signName);
        smsNotification.setParams(params);
        this.dispatcher.dispatch(smsNotification);
        return ResultBody.success("");
    }

    @ApiOperation("邮件通知")
    @PostMapping("/notification/send/email")
    @Override
    public ResultBody<String> sendEmail(
            @RequestParam(value = "to", required = true) String to,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "content", required = true) String content
    ) {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setTo(to);
        emailNotification.setTitle(title);
        emailNotification.setContent(content);
        this.dispatcher.dispatch(emailNotification);
        return ResultBody.success("");
    }


    @ApiOperation("HTTP异步通知")
    @PostMapping("/notification/send/http")
    @Override
    public ResultBody<String> sendHttp(
            @RequestBody HttpNotification notification
    ) {
        try {
            messageSender.sendHttp(notification.getUrl(), notification.getType(), notification.getData());
            return ResultBody.success("");
        } catch (Exception e) {
            return ResultBody.failed(e.getMessage());
        }
    }


}
