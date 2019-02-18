package com.github.lyd.msg.client.model;

import lombok.Data;

/**
 * @author woodev
 */
@Data
public class SmsNotification extends Notification{

    private String phoneNumber;

    private String templateCode;

    private String params;

    private String signName;

}
