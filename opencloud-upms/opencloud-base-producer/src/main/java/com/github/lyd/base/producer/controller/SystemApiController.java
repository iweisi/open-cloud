package com.github.lyd.base.producer.controller;

import com.github.lyd.base.producer.service.SystemApiService;
import com.github.lyd.common.annotation.ApiRateLimit;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.base.client.api.SystemApiRemoteService;
import com.github.lyd.base.client.entity.SystemApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuyadu
 */
@Api(tags = "Api资源")
@RestController
public class SystemApiController implements SystemApiRemoteService {
    @Autowired
    private SystemApiService apiService;

    /**
     * Api列表
     *
     * @return
     */
    @ApiOperation(value = "Api列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "显示条数:最大999", paramType = "form"),
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/apis")
    @ApiRateLimit(limit = 10,interval = 1000)
    @Override
    public ResultBody<PageList<SystemApi>> apis(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        return ResultBody.success(apiService.findListPage(new PageParams(page, limit), keyword));
    }

    /**
     * 获取Api资源
     *
     * @param apiId ApiId
     * @return 应用信息
     */
    @ApiOperation(value = "获取Api资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiId", required = true, value = "ApiId", paramType = "path"),
    })
    @ApiRateLimit(limit = 20,interval = 1000)
    @GetMapping("/apis/{apiId}")
    @Override
    public ResultBody<SystemApi> getApi(@PathVariable("apiId") Long apiId) {
        return ResultBody.success(apiService.getApi(apiId));
    }

    /**
     * 添加Api资源
     *
     * @param apiCode     Api编码
     * @param apiName     Api名称
     * @param serviceId   服务ID
     * @param url         请求路径
     * @param enabled     是否启用
     * @param priority    优先级越小越靠前
     * @param apiDesc 描述
     * @return
     */
    @ApiOperation(value = "添加Api资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiCode", required = true, value = "Api编码", paramType = "form"),
            @ApiImplicitParam(name = "apiName", required = true, value = "Api名称", paramType = "form"),
            @ApiImplicitParam(name = "serviceId", required = true, value = "服务ID", paramType = "form"),
            @ApiImplicitParam(name = "url", required = false, value = "请求路径", paramType = "form"),
            @ApiImplicitParam(name = "enabled",required = true, defaultValue = "true", allowableValues = "true,false", value = "是否启用", paramType = "form"),
            @ApiImplicitParam(name = "priority", required = false, value = "优先级越小越靠前", paramType = "form"),
            @ApiImplicitParam(name = "apiDesc", required = false, value = "描述", paramType = "form"),
    })
    @PostMapping("/apis/add")
    @Override
    public ResultBody<Boolean> addApi(
            @RequestParam(value = "apiCode") String apiCode,
            @RequestParam(value = "apiName") String apiName,
            @RequestParam(value = "serviceId") String serviceId,
            @RequestParam(value = "url", required = false, defaultValue = "") String url,
            @RequestParam(value = "enabled", defaultValue = "true") Boolean enabled,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority,
            @RequestParam(value = "apiDesc", required = false, defaultValue = "") String apiDesc
    ) {
        SystemApi api = new SystemApi();
        api.setApiCode(apiCode);
        api.setApiName(apiName);
        api.setServiceId(serviceId);
        api.setUrl(url);
        api.setEnabled(enabled);
        api.setPriority(priority);
        api.setApiDesc(apiDesc);
        return ResultBody.success(apiService.addApi(api));
    }

    /**
     * 编辑Api资源
     *
     * @param apiId       ApiID
     * @param apiCode     Api编码
     * @param apiName     Api名称
     * @param serviceId   服务ID
     * @param url         请求路径
     * @param enabled     是否启用
     * @param priority    优先级越小越靠前
     * @param apiDesc 描述
     * @return
     */
    @ApiOperation(value = "编辑Api资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiId", required = true, value = "apiId", paramType = "form"),
            @ApiImplicitParam(name = "apiCode", required = true, value = "Api编码", paramType = "form"),
            @ApiImplicitParam(name = "apiName", required = true, value = "Api名称", paramType = "form"),
            @ApiImplicitParam(name = "serviceId", required = true, value = "服务ID", paramType = "form"),
            @ApiImplicitParam(name = "url", required = false, value = "请求路径", paramType = "form"),
            @ApiImplicitParam(name = "enabled", required = true,defaultValue = "true", allowableValues = "true,false", value = "是否启用", paramType = "form"),
            @ApiImplicitParam(name = "priority", required = false, value = "优先级越小越靠前", paramType = "form"),
            @ApiImplicitParam(name = "apiDesc", required = false, value = "描述", paramType = "form"),
    })
    @PostMapping("/apis/update")
    @Override
    public ResultBody<Boolean> updateApi(
            @RequestParam("apiId") Long apiId,
            @RequestParam(value = "apiCode") String apiCode,
            @RequestParam(value = "apiName") String apiName,
            @RequestParam(value = "serviceId") String serviceId,
            @RequestParam(value = "url", required = false, defaultValue = "") String url,
            @RequestParam(value = "enabled", defaultValue = "true") Boolean enabled,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority,
            @RequestParam(value = "apiDesc", required = false, defaultValue = "") String apiDesc
    ) {
        SystemApi api = new SystemApi();
        api.setApiId(apiId);
        api.setApiCode(apiCode);
        api.setApiName(apiName);
        api.setServiceId(serviceId);
        api.setUrl(url);
        api.setEnabled(enabled);
        api.setPriority(priority);
        api.setApiDesc(apiDesc);
        return ResultBody.success(apiService.updateApi(api));
    }

    /**
     * 禁用Api资源
     *
     * @param apiId ApiID
     * @return
     */
    @ApiOperation(value = "禁用Api资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiId", required = true, value = "ApiId", paramType = "form"),
    })
    @PostMapping("/apis/disable")
    @Override
    public ResultBody<Boolean> disableApi(
            @RequestParam("apiId") Long apiId
    ) {
        return ResultBody.success(apiService.updateEnable(apiId, false));
    }

    /**
     * 启用Api资源
     *
     * @param apiId ApiID
     * @return
     */
    @ApiOperation(value = "启用Api资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiId", required = true, value = "ApiId", paramType = "form"),
    })
    @PostMapping("/apis/enable")
    @Override
    public ResultBody<Boolean> enableApi(
            @RequestParam("apiId") Long apiId
    ) {
        return ResultBody.success(apiService.updateEnable(apiId, true));
    }

    /**
     * 移除Api
     *
     * @param apiId ApiID
     * @return
     */
    @ApiOperation(value = "移除Api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiId", required = true, value = "ApiId", paramType = "form"),
    })
    @PostMapping("/apis/remove")
    @Override
    public ResultBody<Boolean> removeApi(
            @RequestParam("apiId") Long apiId
    ) {
        return ResultBody.success(apiService.removeApi(apiId));
    }
}
