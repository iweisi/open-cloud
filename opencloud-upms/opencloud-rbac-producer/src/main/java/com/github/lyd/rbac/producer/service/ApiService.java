package com.github.lyd.rbac.producer.service;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.rbac.client.entity.ResourceApi;

/**
 * Api资源
 */
public interface ApiService {
    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    PageList<ResourceApi> findListPage(PageParams pageParams, String keyword);

    /**
     * 根据主键获取Api
     *
     * @param apiId
     * @return
     */
    ResourceApi getApi(Long apiId);


    /**
     * 检查Api编码是否存在
     *
     * @param apiCode
     * @return
     */
    boolean isExist(String apiCode);

    /**
     * 检查Api编码是否存在
     *
     * @param apiCode
     * @param serviceId
     * @return
     */
    boolean isExist(String apiCode, String serviceId);

    /**
     * 添加Api资源
     *
     * @param api
     * @return
     */
    boolean addApi(ResourceApi api);

    /**
     * 修改Api资源
     *
     * @param api
     * @return
     */
    boolean updateApi(ResourceApi api);

    /**
     * 查询api
     *
     * @param apiCode
     * @param serviceId
     * @return
     */
    ResourceApi getApi(String apiCode, String serviceId);

    /**
     * 更新启用禁用
     *
     * @param apiId
     * @param enable
     * @return
     */
    boolean updateEnable(Long apiId, Boolean enable);

    /**
     * 移除Api
     *
     * @param apiId
     * @return
     */
    boolean removeApi(Long apiId);
}
