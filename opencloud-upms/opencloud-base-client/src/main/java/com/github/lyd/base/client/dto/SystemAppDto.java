package com.github.lyd.base.client.dto;

import com.github.lyd.auth.client.dto.ClientDetailsDto;
import com.github.lyd.base.client.entity.SystemApp;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author liuyadu
 */
public class SystemAppDto extends SystemApp implements Serializable {
    private static final long serialVersionUID = 7902161098976147412L;
    @Transient
    private ClientDetailsDto clientInfo;
    private String redirectUrls;
    private String grantTypes;
    private boolean autoApprove;
    private String scopes;
    private String resourceIds;
    private String authorities;

    public ClientDetailsDto getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientDetailsDto clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getRedirectUrls() {
        return redirectUrls;
    }

    public void setRedirectUrls(String redirectUrls) {
        this.redirectUrls = redirectUrls;
    }

    public String getGrantTypes() {
        return grantTypes;
    }

    public void setGrantTypes(String grantTypes) {
        this.grantTypes = grantTypes;
    }

    public boolean isAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(boolean autoApprove) {
        this.autoApprove = autoApprove;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
}
