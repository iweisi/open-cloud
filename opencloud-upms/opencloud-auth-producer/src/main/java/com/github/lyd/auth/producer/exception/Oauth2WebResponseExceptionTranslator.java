package com.github.lyd.auth.producer.exception;

import com.github.lyd.common.exception.OpenExceptionHandler;
import com.github.lyd.common.model.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义oauth2异常提示
 */
@Slf4j
public class Oauth2WebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        ResultBody responseData = OpenExceptionHandler.resolveException(e, request, response);
        Oauth2Exception oauth2Exception = new Oauth2Exception(responseData.getMessage());
        oauth2Exception.addAdditionalInformation("code", String.valueOf(responseData.getCode()));
        return ResponseEntity.status(200).body(oauth2Exception);
    }
}