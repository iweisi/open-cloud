package com.github.lyd.common.http;

import com.github.lyd.common.autoconfigure.GatewayProperties;
import com.github.lyd.common.model.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.util.Assert;

/**
 * 平台Oauth2请求工具类
 * @author: liuyadu
 * @date: 2018/12/11 15:51
 * @description:
 */
@Slf4j
public class OpenRestTemplate extends OAuth2RestTemplate {

    private GatewayProperties gatewayProperties;


    public OpenRestTemplate(OAuth2ProtectedResourceDetails resource) {
        super(resource);
    }

    public OpenRestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
        super(resource, context);
    }

    /**
     * 刷新网关
     */
    public void refreshGateway() {
        try {
            Assert.notNull(this.gatewayProperties, "网关信息错误");
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity<String> formEntity = new HttpEntity<String>("", headers);
            ResultBody resultBody = postForObject(gatewayProperties.getServerAddr().concat("/actuator/refresh-gateway"), formEntity, ResultBody.class);
            log.info("refreshGateway:{}", resultBody);
        } catch (Exception e) {
            log.error("refreshGateway error:{}", e.getMessage());
        }
    }

    public GatewayProperties getGatewayProperties() {
        return gatewayProperties;
    }

    public void setGatewayProperties(GatewayProperties gatewayProperties) {
        this.gatewayProperties = gatewayProperties;
    }
}
