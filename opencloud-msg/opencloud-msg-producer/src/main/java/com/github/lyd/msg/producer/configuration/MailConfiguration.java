package com.github.lyd.msg.producer.configuration;

import com.github.lyd.msg.producer.locator.MailSenderLocator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({OpenMailProperties.class})
public class MailConfiguration {

    @Bean
    @ConditionalOnMissingBean(MailSenderLocator.class)
    public MailSenderLocator mailSenderLocator(OpenMailProperties properties) {
        MailSenderLocator locator = new MailSenderLocator(properties);
        locator.setMailSenders(locator.locateSenders());
        return locator;
    }
}
