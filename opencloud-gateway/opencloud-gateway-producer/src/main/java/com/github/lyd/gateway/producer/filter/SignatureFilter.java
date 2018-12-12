package com.github.lyd.gateway.producer.filter;

import com.github.lyd.common.autoconfigure.GatewayProperties;
import com.github.lyd.common.exception.OpenSignatureDeniedHandler;
import com.github.lyd.common.exception.OpenSignatureException;
import com.github.lyd.common.exception.SignatureDeniedHandler;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.security.OpenAuth;
import com.github.lyd.common.security.OpenHelper;
import com.github.lyd.common.utils.SignatureUtils;
import com.github.lyd.common.utils.WebUtils;
import com.github.lyd.gateway.producer.service.feign.AppInfoRemoteServiceClient;
import com.github.lyd.rbac.client.dto.AppInfoDto;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 自定义签名过滤器
 *
 * @author: liuyadu
 * @date: 2018/11/28 18:26
 * @description:
 */
public class SignatureFilter implements Filter {
    private SignatureDeniedHandler signatureDeniedHandler;
    private AppInfoRemoteServiceClient appInfoRemoteServiceClient;
    private GatewayProperties gatewayProperties;

    public SignatureFilter(AppInfoRemoteServiceClient appInfoRemoteServiceClient, GatewayProperties gatewayProperties) {
        this.appInfoRemoteServiceClient = appInfoRemoteServiceClient;
        this.gatewayProperties = gatewayProperties;
        this.signatureDeniedHandler = new OpenSignatureDeniedHandler();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        AntPathRequestMatcher matcher = new AntPathRequestMatcher("/sign");
        OpenAuth auth = OpenHelper.getPrincipal();
        if (auth != null && gatewayProperties.getEnabledValidateSign() && !matcher.matches(request)) {
            try {
                //开始验证签名
                String appId = auth.getAuthAppId();
                if (appInfoRemoteServiceClient != null && appId != null) {
                    Map params = WebUtils.getParameterMap(request);
                    // 验证请求参数
                    SignatureUtils.validateParams(params);
                    // 获取客户端信息
                    ResultBody<AppInfoDto> result = appInfoRemoteServiceClient.getApp(appId);
                    AppInfoDto appInfoDto = result.getData();
                    if (appInfoDto == null) {
                        throw new OpenSignatureException("clientId无效");
                    }
                    //强制覆盖请求参数clientId
                    params.put("clientId", appInfoDto.getAppId());
                    //服务器验签
                    if (!SignatureUtils.validateSign(params, appInfoDto.getAppSecret())) {
                        throw new OpenSignatureException("签名验证失败!");
                    }
                }
            } catch (Exception ex) {
                signatureDeniedHandler.handle(request, response, ex);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return true;
    }

    @Override
    public void destroy() {

    }

    public AppInfoRemoteServiceClient getAppInfoClientApi() {
        return appInfoRemoteServiceClient;
    }

    public void setAppInfoClientApi(AppInfoRemoteServiceClient appInfoClientApi) {
        this.appInfoRemoteServiceClient = appInfoClientApi;
    }

}
