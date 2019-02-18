package com.github.lyd.msg.client.api;

import com.github.lyd.common.model.ResultBody;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author woodev
 */
public interface NotificationRemoteService {
    @ApiOperation("/短信通知")
    @PostMapping("/send/sms")
    ResultBody<String> sendSms(
            @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
            @RequestParam(value = "templateCode", required = true) String templateCode,
            @RequestParam(value = "signName", required = false) String signName,
            @RequestParam(value = "params", required = false) String params
    );

    @ApiOperation("/邮件通知")
    @PostMapping("/send/email")
    ResultBody<String> sendEmail(
            @RequestParam(value = "to", required = true) String to,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "content", required = true) String content
    );
}
