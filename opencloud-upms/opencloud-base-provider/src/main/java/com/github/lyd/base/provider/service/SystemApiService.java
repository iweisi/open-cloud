package com.github.lyd.base.provider.service;

import com.github.lyd.base.client.entity.SystemApi;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;

import java.util.List;

/**
 * 接口资源
 */
public interface SystemApiService {
    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    PageList<SystemApi> findListPage(PageParams pageParams, String keyword);

    /**
     * 查询列表
     * @param keyword
     * @return
     */
    PageList<SystemApi> findList(String keyword);

    /**
     * 根据主键获取接口
     *
     * @param apiId
     * @return
     */
    SystemApi getApi(Long apiId);


    /**
     * 检查接口编码是否存在
     *
     * @param apiCode
     * @return
     */
    Boolean isExist(String apiCode);

    /**
     * 添加接口
     *
     * @param api
     * @return
     */
    Long addApi(SystemApi api);

    /**
     * 修改接口
     *
     * @param api
     * @return
     */
    void updateApi(SystemApi api);

    /**
     * 查询接口
     *
     * @param apiCode
     * @param serviceId
     * @return
     */
    SystemApi getApi(String apiCode, String serviceId);

    /**
     * 更新启用禁用
     *
     * @param apiId
     * @param status
     * @return
     */
    void updateStatus(Long apiId, Integer status);

    /**
     * 移除接口
     *
     * @param apiId
     * @return
     */
    void removeApi(Long apiId);

    /**
     * 根据编码查询ID
     * @param codes
     * @return
     */
     List<String> findIdsByCodes(String...codes);
}
