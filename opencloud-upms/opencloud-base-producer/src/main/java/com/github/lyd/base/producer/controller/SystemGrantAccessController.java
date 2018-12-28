package com.github.lyd.base.producer.controller;

import com.github.lyd.base.client.api.SystemGrantAccessRemoteService;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.base.client.entity.SystemGrantAccess;
import com.github.lyd.base.producer.service.SystemGrantAccessService;
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
public class SystemGrantAccessController implements SystemGrantAccessRemoteService {
    @Autowired
    private SystemGrantAccessService systemAccessService;

    /**
     * 获取授权列表
     *
     * @return 应用信息
     */
    @ApiOperation(value = "获取授权列表")
    @GetMapping("/grant/access")
    @Override
    public ResultBody<List<SystemGrantAccess>> grantAccess() {
        return ResultBody.success(systemAccessService.getGrantAccessList());
    }
}
