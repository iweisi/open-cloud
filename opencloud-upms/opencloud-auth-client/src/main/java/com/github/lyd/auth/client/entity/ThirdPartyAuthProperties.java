package com.github.lyd.auth.client.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author: liuyadu
 * @date: 2019/2/14 14:34
 * @description:
 */
@ConfigurationProperties(prefix = "opencloud.social")
public class ThirdPartyAuthProperties {

    private Map<String,ThirdPartyAuthClientDetails> oauth2;

    public Map<String, ThirdPartyAuthClientDetails> getOauth2() {
        return oauth2;
    }

    public void setOauth2(Map<String, ThirdPartyAuthClientDetails> oauth2) {
        this.oauth2 = oauth2;
    }
}
