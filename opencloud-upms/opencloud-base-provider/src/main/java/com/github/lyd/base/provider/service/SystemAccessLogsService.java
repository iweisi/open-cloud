package com.github.lyd.base.provider.service;

import com.github.lyd.base.client.entity.SystemAccessLogs;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;

/**
 * 访问日志
 */
public interface SystemAccessLogsService {
    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    PageList<SystemAccessLogs> findListPage(PageParams pageParams, String keyword);
}
