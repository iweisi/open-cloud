package com.github.lyd.msg.producer.dispatcher;

import com.github.lyd.msg.client.constants.MessageConstants;
import com.github.lyd.msg.client.model.Notification;
import com.github.lyd.msg.producer.exchanger.NotificationExchanger;
import com.github.lyd.msg.producer.task.NotificationTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author woodev
 */
@Component
@RabbitListener(queues = MessageConstants.QUEUE_MSG)
@Slf4j
public class NotificationDispatcher implements ApplicationContextAware{

    private Collection<NotificationExchanger> exchangers;


    @Bean
    public Queue notificationQueue(){
        return new Queue(MessageConstants.QUEUE_MSG);
    }

    private ExecutorService executorService;

    public NotificationDispatcher(){
        Integer availableProcessors = Runtime.getRuntime().availableProcessors();
        Integer numOfThreads = availableProcessors * 2;
        executorService = new ThreadPoolExecutor(numOfThreads,numOfThreads,0,TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>());
        log.info("Init Notification ExecutorService , numOfThread : " + numOfThreads);
    }

    @RabbitHandler
    public void dispatch(@Payload Notification notification){
        if(notification != null && exchangers != null){
            exchangers.forEach((exchanger) -> {
                if(exchanger.support(notification)){
                    //添加到线程池进行处理
                   executorService.submit(new NotificationTask(exchanger,notification));
                }
            });
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String,NotificationExchanger> beansOfType = applicationContext.getBeansOfType(NotificationExchanger.class);
        this.exchangers = beansOfType.values();
    }
}
