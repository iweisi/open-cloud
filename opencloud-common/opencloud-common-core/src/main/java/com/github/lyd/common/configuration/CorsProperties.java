package com.github.lyd.common.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义跨域配置
 * @author LYD
 * @date 2018/7/29
 */
@ConfigurationProperties(prefix = "opencloud.cors")
public class CorsProperties {
    private Boolean enabled = false;
    private Boolean allowCredentials;
    private String allowedOrigin;
    private String allowedHeader;
    private String allowedMethod;
    private Long maxAge;

    public Boolean getAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(Boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public String getAllowedOrigin() {
        return allowedOrigin;
    }

    public void setAllowedOrigin(String allowedOrigin) {
        this.allowedOrigin = allowedOrigin;
    }

    public String getAllowedHeader() {
        return allowedHeader;
    }

    public void setAllowedHeader(String allowedHeader) {
        this.allowedHeader = allowedHeader;
    }

    public String getAllowedMethod() {
        return allowedMethod;
    }

    public void setAllowedMethod(String allowedMethod) {
        this.allowedMethod = allowedMethod;
    }

    public Long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "CorsProperties{" +
                "enabled=" + enabled +
                ", allowCredentials=" + allowCredentials +
                ", allowedOrigin='" + allowedOrigin + '\'' +
                ", allowedHeader='" + allowedHeader + '\'' +
                ", allowedMethod='" + allowedMethod + '\'' +
                ", maxAge=" + maxAge +
                '}';
    }
}
