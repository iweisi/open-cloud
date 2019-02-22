package com.github.lyd.base.provider.controller;

import com.github.lyd.base.client.api.SystemAccessLogsRemoteService;
import com.github.lyd.base.client.entity.SystemAccessLogs;
import com.github.lyd.base.provider.service.SystemAccessLogsService;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyadu
 */
@Api(tags = "访问日志")
@RestController
public class SystemAccessLogsController implements SystemAccessLogsRemoteService {
    @Autowired
    private SystemAccessLogsService accessLogsServiceService;

    /**
     * 获取访问日志分页列表
     *
     * @return
     */
    @ApiOperation(value = "获取访问日志分页列表",notes = "获取访问日志分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "显示条数:最大999", paramType = "form"),
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/access/logs")
    @Override
    public ResultBody<PageList<SystemAccessLogs>> accessLogs(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        return ResultBody.success(accessLogsServiceService.findListPage(new PageParams(page, limit), keyword));
    }


}
