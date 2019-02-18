package com.github.lyd.msg.producer.exchanger;


import com.github.lyd.msg.client.model.Notification;

/**
 * @author woodev
 */
public interface NotificationExchanger {

    boolean support(Object notification);

    boolean exchange(Notification notification);
}
