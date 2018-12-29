package com.github.lyd.base.producer.controller;

import com.github.lyd.base.client.api.SystemGrantAccessRemoteService;
import com.github.lyd.base.client.entity.SystemGrantAccess;
import com.github.lyd.base.producer.service.SystemGrantAccessService;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * 获取已授权访问列表
     *
     * @return
     */
    @ApiOperation(value = "获取已授权访问列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "显示条数:最大999", paramType = "form"),
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/user")
    @Override
    public ResultBody<PageList<SystemGrantAccess>> grantAccess(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        return ResultBody.success(systemAccessService.findListPage(new PageParams(page, limit), keyword));
    }

    /**
     * 获取已授权访问列表
     *
     * @return 应用信息
     */
    @ApiOperation(value = "获取已授权访问列表")
    @GetMapping("/grant/access/list")
    @Override
    public ResultBody<List<SystemGrantAccess>> grantAccessList() {
        return ResultBody.success(systemAccessService.getGrantAccessList());
    }


}
