package com.github.lyd.msg.producer.task;

import com.github.lyd.msg.client.dto.Notification;
import com.github.lyd.msg.producer.exchanger.NotificationExchanger;

import java.util.concurrent.Callable;

/**
 * @author woodev
 */
public class NotificationTask implements Callable<Boolean> {

    private NotificationExchanger notificationExchanger;

    private Notification notification;

    public NotificationTask(NotificationExchanger notificationExchanger, Notification notification){
        this.notificationExchanger = notificationExchanger;
        this.notification = notification;
    }

    @Override
    public Boolean call() throws Exception {
        return notificationExchanger.exchange(notification);
    }
}
