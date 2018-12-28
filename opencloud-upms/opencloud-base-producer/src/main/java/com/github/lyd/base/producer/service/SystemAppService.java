package com.github.lyd.base.producer.service;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.base.client.dto.SystemAppDto;

/**
 * app信息管理
 *
 * @author liuyadu
 */
public interface SystemAppService {

    /**
     * 查询应用列表
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    PageList<SystemAppDto> findListPage(PageParams pageParams, String keyword);

    /**
     * 获取app信息
     *
     * @param appId
     * @return
     */
    SystemAppDto getAppInfo(String appId);

    /**
     * 获取app和应用信息
     *
     * @param appId
     * @return
     */
    SystemAppDto getAppWithClientInfo(String appId);

    /**
     * 添加应用
     *
     * @param app 应用
     * @return 应用信息
     */
    Boolean addAppInfo(SystemAppDto app);

    /**
     * 修改应用
     *
     * @param app 应用
     * @return 应用信息
     */
    Boolean updateInfo(SystemAppDto app);

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
