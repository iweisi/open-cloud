package com.github.lyd.sys.client.api;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.sys.client.dto.SystemAppDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface SystemAppRemoteService {
    /**
     * 获取应用列表
     *
     * @return
     */
    @PostMapping("/apps")
    ResultBody<PageList<SystemAppDto>> apps(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    );


    /**
     * 获取应用信息
     *
     * @param appId 应用Id
     * @return
     */
    @GetMapping("/apps/{appId}")
    ResultBody<SystemAppDto> getApp(@PathVariable("appId") String appId);

    /**
     * 添加应用
     *
     * @param appName      应用名称
     * @param appNameEn    应用英文名称
     * @param appIcon      应用图标
     * @param appType      应用类型:server-应用服务 app-手机应用 pc-PC网页应用 wap-手机网页应用
     * @param appDesc  应用说明
     * @param os           手机应用操作系统:ios-苹果 android-安卓
     * @param redirectUrls 重定向地址
     * @param scopes       授权范围
     * @param resourceIds  资源服务器ID
     * @param authorities  应用权限,多个用逗号隔开
     * @return
     */
    @PostMapping("/apps/add")
    ResultBody<Boolean> addApp(
            @RequestParam(value = "appName") String appName,
            @RequestParam(value = "appNameEn") String appNameEn,
            @RequestParam(value = "appType") String appType,
            @RequestParam(value = "appIcon",required = false) String appIcon,
            @RequestParam(value = "appDesc",required = false) String appDesc,
            @RequestParam(value = "appOs",required = false) String appOs,
            @RequestParam(value = "redirectUrls",required = false) String redirectUrls,
            @RequestParam(value = "scopes",required = false) String scopes,
            @RequestParam(value = "resourceIds",required = false) String resourceIds,
            @RequestParam(value = "authorities",required = false) String authorities
    );

    /**
     * 更新应用
     *
     * @param appName      应用名称
     * @param appNameEn    应用英文名称
     * @param appIcon      应用图标
     * @param appType      应用类型:server-应用服务 app-手机应用 pc-PC网页应用 wap-手机网页应用
     * @param appDesc  应用说明
     * @param os           手机应用操作系统:ios-苹果 android-安卓
     * @param appId        应用Id
     * @param redirectUrls 重定向地址
     * @param scopes       授权范围
     * @param resourceIds  资源服务器ID
     * @param authorities  应用权限,多个用逗号隔开
     * @return
     */
    @PostMapping("/apps/update")
    ResultBody<Boolean> updateApp(
            @RequestParam("appId") String appId,
            @RequestParam(value = "appName") String appName,
            @RequestParam(value = "appNameEn") String appNameEn,
            @RequestParam(value = "appType") String appType,
            @RequestParam(value = "appIcon",required = false) String appIcon,
            @RequestParam(value = "appDesc",required = false) String appDesc,
            @RequestParam(value = "appOs",required = false) String appOs,
            @RequestParam(value = "redirectUrls",required = false) String redirectUrls,
            @RequestParam(value = "scopes",required = false) String scopes,
            @RequestParam(value = "resourceIds",required = false) String resourceIds,
            @RequestParam(value = "authorities",required = false) String authorities
    );

    /**
     * 重置秘钥
     *
     * @param appId 应用Id
     * @return
     */
    @PostMapping("/apps/reset")
    ResultBody<Boolean> resetSecret(
            @RequestParam("appId") String appId
    );


    /**
     * 删除应用
     *
     * @param appId 应用Id
     * @return
     */
    @PostMapping("/apps/remove")
    ResultBody<Boolean> removeApp(
            @RequestParam("appId") String appId
    );
}
