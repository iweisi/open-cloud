package com.github.lyd.msg.client.api;

import com.github.lyd.common.model.ResultBody;
import com.github.lyd.msg.client.dto.HttpNotification;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author woodev
 */
public interface NotificationRemoteService {
    /**
     * 短信通知
     *
     * @param phoneNumber
     * @param templateCode
     * @param signName
     * @param params
     * @return
     */
    @ApiOperation("短信通知")
    @PostMapping("/notify/send/sms")
    ResultBody<String> sendSms(
            @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
            @RequestParam(value = "templateCode", required = true) String templateCode,
            @RequestParam(value = "signName", required = false) String signName,
            @RequestParam(value = "params", required = false) String params
    );

    /**
     * 邮件通知
     *
     * @param to
     * @param title
     * @param content
     * @return
     */
    @ApiOperation("邮件通知")
    @PostMapping("/notify/send/email")
    ResultBody<String> sendEmail(
            @RequestParam(value = "to", required = true) String to,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "content", required = true) String content
    );


    /**
     * HTTP异步通知
     *
     * @param notification
     * @return
     */
    @ApiOperation("HTTP异步通知")
    @PostMapping("/notify/send/http")
    ResultBody<String> sendHttp(
            @RequestBody HttpNotification notification
    );
}
