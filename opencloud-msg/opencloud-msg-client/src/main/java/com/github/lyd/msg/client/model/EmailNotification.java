package com.github.lyd.msg.client.model;

import lombok.Data;

/**
 * @author woodev
 */
@Data
public class EmailNotification extends Notification{

    private String title;

    private String content;

    private String to;

}
