package com.github.lyd.auth.producer.controller;

import com.github.lyd.auth.producer.service.impl.QQAuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: liuyadu
 * @date: 2018/10/29 15:59
 * @description:
 */
@Controller
public class IndexController {
    @Autowired
    private JdbcClientDetailsService clientDetailsService;

    @Autowired
    private QQAuthServiceImpl qqAuthService;

    /**
     * 欢迎页
     *
     * @return
     */
    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }

    /**
     * 登录页
     *
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 第三方授权后会回调此方法，并将code传过来
     *
     * @param code code
     * @return
     */
    @GetMapping("/oauth/qq/callback")
    public String oauthByQQ(@RequestParam(value = "code") String code, HttpServletRequest request) {
        String token = qqAuthService.getAccessToken(code);
        if (token!=null) {
             String openid = qqAuthService.getOpenId(token);
            if (openid!=null) {

            }
        }
        return "redirect:http://www.baidu.com";
    }

    @GetMapping("/oauth/wechat/callback")
    public String oauthByWechat(@RequestParam(value = "code") String code, HttpServletRequest request) {
        String token = qqAuthService.getAccessToken(code);
        if (token!=null) {
            String openid = qqAuthService.getOpenId(token);
            if (openid!=null) {

            }
        }
        return "redirect:http://www.baidu.com";
    }

    @GetMapping("/oauth/gitee/callback")
    public String oauthByGitee(@RequestParam(value = "code") String code, HttpServletRequest request) {
        String token = qqAuthService.getAccessToken(code);
        if (token!=null) {
            String openid = qqAuthService.getOpenId(token);
            if (openid!=null) {

            }
        }
        return "redirect:http://www.baidu.com";
    }

    @RequestMapping("/confirm_access")
    public String confirm_access(HttpServletRequest request, HttpSession session, Map model) {
        Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ? model.get("scopes") : request.getAttribute("scopes"));
        List<String> scopeList = new ArrayList<String>();
        for (String scope : scopes.keySet()) {
            scopeList.add(scope);
        }
        model.put("scopeList", scopeList);
        Object auth = session.getAttribute("authorizationRequest");
        if (auth != null) {
            try {
                AuthorizationRequest authorizationRequest = (AuthorizationRequest) auth;
                ClientDetails clientDetails = clientDetailsService.loadClientByClientId(authorizationRequest.getClientId());
                model.put("app", clientDetails.getAdditionalInformation());
            } catch (Exception e) {

            }
        }
        return "confirm_access";
    }

}
