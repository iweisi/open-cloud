package com.github.lyd.auth.client.constants;

/**
 * @author: liuyadu
 * @date: 2018/11/2 18:15
 * @description:
 */
public class AuthConstants {
    // token有效期，默认12小时
    public static final  int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 12;
    // token有效期，默认7天
    public static final int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 7;
}
