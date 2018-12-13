package com.github.lyd.common.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义网关配置
 *
 * @author: liuyadu
 * @date: 2018/11/23 14:40
 * @description:
 */
@ConfigurationProperties(prefix = "opencloud.gateway")
public class GatewayProperties {
    /**
     * 网关客户端Id
     */
    private String clientId;
    /**
     * 网关客户端密钥
     */
    private String clientSecret;
    /**
     * 网关服务地址
     */
    private String serverAddr;
    /**
     * 认证范围
     */
    private String scope;
    /**
     * 是否开启数字签名校验,只对网关服务有效
     */
    private Boolean enabledValidateSign = true;
    /**
     * 是否开启资源访问鉴权,只对网关服务有效
     */
    private Boolean enabledValidateAccess = true;
    /**
     * 获取token
     */
    private String accessTokenUri;
    /**
     * 认证地址
     */
    private String userAuthorizationUri;
    /**
     * 获取token地址
     */
    private String tokenInfoUri;
    /**
     * 获取用户信息地址
     */
    private String userInfoUri;


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Boolean getEnabledValidateSign() {
        return enabledValidateSign;
    }

    public void setEnabledValidateSign(Boolean enabledValidateSign) {
        this.enabledValidateSign = enabledValidateSign;
    }

    public Boolean getEnabledValidateAccess() {
        return enabledValidateAccess;
    }

    public void setEnabledValidateAccess(Boolean enabledValidateAccess) {
        this.enabledValidateAccess = enabledValidateAccess;
    }

    public String getAccessTokenUri() {
        return accessTokenUri;
    }

    public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
    }

    public String getUserAuthorizationUri() {
        return userAuthorizationUri;
    }

    public void setUserAuthorizationUri(String userAuthorizationUri) {
        this.userAuthorizationUri = userAuthorizationUri;
    }

    public String getTokenInfoUri() {
        return tokenInfoUri;
    }

    public void setTokenInfoUri(String tokenInfoUri) {
        this.tokenInfoUri = tokenInfoUri;
    }

    public String getUserInfoUri() {
        return userInfoUri;
    }

    public void setUserInfoUri(String userInfoUri) {
        this.userInfoUri = userInfoUri;
    }

    @Override
    public String toString() {
        return "GatewayProperties{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", serverAddr='" + serverAddr + '\'' +
                ", scope='" + scope + '\'' +
                ", enabledValidateSign=" + enabledValidateSign +
                ", accessTokenUri='" + accessTokenUri + '\'' +
                ", userAuthorizationUri='" + userAuthorizationUri + '\'' +
                ", tokenInfoUri='" + tokenInfoUri + '\'' +
                ", userInfoUri='" + userInfoUri + '\'' +
                ", enabledValidateAccess=" + enabledValidateAccess +
                '}';
    }
}
