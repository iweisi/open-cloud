package com.github.lyd.auth.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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


    @RequestMapping("/confirm_access")
    public String confirm_access(HttpServletRequest request,HttpSession session, Map model) {
        Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ? model.get("scopes") : request.getAttribute("scopes"));
        List<String> scopeList = new ArrayList<String>();
        for (String scope : scopes.keySet()) {
            scopeList.add(scope);
        }
        model.put("scopeList", scopeList);
        Object auth = session.getAttribute("authorizationRequest");
        if (auth != null) {
           try{
               AuthorizationRequest authorizationRequest = (AuthorizationRequest) auth;
               ClientDetails clientDetails = clientDetailsService.loadClientByClientId(authorizationRequest.getClientId());
               model.put("app", clientDetails.getAdditionalInformation());
           }catch (Exception e){

           }
        }
        return "confirm_access";
    }

}
