package com.github.lyd.rbac.producer.controller;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.rbac.client.api.TenantProfileRemoteService;
import com.github.lyd.rbac.client.dto.TenantProfileDto;
import com.github.lyd.rbac.producer.service.TenantProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 租户信息
 *
 * @author liuyadu
 */
@Api(tags = "租户管理")
@RestController
public class TenantProfileController implements TenantProfileRemoteService {
    @Autowired
    private TenantProfileService tenantProfileService;

    /**
     * 租户列表
     *
     * @return
     */
    @ApiOperation(value = "租户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "显示条数:最大999", paramType = "form"),
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/tenants")
    @Override
    public ResultBody<PageList<TenantProfileDto>> tenants(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        return ResultBody.success(tenantProfileService.findListPage(new PageParams(page, limit), keyword));
    }

    /**
     * 添加租户
     *
     * @param username
     * @param password
     * @param state
     * @return
     */
    @ApiOperation(value = "添加租户")
    @PostMapping("/tenants/add")
    @Override
    public ResultBody<TenantProfileDto> addTenant(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "state") Integer state
    ) {
        return null;
    }

    /**
     * 更新租户
     *
     * @param username
     * @param password
     * @param state
     * @return
     */
    @ApiOperation(value = "更新租户")
    @PostMapping("/tenants/update")
    @Override
    public ResultBody<TenantProfileDto> updateTenant(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "state") Integer state
    ) {
        return null;
    }


}
