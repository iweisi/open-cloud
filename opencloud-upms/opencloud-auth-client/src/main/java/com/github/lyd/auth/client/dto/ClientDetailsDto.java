package com.github.lyd.auth.client.dto;

import com.alibaba.fastjson.JSONObject;
import com.github.lyd.auth.client.constants.AuthConstants;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: liuyadu
 * @date: 2018/11/2 18:02
 * @description:
 */
public class ClientDetailsDto extends BaseClientDetails implements Serializable {
    private static final long serialVersionUID = 3725084953460581042L;

    public ClientDetailsDto() {
        super();
    }

    public ClientDetailsDto(ClientDetails prototype) {
       super(prototype);
    }
    /**
     * @param clientId       应用ID
     * @param clientSecret   应用秘钥
     * @param grantTypes     授权类型
     * @param autoApprove    自动授权
     * @param redirectUrls   授权重定向地址
     * @param scopes         授权范围
     * @param resourceIds    资源服务ID
     * @param authorities    权限
     * @param additionalInfo 客户端附加信息,json字符串
     */
    public ClientDetailsDto(String clientId, String clientSecret, String grantTypes, boolean autoApprove, String redirectUrls, String scopes, String resourceIds, String authorities, String additionalInfo) {
        super(clientId, resourceIds, scopes, grantTypes, authorities, redirectUrls);
        try {
            this.setClientSecret(clientSecret);
            Map appInfo = JSONObject.parseObject(additionalInfo, Map.class);
            this.setAdditionalInformation(appInfo);
            this.setAccessTokenValiditySeconds(AuthConstants.ACCESS_TOKEN_VALIDITY_SECONDS);
            this.setRefreshTokenValiditySeconds(AuthConstants.REFRESH_TOKEN_VALIDITY_SECONDS);
        } catch (Exception e) {

        }
    }

}
