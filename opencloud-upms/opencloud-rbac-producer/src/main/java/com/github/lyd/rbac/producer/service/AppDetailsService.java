package com.github.lyd.rbac.producer.service;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.rbac.client.dto.AppDetailsDto;

/**
 * app信息管理
 *
 * @author: liuyadu
 * @date: 2018/11/12 16:26
 * @description:
 */
public interface AppDetailsService {

    /**
     * 查询应用列表
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    PageList<AppDetailsDto> findListPage(PageParams pageParams, String keyword);

    /**
     * 获取app信息
     *
     * @param appId
     * @return
     */
    AppDetailsDto getAppInfo(String appId);

    /**
     * 获取app和应用信息
     *
     * @param appId
     * @return
     */
    AppDetailsDto getAppWithClientInfo(String appId);

    /**
     * 添加应用
     *
     * @param appName      应用名称
     * @param appNameEn    应用英文名称
     * @param appType      应用类型:server-应用服务 app-手机应用 pc-PC网页应用 wap-手机网页应用
     * @param appIcon      应用图标
     * @param description  应用说明
     * @param os           手机应用操作系统:ios-苹果 android-安卓
     * @param redirectUrls 重定向地址
     * @param scopes       授权范围
     * @param resourceIds  资源服务器ID
     * @param authorities  应用权限,多个用逗号隔开
     * @return 应用信息
     */
    Boolean addAppInfo(String appName,
                       String appNameEn,
                       String appType,
                       String appIcon,
                       String description,
                       String os,
                       String redirectUrls,
                       String scopes,
                       String resourceIds,
                       String authorities);

    /**
     * 修改应用
     *
     * @param appId        应用ID
     * @param appName      应用名称
     * @param appNameEn    应用英文名称
     * @param appType      应用类型:server-应用服务 app-手机应用 pc-PC网页应用 wap-手机网页应用
     * @param appIcon      应用图标
     * @param description  应用说明
     * @param os           手机应用操作系统:ios-苹果 android-安卓
     * @param redirectUrls 重定向地址
     * @param scopes       授权范围
     * @param resourceIds  资源服务器ID
     * @param authorities  应用权限,多个用逗号隔开
     * @return 应用信息
     */
    Boolean updateInfo(String appId,
                       String appName,
                       String appNameEn,
                       String appType,
                       String appIcon,
                       String description,
                       String os,
                       String redirectUrls,
                       String scopes,
                       String resourceIds,
                       String authorities);

    /**
     * 重置秘钥
     *
     * @param appId
     * @return
     */
    Boolean restSecret(String appId);

    /**
     * 删除应用
     *
     * @param appId
     * @return
     */
    Boolean removeApp(String appId);
}
