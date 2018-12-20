package com.github.lyd.auth.client.dto;

import com.alibaba.fastjson.JSONObject;
import com.github.lyd.auth.client.constants.AuthConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * @author: liuyadu
 * @date: 2018/11/2 18:02
 * @description:
 */
public class ClientDetailsDto implements Serializable, ClientDetails {
    private static final long serialVersionUID = 3725084953460581042L;
    private String clientId;
    private String clientSecret;
    private Set<String> resourceIds;
    private Set<String> scopes;
    private Set<String> grantTypes;
    private Set<String> redirectUrls;
    private Collection<GrantedAuthority> authorities;
    private Map<String, Object> clientInfo;
    private boolean isAutoApprove;

    public ClientDetailsDto() {
    }

    /**
     * @param clientId     应用ID
     * @param clientSecret 应用秘钥
     * @param grantTypes   授权类型
     * @param autoApprove  自动授权
     * @param redirectUrls 授权重定向地址
     * @param scopes       授权范围
     * @param resourceIds  资源服务ID
     * @param authorities  权限
     * @param clientInfo   客户端附加信息,json字符串
     */
    public ClientDetailsDto(String clientId, String clientSecret, String grantTypes, boolean autoApprove, String redirectUrls, String scopes, String resourceIds, String authorities, String clientInfo) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.clientInfo = Maps.newHashMap();
        this.isAutoApprove = autoApprove;
        this.resourceIds = resourceIds != null ? new HashSet<>(Arrays.asList(StringUtils
                .tokenizeToStringArray(resourceIds, ","))) : null;
        this.scopes = scopes != null ? new HashSet<>(Arrays.asList(StringUtils
                .tokenizeToStringArray(scopes, ","))) : null;
        this.grantTypes = grantTypes != null ? new HashSet<>(Arrays.asList(StringUtils
                .tokenizeToStringArray(grantTypes, ","))) : null;
        this.redirectUrls = redirectUrls != null ? new HashSet<>(Arrays.asList(StringUtils
                .tokenizeToStringArray(redirectUrls, ","))) : null;
        try {
            this.clientInfo = JSONObject.parseObject(clientInfo, Map.class);
        } catch (Exception e) {

        }

        if (authorities != null) {
            List<GrantedAuthority> grantedAuthorityList = null;
            List<String> list = Arrays.asList(StringUtils.tokenizeToStringArray(authorities, ","));
            if (list.size() > 0) {
                grantedAuthorityList = Lists.newArrayList();
                List<GrantedAuthority> finalGrantedAuthorityList = grantedAuthorityList;
                list.forEach(a -> {
                    finalGrantedAuthorityList.add(new SimpleGrantedAuthority(a));
                });
            }
            this.authorities = grantedAuthorityList;
        }
    }

    public ClientDetailsDto(ClientDetails details) {
        this.clientId = details.getClientId();
        this.clientSecret = details.getClientSecret();
        this.resourceIds = details.getResourceIds();
        this.scopes = details.getScope();
        this.redirectUrls = details.getRegisteredRedirectUri();
        this.authorities = details.getAuthorities();
        this.grantTypes = details.getAuthorizedGrantTypes();
        this.authorities = details.getAuthorities();
        this.clientInfo = details.getAdditionalInformation();
    }

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return this.resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Set<String> getScope() {
        return this.scopes;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return this.grantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return this.redirectUrls;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {

        return this.authorities;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return AuthConstants.ACCESS_TOKEN_VALIDITY_SECONDS;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return AuthConstants.REFRESH_TOKEN_VALIDITY_SECONDS;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return isAutoApprove;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return clientInfo;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public void setScopes(Set<String> scopes) {
        this.scopes = scopes;
    }

    public Set<String> getGrantTypes() {
        return grantTypes;
    }

    public void setGrantTypes(Set<String> grantTypes) {
        this.grantTypes = grantTypes;
    }

    public Set<String> getRedirectUrls() {
        return redirectUrls;
    }

    public void setRedirectUrls(Set<String> redirectUrls) {
        this.redirectUrls = redirectUrls;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public Map<String, Object> getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(Map<String, Object> clientInfo) {
        this.clientInfo = clientInfo;
    }

    public boolean isAutoApprove() {
        return isAutoApprove;
    }

    public void setAutoApprove(boolean autoApprove) {
        isAutoApprove = autoApprove;
    }
}
