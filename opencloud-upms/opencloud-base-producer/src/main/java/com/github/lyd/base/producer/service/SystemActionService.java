package com.github.lyd.base.producer.service;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.base.client.entity.SystemAction;

/**
 * 动作资源
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
     * 根据主键获取动作
     *
     * @param actionId
     * @return
     */
    SystemAction getAction(Long actionId);

    PageList<SystemAction> findList(String keyword);

    /**
     * 检查动作编码是否存在
     *
     * @param actionCode
     * @return
     */
    Boolean isExist(String actionCode);


    /**
     * 添加动作资源
     *
     * @param action
     * @return
     */
    Boolean addAction(SystemAction action);

    /**
     * 修改动作资源
     *
     * @param action
     * @return
     */
    Boolean updateAction(SystemAction action);

    /**
     * 更新启用禁用
     *
     * @param actionId
     * @param status
     * @return
     */
    Boolean updateStatus(Long actionId, Integer status);

    /**
     * 移除动作
     *
     * @param actionId
     * @return
     */
    Boolean removeAction(Long actionId);
}
