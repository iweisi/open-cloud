package com.github.lyd.admin.configuration;

import com.github.lyd.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * SSO登陆成功监听
 * 并写入cookie
 *
 * @author liuyadu
 */
@Slf4j
@Component
public class SsoLoginListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent successEvent) {
        if (successEvent.getAuthentication() != null) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) successEvent.getAuthentication().getDetails();
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = requestAttributes.getResponse();
            WebUtils.setCookie(response, "token", details.getTokenValue());
            log.info("登陆成功,写入cookie:token={}", details.getTokenValue());
        }
    }
}
