package com.github.lyd.msg.provider.configuration;

import com.github.lyd.msg.client.dto.sms.SmsSender;
import com.github.lyd.msg.provider.exchanger.EmailNoficationExchanger;
import com.github.lyd.msg.provider.exchanger.SmsNotificationExchanger;
import com.github.lyd.msg.provider.exchanger.WebSocketNotificationExchanger;
import com.github.lyd.msg.provider.locator.MailSenderLocator;
import com.github.lyd.msg.provider.service.impl.MailSenderImpl;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author woodev
 */
@Configuration
@EnableAutoConfiguration
@AutoConfigureAfter({SmsConfiguration.class, MailConfiguration.class})
public class NoticationConfiguration {

    @Bean
    public SmsNotificationExchanger smsNotifcationExchanger(SmsSender smsSender){
        return new SmsNotificationExchanger(smsSender);
    }

    @Bean
    public EmailNoficationExchanger emailNoficationExchanger(MailSenderImpl mailSender, MailSenderLocator mailSenderLocator){
        return new EmailNoficationExchanger(mailSender,mailSenderLocator);
    }

    @Bean
    public WebSocketNotificationExchanger webSocketNotificationExchanger(){
        return new WebSocketNotificationExchanger();
    }
}
