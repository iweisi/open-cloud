package com.github.lyd.sys.producer.controller;

import com.github.lyd.common.model.ResultBody;
import com.github.lyd.sys.client.api.SystemAccessRemoteService;
import com.github.lyd.sys.client.entity.SystemAccess;
import com.github.lyd.sys.producer.service.SystemAccessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: liuyadu
 * @date: 2018/11/26 18:20
 * @description:
 */
@Api(tags = "授权管理")
@RestController
public class SystemAccessController implements SystemAccessRemoteService {
    @Autowired
    private SystemAccessService systemAccessService;

    /**
     * 获取授权列表
     *
     * @return 应用信息
     */
    @ApiOperation(value = "获取授权列表")
    @GetMapping("/access")
    @Override
    public ResultBody<List<SystemAccess>> access() {
        return ResultBody.success(systemAccessService.getAccessList());
    }
}
