package com.github.lyd.common.constants;

/**
 * 返回码
 *
 * @author admin
 */

public enum ResultEnum {
    /**
     * 成功
     */
    OK(0, "success"),

    /**
     * oauth2返回码
     */
    INVALID_TOKEN(10001, "invalid_token"),
    INVALID_SCOPE(10002, "invalid_scope"),
    INVALID_REQUEST(10003, "invalid_request"),
    INVALID_CLIENT(10004, "invalid_client"),
    INVALID_GRANT(10005, "invalid_grant"),
    REDIRECT_URI_MISMATCH(10006, "redirect_uri_mismatch"),
    UNAUTHORIZED_CLIENT(10007, "unauthorized_client"),
    EXPIRED_TOKEN(10008, "expired_token"),
    UNSUPPORTED_GRANT_TYPE(10009, "unsupported_grant_type"),
    UNSUPPORTED_RESPONSE_TYPE(10010, "unsupported_response_type"),
    ACCESS_DENIED(10011, "access_denied"),
    TEMPORARILY_UNAVAILABLE(10012, "temporarily_unavailable"),
    UNAUTHORIZED(10013, "unauthorized"),
    /**
     * 账号错误
     */
    BAD_CREDENTIALS(20001, "bad_credentials"),
    ACCOUNT_DISABLED(20002, "account_disabled"),
    ACCOUNT_EXPIRED(20003, "account_expired"),
    CREDENTIALS_EXPIRED(20004, "credentials_expired"),
    ACCOUNT_LOCKED(20005, "account_locked"),
    USERNAME_NOTFOUND(20006, "username_notfound"),

    /**
     * 提示错误
     */
    PARAMETER_ERROR(30001, "parameter_error"),
    ALERT_ERROR(30002, "alert_error"),

    /**
     * 请求错误
     */
    BAD_REQUEST(40000, "bad_request"),
    NOT_FOUND(40004, "not_found"),
    METHOD_NOT_ALLOWED(40005, "method_not_allowed"),
    MEDIA_TYPE_NOT_ACCEPTABLE(40006, "media_type_not_acceptable"),
    /**
     * 系统错误
     */
    ERROR(50000, "error"),
    SERVICE_NOT_FOUND(50002, "service_not_found"),
    SIGNATURE_DENIED(50003, "signature_denied");


    private int code;
    private String message;

    ResultEnum() {
    }

    private ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultEnum getResultEnum(int code) {
        for (ResultEnum type : ResultEnum.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return ERROR;
    }

    public static ResultEnum getResultEnum(String message) {
        for (ResultEnum type : ResultEnum.values()) {
            if (type.getMessage().equals(message)) {
                return type;
            }
        }
        return ERROR;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
