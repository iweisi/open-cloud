package com.github.lyd.msg.producer.configuration;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author liuyadu
 */
@ConfigurationProperties(prefix = "open.mail")
public class OpenMailProperties {
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