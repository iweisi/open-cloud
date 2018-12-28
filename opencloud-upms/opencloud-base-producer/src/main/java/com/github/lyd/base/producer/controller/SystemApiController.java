package com.github.lyd.base.producer.controller;

import com.github.lyd.base.client.api.SystemApiRemoteService;
import com.github.lyd.base.client.entity.SystemApi;
import com.github.lyd.base.producer.service.SystemApiService;
import com.github.lyd.common.annotation.ApiRateLimit;
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
     * Api列表
     *
     * @return
     */
    @ApiOperation(value = "接口列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "显示条数:最大999", paramType = "form"),
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/api")
    @ApiRateLimit(limit = 10, interval = 1000)
    @Override
    public ResultBody<PageList<SystemApi>> api(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        return ResultBody.success(apiService.findListPage(new PageParams(page, limit), keyword));
    }


    /**
     * 获取Api资源列表
     *
     * @param keyword
     * @return
     */
    @ApiOperation(value = "接口列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/api/list")
    @Override
    public ResultBody<PageList<SystemApi>> apiList(String keyword) {
        return ResultBody.success(apiService.findList(keyword));
    }

    /**
     * 获取Api资源
     *
     * @param apiId ApiId
     * @return 应用信息
     */
    @ApiOperation(value = "获取接口资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiId", required = true, value = "ApiId", paramType = "path"),
    })
    @ApiRateLimit(limit = 20, interval = 1000)
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
    @ApiOperation(value = "添加Api资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiCode", required = true, value = "接口编码", paramType = "form"),
            @ApiImplicitParam(name = "apiName", required = true, value = "接口名称", paramType = "form"),
            @ApiImplicitParam(name = "serviceId", required = true, value = "服务ID", paramType = "form"),
            @ApiImplicitParam(name = "path", required = false, value = "请求路径", paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, defaultValue = "1", allowableValues = "0,1", value = "是否启用", paramType = "form"),
            @ApiImplicitParam(name = "priority", required = false, value = "优先级越小越靠前", paramType = "form"),
            @ApiImplicitParam(name = "apiDesc", required = false, value = "描述", paramType = "form"),
    })
    @PostMapping("/api/add")
    @Override
    public ResultBody<Boolean> addApi(
            @RequestParam(value = "apiCode") String apiCode,
            @RequestParam(value = "apiName") String apiName,
            @RequestParam(value = "serviceId") String serviceId,
            @RequestParam(value = "path", required = false, defaultValue = "") String path,
            @RequestParam(value = "status", defaultValue = "1") Integer status,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority,
            @RequestParam(value = "apiDesc", required = false, defaultValue = "") String apiDesc
    ) {
        SystemApi api = new SystemApi();
        api.setApiCode(apiCode);
        api.setApiName(apiName);
        api.setServiceId(serviceId);
        api.setPath(path);
        api.setStatus(status);
        api.setPriority(priority);
        api.setApiDesc(apiDesc);
        return ResultBody.success(apiService.addApi(api));
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
    @ApiOperation(value = "编辑Api资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiId", required = true, value = "接口Id", paramType = "form"),
            @ApiImplicitParam(name = "apiCode", required = true, value = "接口编码", paramType = "form"),
            @ApiImplicitParam(name = "apiName", required = true, value = "接口名称", paramType = "form"),
            @ApiImplicitParam(name = "serviceId", required = true, value = "服务ID", paramType = "form"),
            @ApiImplicitParam(name = "path", required = false, value = "请求路径", paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, defaultValue = "1", allowableValues = "0,1", value = "是否启用", paramType = "form"),
            @ApiImplicitParam(name = "priority", required = false, value = "优先级越小越靠前", paramType = "form"),
            @ApiImplicitParam(name = "apiDesc", required = false, value = "描述", paramType = "form"),
    })
    @PostMapping("/api/update")
    @Override
    public ResultBody<Boolean> updateApi(
            @RequestParam("apiId") Long apiId,
            @RequestParam(value = "apiCode") String apiCode,
            @RequestParam(value = "apiName") String apiName,
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
        api.setServiceId(serviceId);
        api.setPath(path);
        api.setStatus(status);
        api.setPriority(priority);
        api.setApiDesc(apiDesc);
        return ResultBody.success(apiService.updateApi(api));
    }

    /**
     * 更新状态
     *
     * @param apiId 接口ID
     * @return
     */
    @ApiOperation(value = "禁用接口资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiId", required = true, value = "ApiId", paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, defaultValue = "1", allowableValues = "0,1", value = "是否启用", paramType = "form")
    })
    @PostMapping("/api/update/status")
    @Override
    public ResultBody<Boolean> updateStatus(
            @RequestParam("apiId") Long apiId,
            @RequestParam(value = "status", defaultValue = "1") Integer status
    ) {
        return ResultBody.success(apiService.updateStatus(apiId, status));
    }

    /**
     * 移除接口
     *
     * @param apiId ApiID
     * @return
     */
    @ApiOperation(value = "移除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiId", required = true, value = "ApiId", paramType = "form"),
    })
    @PostMapping("/api/remove")
    @Override
    public ResultBody<Boolean> removeApi(
            @RequestParam("apiId") Long apiId
    ) {
        return ResultBody.success(apiService.removeApi(apiId));
    }
}
