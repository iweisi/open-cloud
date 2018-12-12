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
     * 认证授权地址
     */
    private String authServerAddr;
    /**
     * 认证范围
     */
    private String scope;
    /**
     * 是否开启数字签名校验
     */
    private Boolean enabledValidateSign = true;

    /**
     * 是否开启资源访问鉴权
     */
    private Boolean enabledValidateAccess = true;

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

    public String getAuthServerAddr() {
        return authServerAddr;
    }

    public void setAuthServerAddr(String authServerAddr) {
        this.authServerAddr = authServerAddr;
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

    @Override
    public String toString() {
        return "GatewayProperties{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", serverAddr='" + serverAddr + '\'' +
                ", authServerAddr='" + authServerAddr + '\'' +
                ", scope='" + scope + '\'' +
                ", enabledValidateSign=" + enabledValidateSign +
                ", enabledValidateAccess=" + enabledValidateAccess +
                '}';
    }
}
