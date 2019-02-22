package com.github.lyd.base.provider.service;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.base.client.entity.SystemAction;

/**
 * 操作资源
 * @author liuyadu
 */
public interface SystemActionService {
    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    PageList<SystemAction> findListPage(PageParams pageParams, String keyword);

    /**
     * 根据主键获取操作
     *
     * @param actionId
     * @return
     */
    SystemAction getAction(Long actionId);

    PageList<SystemAction> findList(String keyword,Long menuId);

    /**
     * 检查操作编码是否存在
     *
     * @param actionCode
     * @return
     */
    Boolean isExist(String actionCode);


    /**
     * 添加操作资源
     *
     * @param action
     * @return
     */
    Long addAction(SystemAction action);

    /**
     * 修改操作资源
     *
     * @param action
     * @return
     */
    void updateAction(SystemAction action);

    /**
     * 更新启用禁用
     *
     * @param actionId
     * @param status
     * @return
     */
    void updateStatus(Long actionId, Integer status);

    /**
     * 移除操作
     *
     * @param actionId
     * @return
     */
    void removeAction(Long actionId);
}
