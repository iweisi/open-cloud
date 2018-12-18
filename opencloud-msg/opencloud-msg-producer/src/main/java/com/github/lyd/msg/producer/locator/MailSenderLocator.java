package com.github.lyd.msg.producer.locator;

import com.github.lyd.msg.producer.configuration.MailChannelsProperties;
import com.google.common.collect.Maps;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author: liuyadu
 * @date: 2018/11/27 14:19
 * @description:
 */
public class MailSenderLocator {
    private MailChannelsProperties mailProperties;

    public MailSenderLocator(MailChannelsProperties mailProperties) {
        this.mailProperties = mailProperties;
    }

    private Map<String, JavaMailSender> mailSenders = Maps.newLinkedHashMap();

    public Map<String, JavaMailSender> locateSenders() {
        Map<String, JavaMailSender> senders = Maps.newLinkedHashMap();
        if (this.mailProperties != null && this.mailProperties.getChannels() != null) {
            Iterator<Map.Entry<String, MailProperties>> entries = mailProperties.getChannels().entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, MailProperties> entry = entries.next();
                MailProperties properties = entry.getValue();
                JavaMailSenderImpl sender = new JavaMailSenderImpl();
                sender.setHost(properties.getHost());
                if (properties.getPort() != null) {
                    sender.setPort(properties.getPort().intValue());
                }
                sender.setUsername(properties.getUsername());
                sender.setPassword(properties.getPassword());
                sender.setProtocol(properties.getProtocol());
                if (properties.getDefaultEncoding() != null) {
                    sender.setDefaultEncoding(properties.getDefaultEncoding().name());
                }
                if (!properties.getProperties().isEmpty()) {
                    Properties props = new Properties();
                    props.putAll(properties.getProperties());
                    sender.setJavaMailProperties(props);
                }
                senders.put(entry.getKey(), sender);
            }
        }
        return senders;
    }

    public Map<String, JavaMailSender> getMailSenders() {
        return mailSenders;
    }

    public void setMailSenders(Map<String, JavaMailSender> mailSenders) {
        this.mailSenders = mailSenders;
    }
}
