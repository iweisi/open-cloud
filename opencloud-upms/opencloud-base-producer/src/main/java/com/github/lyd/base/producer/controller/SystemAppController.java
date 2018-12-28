package com.github.lyd.base.producer.controller;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.base.client.api.SystemAppRemoteService;
import com.github.lyd.base.client.dto.SystemAppDto;
import com.github.lyd.base.producer.service.SystemAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统用户信息
 *
 * @author liuyadu
 */
@Api(tags = "应用管理")
@RestController
public class SystemAppController implements SystemAppRemoteService {
    @Autowired
    private SystemAppService appInfoService;


    /**
     * 获取应用列表
     *
     * @return
     */
    @ApiOperation(value = "应用列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "显示条数:最大999", paramType = "form"),
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/app")
    @Override
    public ResultBody<PageList<SystemAppDto>> app(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        PageList<SystemAppDto> pageList = appInfoService.findListPage(new PageParams(page, limit), keyword);
        return ResultBody.success(pageList);
    }

    /**
     * 获取应用信息
     *
     * @param appId appId
     * @return 应用信息
     */
    @ApiOperation(value = "获取应用信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用ID", defaultValue = "1", required = true, paramType = "path"),
    })
    @GetMapping("/app/{appId}")
    @Override
    public ResultBody<SystemAppDto> getApp(
            @PathVariable("appId") String appId
    ) {
        SystemAppDto appInfo = appInfoService.getAppInfo(appId);
        return ResultBody.success(appInfo);
    }

    /**
     * 添加应用
     *
     * @param appName      应用名称
     * @param appNameEn    应用英文名称
     * @param appOs        手机应用操作系统:ios-苹果 android-安卓
     * @param appIcon      应用图标
     * @param appType      应用类型:server-应用服务 app-手机应用 pc-PC网页应用 wap-手机网页应用
     * @param appDesc      应用说明
     * @param status       状态
     * @param website      官网地址
     * @param redirectUrls 第三方应用授权回调地址
     * @param userId       开发者
     * @param userType     开发者类型
     * @return
     */
    @ApiOperation(value = "添加应用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appName", value = "应用名称", required = true, paramType = "form"),
            @ApiImplicitParam(name = "appNameEn", value = "应用英文名称", required = true, paramType = "form"),
            @ApiImplicitParam(name = "appType", value = "应用类型(server-应用服务 app-手机应用 pc-PC网页应用 wap-手机网页应用)", allowableValues = "server,app,pc,wap", required = true, paramType = "form"),
            @ApiImplicitParam(name = "appOs", value = "手机应用操作系统", allowableValues = "android,ios", required = false, paramType = "form"),
            @ApiImplicitParam(name = "appIcon", value = "应用图标", required = false, paramType = "form"),
            @ApiImplicitParam(name = "appDesc", value = "应用说明", required = false, paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, defaultValue = "1", allowableValues = "0,1", value = "是否启用", paramType = "form"),
            @ApiImplicitParam(name = "website", value = "官网地址", required = true, paramType = "form"),
            @ApiImplicitParam(name = "redirectUrls", value = "第三方应用授权回调地址", required = true, paramType = "form"),
            @ApiImplicitParam(name = "userId", value = "0-平台,其他填写真实Id", required = true, paramType = "form"),
            @ApiImplicitParam(name = "userType", value = "开发者类型", allowableValues = "platform,isp,dev", required = true, paramType = "form")
    })
    @PostMapping("/app/add")
    @Override
    public ResultBody<Boolean> addApp(
            @RequestParam(value = "appName") String appName,
            @RequestParam(value = "appNameEn") String appNameEn,
            @RequestParam(value = "appType") String appType,
            @RequestParam(value = "appOs", required = false) String appOs,
            @RequestParam(value = "appIcon", required = false) String appIcon,
            @RequestParam(value = "appDesc", required = false) String appDesc,
            @RequestParam(value = "status", defaultValue = "1") Integer status,
            @RequestParam(value = "website") String website,
            @RequestParam(value = "redirectUrls") String redirectUrls,
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "userType") String userType
    ) {
        SystemAppDto app = new SystemAppDto();
        app.setAppName(appName);
        app.setAppNameEn(appNameEn);
        app.setAppType(appType);
        app.setAppOs(appOs);
        app.setAppIcon(appIcon);
        app.setAppDesc(appDesc);
        app.setStatus(status);
        app.setWebsite(website);
        app.setRedirectUrls(redirectUrls);
        app.setUserId(userId);
        app.setUserType(userType);
        Boolean result = appInfoService.addAppInfo(app);
        return ResultBody.success(result);
    }

    /**
     * 编辑应用
     *
     * @param appId
     * @param appName      应用名称
     * @param appNameEn    应用英文名称
     * @param appOs        手机应用操作系统:ios-苹果 android-安卓
     * @param appIcon      应用图标
     * @param appType      应用类型:server-应用服务 app-手机应用 pc-PC网页应用 wap-手机网页应用
     * @param appDesc      应用说明
     * @param status       状态
     * @param website      官网地址
     * @param redirectUrls 第三方应用授权回调地址
     * @param userId       开发者
     * @param userType     开发者类型
     * @return
     */
    @ApiOperation(value = "编辑应用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用Id", required = true, paramType = "form"),
            @ApiImplicitParam(name = "appName", value = "应用名称", required = true, paramType = "form"),
            @ApiImplicitParam(name = "appNameEn", value = "应用英文名称", required = true, paramType = "form"),
            @ApiImplicitParam(name = "appType", value = "应用类型(server-应用服务 app-手机应用 pc-PC网页应用 wap-手机网页应用)", allowableValues = "server,app,pc,wap", required = true, paramType = "form"),
            @ApiImplicitParam(name = "appOs", value = "手机应用操作系统", allowableValues = "android,ios", required = false, paramType = "form"),
            @ApiImplicitParam(name = "appIcon", value = "应用图标", required = false, paramType = "form"),
            @ApiImplicitParam(name = "appDesc", value = "应用说明", required = false, paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, defaultValue = "1", allowableValues = "0,1", value = "是否启用", paramType = "form"),
            @ApiImplicitParam(name = "website", value = "官网地址", required = true, paramType = "form"),
            @ApiImplicitParam(name = "redirectUrls", value = "第三方应用授权回调地址", required = true, paramType = "form"),
            @ApiImplicitParam(name = "userId", value = "0-平台,其他填写真实Id", required = true, paramType = "form"),
            @ApiImplicitParam(name = "userType", value = "开发者类型", allowableValues = "platform,isp,dev", required = true, paramType = "form")
    })
    @PostMapping("/app/update")
    @Override
    public ResultBody<Boolean> updateApp(
            @RequestParam("appId") String appId,
            @RequestParam(value = "appName") String appName,
            @RequestParam(value = "appNameEn") String appNameEn,
            @RequestParam(value = "appType") String appType,
            @RequestParam(value = "appOs", required = false) String appOs,
            @RequestParam(value = "appIcon", required = false) String appIcon,
            @RequestParam(value = "appDesc", required = false) String appDesc,
            @RequestParam(value = "status", defaultValue = "1") Integer status,
            @RequestParam(value = "website") String website,
            @RequestParam(value = "redirectUrls") String redirectUrls,
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "userType") String userType
    ) {
        SystemAppDto app = new SystemAppDto();
        app.setAppId(appId);
        app.setAppName(appName);
        app.setAppNameEn(appNameEn);
        app.setAppType(appType);
        app.setAppOs(appOs);
        app.setAppIcon(appIcon);
        app.setAppDesc(appDesc);
        app.setStatus(status);
        app.setWebsite(website);
        app.setRedirectUrls(redirectUrls);
        app.setUserId(userId);
        app.setUserType(userType);
        Boolean result = appInfoService.updateInfo(app);
        return ResultBody.success(result);
    }

    /**
     * 重置秘钥
     *
     * @param appId 应用Id
     * @return 应用信息
     */
    @ApiOperation(value = "重置秘钥")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用Id", required = true, paramType = "form"),
    })
    @PostMapping("/app/reset")
    @Override
    public ResultBody<Boolean> resetSecret(
            @RequestParam("appId") String appId
    ) {
        Boolean result = appInfoService.restSecret(appId);
        return ResultBody.success(result);
    }

    /**
     * 删除应用
     *
     * @param appId 应用Id
     * @return 应用信息
     */
    @ApiOperation(value = "删除应用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用Id", required = true, paramType = "form"),
    })
    @PostMapping("/app/remove")
    @Override
    public ResultBody<Boolean> removeApp(
            @RequestParam("appId") String appId
    ) {
        Boolean result = appInfoService.removeApp(appId);
        return ResultBody.success(result);
    }
}
