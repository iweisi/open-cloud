package com.github.lyd.base.client.api;

import com.github.lyd.base.client.entity.SystemAccessLogs;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.ResultBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface SystemAccessLogsRemoteService {
    /**
     * 获取访问日志分页列表
     *
     * @return
     */
    @PostMapping("/access/logs")
    ResultBody<PageList<SystemAccessLogs>> accessLogs(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    );
}
