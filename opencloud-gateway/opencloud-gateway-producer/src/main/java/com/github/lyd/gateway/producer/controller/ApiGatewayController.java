package com.github.lyd.gateway.producer.controller;

import com.github.lyd.common.autoconfigure.GatewayProperties;
import com.github.lyd.common.http.OpenRestTemplate;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.utils.DateUtils;
import com.github.lyd.common.utils.RandomValueUtils;
import com.github.lyd.common.utils.SignatureUtils;
import com.github.lyd.common.utils.WebUtils;
import com.github.lyd.gateway.client.api.ApiGatewayRemoteService;
import com.github.lyd.gateway.producer.locator.AccessLocator;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author liuyadu
 */
@RestController
public class ApiGatewayController implements ApiGatewayRemoteService {
    @Autowired
    private OpenRestTemplate openRestTemplate;
    @Autowired
    private GatewayProperties gatewayProperties;
    @Autowired
    private AccessLocator accessLocator;

    /**
     * 平台登录
     * 基于oauth2密码模式登录
     *
     * @param username
     * @param password
     * @return access_token
     */
    @ApiOperation(value = "平台登录", notes = "基于oauth2密码模式登录,无需签名,返回access_token。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", required = true, value = "登录名", paramType = "form"),
            @ApiImplicitParam(name = "password", required = true, value = "登录密码", paramType = "form")
    })
    @PostMapping("/login/token")
    public Object login(@RequestParam String username, @RequestParam String password, @RequestHeader HttpHeaders headers) {
        // 使用oauth2密码模式登录.
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("username", username);
        postParameters.add("password", password);
        postParameters.add("client_id", gatewayProperties.getClientId());
        postParameters.add("client_secret", gatewayProperties.getClientSecret());
        postParameters.add("grant_type", "password");
        // 使用客户端的请求头,发起请求
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity(postParameters, headers);
        Map result = openRestTemplate.postForObject(gatewayProperties.getAccessTokenUri(), request, Map.class);
        if (result.containsKey("access_token")) {
            return ResultBody.success(result);
        } else {
            return result;
        }
    }

    /**
     * 参数签名
     *
     * @param
     * @return
     */
    @ApiOperation(value = "内部应用请求签名", notes = "只适用于内部应用,外部不允许使用。返回clientId,nonce,timestamp,signType,sign")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params1", value = "参数1", paramType = "form"),
            @ApiImplicitParam(name = "params2", value = "参数2", paramType = "form"),
            @ApiImplicitParam(name = "paramsN", value = "参数...", paramType = "form"),
    })
    @PostMapping(value = "/sign")
    public ResultBody sign(HttpServletRequest request) {
        Map params = WebUtils.getParameterMap(request);
        Map appMap = Maps.newHashMap();
        appMap.put("clientId", gatewayProperties.getClientId());
        appMap.put("nonce", RandomValueUtils.uuid().substring(0, 16));
        appMap.put("timestamp", DateUtils.getTimestampStr());
        appMap.put("signType", "SHA256");
        params.putAll(appMap);
        String sign = SignatureUtils.getSign(params, gatewayProperties.getClientSecret());
        appMap.put("sign", sign);
        return ResultBody.success(appMap);
    }

    /**
     * 参数签名
     *
     * @param
     * @return
     */
    @ApiOperation(value = "获取网关缓存的访问限制", notes = "获取网关缓存的访问限制")
    @GetMapping(value = "/access/cache")
    @Override
    public ResultBody accessCache() {
        return ResultBody.success(accessLocator.getAccessList());
    }
}
