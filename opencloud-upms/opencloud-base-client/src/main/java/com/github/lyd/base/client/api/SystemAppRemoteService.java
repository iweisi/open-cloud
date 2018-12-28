package com.github.lyd.base.client.api;

import com.github.lyd.base.client.dto.SystemAppDto;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.ResultBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liuyadu
 */
public interface SystemAppRemoteService {
    /**
     * 获取应用列表
     *
     * @return
     */
    @PostMapping("/app")
    ResultBody<PageList<SystemAppDto>> app(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    );


    /**
     * 获取
     *
     * @param appId 应用Id
     * @return
     */
    @GetMapping("/app/{appId}")
    ResultBody<SystemAppDto> getApp(@PathVariable("appId") String appId);

    /**
     * 添加应用
     *
     * @param appName      应用名称
     * @param appNameEn    应用英文名称
     * @param appOs        手机应用操作系统:ios-苹果 android-安卓
     * @param appType      应用类型:server-应用服务 app-手机应用 pc-PC网页应用 wap-手机网页应用
     * @param appIcon      应用图标
     * @param appDesc      应用说明
     * @param status       状态
     * @param website      官网地址
     * @param redirectUrls 第三方应用授权回调地址
     * @param userId       开发者
     * @param userType     开发者类型
     * @return
     */
    @PostMapping("/app/add")
    ResultBody<Boolean> addApp(
            @RequestParam(value = "appName") String appName,
            @RequestParam(value = "appNameEn") String appNameEn,
            @RequestParam(value = "appType") String appType,
            @RequestParam(value = "appIcon") String appIcon,
            @RequestParam(value = "appOs", required = false) String appOs,
            @RequestParam(value = "appDesc", required = false) String appDesc,
            @RequestParam(value = "status", defaultValue = "1") Integer status,
            @RequestParam(value = "website") String website,
            @RequestParam(value = "redirectUrls") String redirectUrls,
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "userType") String userType
    );

    /**
     * 更新应用
     *
     * @param appId
     * @param appName      应用名称
     * @param appNameEn    应用英文名称
     * @param appOs        手机应用操作系统:ios-苹果 android-安卓
     * @param appType      应用类型:server-应用服务 app-手机应用 pc-PC网页应用 wap-手机网页应用
     * @param appIcon      应用图标
     * @param appDesc      应用说明
     * @param status       状态
     * @param website      官网地址
     * @param redirectUrls 第三方应用授权回调地址
     * @param userId       开发者
     * @param userType     开发者类型
     * @return
     */
    @PostMapping("/app/update")
    ResultBody<Boolean> updateApp(
            @RequestParam("appId") String appId,
            @RequestParam(value = "appName") String appName,
            @RequestParam(value = "appNameEn") String appNameEn,
            @RequestParam(value = "appType") String appType,
            @RequestParam(value = "appIcon") String appIcon,
            @RequestParam(value = "appOs", required = false) String appOs,
            @RequestParam(value = "appDesc", required = false) String appDesc,
            @RequestParam(value = "status", defaultValue = "1") Integer status,
            @RequestParam(value = "website") String website,
            @RequestParam(value = "redirectUrls") String redirectUrls,
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "userType") String userType
    );

    /**
     * 重置秘钥
     *
     * @param appId 应用Id
     * @return
     */
    @PostMapping("/app/reset")
    ResultBody<Boolean> resetSecret(
            @RequestParam("appId") String appId
    );


    /**
     * 删除应用
     *
     * @param appId 应用Id
     * @return
     */
    @PostMapping("/app/remove")
    ResultBody<Boolean> removeApp(
            @RequestParam("appId") String appId
    );
}
