package com.github.lyd.rbac.producer.service;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.rbac.client.entity.ResourceAction;

/**
 * 动作资源
 */
public interface ActionService {
    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    PageList<ResourceAction> findListPage(PageParams pageParams, String keyword);

    /**
     * 根据主键获取动作
     *
     * @param actionId
     * @return
     */
    ResourceAction getAction(Long actionId);


    /**
     * 检查动作编码是否存在
     *
     * @param actionCode
     * @return
     */
    boolean isExist(String actionCode);


    /**
     * 添加动作资源
     *
     * @param action
     * @return
     */
    boolean addAction(ResourceAction action);

    /**
     * 修改动作资源
     *
     * @param action
     * @return
     */
    boolean updateAction(ResourceAction action);

    /**
     * 更新启用禁用
     *
     * @param actionId
     * @param enable
     * @return
     */
    boolean updateEnable(Long actionId, Boolean enable);

    /**
     * 移除动作
     *
     * @param actionId
     * @return
     */
    boolean removeAction(Long actionId);
}
