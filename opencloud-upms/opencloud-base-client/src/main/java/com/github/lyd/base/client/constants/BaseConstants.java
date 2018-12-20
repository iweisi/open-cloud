package com.github.lyd.base.client.constants;

/**
 * 通用权限常量
 */
public class BaseConstants {
    /**
     * 超级权限
     */
    public final static String SUPER_AUTHORITY = "ROLE_Administrator";

    /**
     * 系统用户类型:0-平台、1-服务提供商、2-自研开发者
     */
    public final static int USER_TYPE_PLATFORM = 0;
    public final static int USER_TYPE_ISP = 1;
    public final static int USER_TYPE_DEVELOPER = 2;
    /**
     * 系统用户状态
     * 0:禁用、1:正常、2:锁定
     */
    public final static int USER_STATE_DISABLE = 0;
    public final static int USER_STATE_NORMAL = 1;
    public final static int USER_STATE_LOCKED = 2;

    /**
     * 系统用户账号类型:
     * username:系统用户名、email：邮箱、mobile：手机号、qq：QQ号、weixin：微信号、weibo：微博
     */
    public final static String USER_ACCOUNT_TYPE_USERNAME = "username";
    public final static String USER_ACCOUNT_TYPE_EMAIL = "email";
    public final static String USER_ACCOUNT_TYPE_MOBILE = "mobile";
    public final static String USER_ACCOUNT_TYPE_QQ = "qq";
    public final static String USER_ACCOUNT_TYPE_WEIXIN = "weixin";
    public final static String USER_ACCOUNT_TYPE_WEIBO = "weibo";

    /**
     * 资源类型
     * menu:菜单、action:操作、api:API
     */
    public final static String RESOURCE_TYPE_MENU = "menu";
    public final static String RESOURCE_TYPE_ACTION = "action";
    public final static String RESOURCE_TYPE_API = "api";

    /**
     * 权限所有者
     * user:系统用户权限、role:角色权限
     */
    public final static String PERMISSION_SEPARATOR = "_";
    public final static String PERMISSION_IDENTITY_PREFIX_USER = "USER_";
    public final static String PERMISSION_IDENTITY_PREFIX_ROLE = "ROLE_";

    /**
     * 应用服务
     */
    public final static String APP_TYPE_SERVER = "server";
    public final static String APP_TYPE_APP = "app";
    public final static String APP_TYPE_PC = "pc";
    public final static String APP_TYPE_WAP = "wap";

    public final static String APP_IOS = "ios";
    public final static String APP_ANDROID = "android";


    public static boolean validateOs(String os) {
        if (os == null) {
            return false;
        }
        if (APP_ANDROID.equals(os) || APP_IOS.equals(os)) {
            return true;
        }
        return false;
    }

    public static boolean validateAppType(String appType) {
        if (appType == null) {
            return false;
        }
        if (APP_TYPE_SERVER.equals(appType) || APP_TYPE_APP.equals(appType) || APP_TYPE_PC.equals(appType) || APP_TYPE_WAP.equals(appType)) {
            return true;
        }
        return false;
    }

    public static boolean isAutoApprove(String appType) {
        if (appType == null) {
            return false;
        }
        if (APP_TYPE_SERVER.equals(appType) || APP_TYPE_APP.equals(appType)) {
            return true;
        }
        return false;
    }

    public static String getGrantTypes(String appType) {
        if (appType == null) {
            return null;
        }
        if (APP_TYPE_SERVER.equals(appType)) {
            return "authorization_code,refresh_token,client_credentials";
        }
        if (APP_TYPE_APP.equals(appType)) {
            return "authorization_code,refresh_token,client_credentials";
        }
        if (APP_TYPE_PC.equals(appType)) {
            return "authorization_code,refresh_token,implicit";
        }
        if (APP_TYPE_WAP.equals(appType)) {
            return "authorization_code,refresh_token,implicit";
        }
        return null;
    }
}
