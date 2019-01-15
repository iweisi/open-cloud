package com.github.lyd.msg.producer.configuration;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 自定义邮件配置
 *
 * @author liuyadu
 */
@ConfigurationProperties(prefix = "opencloud.mail")
public class MailChannelsProperties {
    private Map<String, MailProperties> channels;

    public Map<String, MailProperties> getChannels() {
        return channels;
    }

    public void setChannels(Map<String, MailProperties> channels) {
        this.channels = channels;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OpenMailProperties{");
        sb.append("channels=").append(channels);
        sb.append('}');
        return sb.toString();
    }
}