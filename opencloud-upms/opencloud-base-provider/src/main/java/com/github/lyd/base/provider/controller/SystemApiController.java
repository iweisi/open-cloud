package com.github.lyd.base.provider.controller;

import com.github.lyd.base.client.api.SystemApiRemoteService;
import com.github.lyd.base.client.entity.SystemApi;
import com.github.lyd.base.provider.service.SystemApiService;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuyadu
 */
@Api(tags = "接口资源管理")
@RestController
public class SystemApiController implements SystemApiRemoteService {
    @Autowired
    private SystemApiService apiService;

    /**
     * 获取接口分页列表
     *
     * @return
     */
    @ApiOperation(value = "获取接口分页列表", notes = "获取接口分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "显示条数:最大999", paramType = "form"),
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/api")
    @Override
    public ResultBody<PageList<SystemApi>> api(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        return ResultBody.success(apiService.findListPage(new PageParams(page, limit), keyword));
    }


    /**
     * 获取接口列表
     *
     * @param keyword
     * @return
     */
    @ApiOperation(value = "获取接口列表", notes = "获取接口列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/api/list")
    @Override
    public ResultBody<PageList<SystemApi>> apiList(String keyword) {
        return ResultBody.success(apiService.findList(keyword));
    }

    /**
     * 获取接口资源
     *
     * @param apiId ApiId
     * @return 应用信息
     */
    @ApiOperation(value = "获取接口资源", notes = "获取接口资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiId", required = true, value = "ApiId", paramType = "path"),
    })
    @GetMapping("/api/{apiId}")
    @Override
    public ResultBody<SystemApi> getApi(@PathVariable("apiId") Long apiId) {
        return ResultBody.success(apiService.getApi(apiId));
    }

    /**
     * 添加接口资源
     *
     * @param apiCode   接口编码
     * @param apiName   接口名称
     * @param serviceId 服务ID
     * @param path      请求路径
     * @param status    是否启用
     * @param priority  优先级越小越靠前
     * @param apiDesc   描述
     * @return
     */
    @ApiOperation(value = "添加接口资源", notes = "添加接口资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiCode", required = true, value = "接口编码", paramType = "form"),
            @ApiImplicitParam(name = "apiName", required = true, value = "接口名称", paramType = "form"),
            @ApiImplicitParam(name = "apiCategory", required = true, value = "接口分类", paramType = "form"),
            @ApiImplicitParam(name = "serviceId", required = true, value = "服务ID", paramType = "form"),
            @ApiImplicitParam(name = "path", required = false, value = "请求路径", paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, defaultValue = "1", allowableValues = "0,1", value = "是否启用", paramType = "form"),
            @ApiImplicitParam(name = "priority", required = false, value = "优先级越小越靠前", paramType = "form"),
            @ApiImplicitParam(name = "apiDesc", required = false, value = "描述", paramType = "form"),
    })
    @PostMapping("/api/add")
    @Override
    public ResultBody<Long> addApi(
            @RequestParam(value = "apiCode") String apiCode,
            @RequestParam(value = "apiName") String apiName,
            @RequestParam(value = "apiCategory") String apiCategory,
            @RequestParam(value = "serviceId") String serviceId,
            @RequestParam(value = "path", required = false, defaultValue = "") String path,
            @RequestParam(value = "status", defaultValue = "1") Integer status,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority,
            @RequestParam(value = "apiDesc", required = false, defaultValue = "") String apiDesc
    ) {
        SystemApi api = new SystemApi();
        api.setApiCode(apiCode);
        api.setApiName(apiName);
        api.setApiCategory(apiCategory);
        api.setServiceId(serviceId);
        api.setPath(path);
        api.setStatus(status);
        api.setPriority(priority);
        api.setApiDesc(apiDesc);
        Long result = apiService.addApi(api);
        return ResultBody.success(result);
    }

    /**
     * 编辑接口资源
     *
     * @param apiId     接口ID
     * @param apiCode   接口编码
     * @param apiName   接口名称
     * @param serviceId 服务ID
     * @param path      请求路径
     * @param status    是否启用
     * @param priority  优先级越小越靠前
     * @param apiDesc   描述
     * @return
     */
    @ApiOperation(value = "编辑接口资源", notes = "编辑接口资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiId", required = true, value = "接口Id", paramType = "form"),
            @ApiImplicitParam(name = "apiCode", required = true, value = "接口编码", paramType = "form"),
            @ApiImplicitParam(name = "apiName", required = true, value = "接口名称", paramType = "form"),
            @ApiImplicitParam(name = "apiCategory", required = true, value = "接口分类", paramType = "form"),
            @ApiImplicitParam(name = "serviceId", required = true, value = "服务ID", paramType = "form"),
            @ApiImplicitParam(name = "path", required = false, value = "请求路径", paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, defaultValue = "1", allowableValues = "0,1", value = "是否启用", paramType = "form"),
            @ApiImplicitParam(name = "priority", required = false, value = "优先级越小越靠前", paramType = "form"),
            @ApiImplicitParam(name = "apiDesc", required = false, value = "描述", paramType = "form"),
    })
    @PostMapping("/api/update")
    @Override
    public ResultBody updateApi(
            @RequestParam("apiId") Long apiId,
            @RequestParam(value = "apiCode") String apiCode,
            @RequestParam(value = "apiName") String apiName,
            @RequestParam(value = "apiCategory") String apiCategory,
            @RequestParam(value = "serviceId") String serviceId,
            @RequestParam(value = "path", required = false, defaultValue = "") String path,
            @RequestParam(value = "status", defaultValue = "1") Integer status,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority,
            @RequestParam(value = "apiDesc", required = false, defaultValue = "") String apiDesc
    ) {
        SystemApi api = new SystemApi();
        api.setApiId(apiId);
        api.setApiCode(apiCode);
        api.setApiName(apiName);
        api.setApiCategory(apiCategory);
        api.setServiceId(serviceId);
        api.setPath(path);
        api.setStatus(status);
        api.setPriority(priority);
        api.setApiDesc(apiDesc);
        apiService.updateApi(api);
        return ResultBody.success();
    }

    /**
     * 更新接口资源状态
     *
     * @param apiId 接口ID
     * @return
     */
    @ApiOperation(value = "更新接口资源状态", notes = "更新接口资源状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiId", required = true, value = "ApiId", paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, defaultValue = "1", allowableValues = "0,1", value = "是否启用", paramType = "form")
    })
    @PostMapping("/api/update/status")
    @Override
    public ResultBody updateStatus(
            @RequestParam("apiId") Long apiId,
            @RequestParam(value = "status", defaultValue = "1") Integer status
    ) {
        apiService.updateStatus(apiId, status);
        return ResultBody.success();
    }

    /**
     * 移除接口资源
     *
     * @param apiId ApiID
     * @return
     */
    @ApiOperation(value = "移除接口资源", notes = "移除接口资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiId", required = true, value = "ApiId", paramType = "form"),
    })
    @PostMapping("/api/remove")
    @Override
    public ResultBody removeApi(
            @RequestParam("apiId") Long apiId
    ) {
        apiService.removeApi(apiId);
        return ResultBody.success();
    }
}
