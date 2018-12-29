package com.github.lyd.auth.producer.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义oauth2异常提示
 * @author liuyadu
 */
@JsonSerialize(using = Oauth2ExceptionSerializer.class)
public class Oauth2Exception extends OAuth2Exception {
    public Oauth2Exception(String msg) {
        super(msg);
    }
}