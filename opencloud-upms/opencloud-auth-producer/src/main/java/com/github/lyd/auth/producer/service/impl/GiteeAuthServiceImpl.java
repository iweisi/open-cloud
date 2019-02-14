package com.github.lyd.auth.producer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.lyd.auth.client.entity.ThirdPartyAuthClientDetails;
import com.github.lyd.auth.client.entity.ThirdPartyAuthProperties;
import com.github.lyd.auth.client.service.ThirdPartyAuthService;
import com.github.lyd.common.http.OpenRestTemplate;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信oauth2认证实现类
 *
 * @author liuyadu
 */
@Service("giteeService")
@Slf4j
public class GiteeAuthServiceImpl implements ThirdPartyAuthService {
    private static  final String TYPE = "gitee";
    @Autowired
    private OpenRestTemplate restTemplate;
    @Autowired
    private ThirdPartyAuthProperties socialAuthProperties;
    /**
     * 微信 登陆页面的URL
     */
    private final static String AUTHORIZATION_URL = "%s?response_type=code&client_id=%s&redirect_uri=%s&scope=%s";
    /**
     * 获取token的URL
     */
    private final static String ACCESS_TOKEN_URL = "%s?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";

    /**
     * 获取用户信息的 URL，oauth_consumer_key 为 apiKey
     */
    private static final String USER_INFO_URL = "%s?access_token=%s";


    @Override
    public String getAuthorizationUrl() {
        String url = String.format(AUTHORIZATION_URL, getClientDetails().getUserAuthorizationUri(), getClientDetails().getClientId(), getClientDetails().getRedirectUri(), getClientDetails().getScope(),System.currentTimeMillis());
        return url;
    }

    @Override
    public String getAccessToken(String code) {
        String url = String.format(ACCESS_TOKEN_URL, getClientDetails().getAccessTokenUri(), getClientDetails().getClientId(), getClientDetails().getClientSecret(), code,getClientDetails().getRedirectUri());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();
        String resp = restTemplate.getForObject(uri, String.class);
        if (resp != null && resp.contains("access_token")) {
            Map<String, String> map = getParam(resp);
            String access_token = map.get("access_token");
            return access_token;
        }
        log.error("QQ获得access_token失败，code无效，resp:{}", resp);
        return null;
    }

    /**
     * 由于QQ的几个接口返回类型不一样，此处是获取key-value类型的参数
     *
     * @param string
     * @return
     */
    private Map<String, String> getParam(String string) {
        Map<String, String> map = new HashMap();
        String[] kvArray = string.split("&");
        for (int i = 0; i < kvArray.length; i++) {
            String[] kv = kvArray[i].split("=");
            map.put(kv[0], kv[1]);
        }
        return map;
    }

    /**
     * QQ接口返回类型是text/plain，此处将其转为json
     *
     * @param string
     * @return
     */
    private JSONObject ConvertToJson(String string) {
        string = string.substring(string.indexOf("(") + 1, string.length());
        string = string.substring(0, string.indexOf(")"));
        JSONObject jsonObject = JSONObject.parseObject(string);
        return jsonObject;
    }

    @Override
    public String getOpenId(String accessToken) {
        return null;
    }

    @Override
    public Map getUserInfo(String accessToken, String openId) {
        String url = String.format(USER_INFO_URL, getClientDetails().getUserInfoUri(), accessToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();
        String resp = restTemplate.getForObject(uri, String.class);
        JSONObject data = JSONObject.parseObject(resp);
        Map result = Maps.newHashMap();
        result.put("openId", openId);
        result.put("avatar", data.getString("figureurl_qq_2"));
        result.put("nickName", data.getString("nickname"));
        return result;
    }


    @Override
    public String refreshToken(String code) {
        return null;
    }

    /**
     * 获取客户端配置信息
     *
     * @return
     */
    @Override
    public ThirdPartyAuthClientDetails getClientDetails() {
        return socialAuthProperties.getOauth2().get(TYPE);
    }

}