package com.github.lyd.base.client.api;

import com.github.lyd.base.client.entity.SystemApi;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.ResultBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface SystemApiRemoteService {
    /**
     * 获取接口分页列表
     *
     * @return
     */
    @PostMapping("/api")
    ResultBody<PageList<SystemApi>> api(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    );

    /**
     * 获取接口列表
     *
     * @return
     */
    @PostMapping("/api/list")
    ResultBody<PageList<SystemApi>> apiList(
            @RequestParam(name = "keyword", required = false) String keyword
    );

    /**
     * 获取接口资源
     *
     * @param apiId apiId
     * @return
     */
    @GetMapping("/api/{apiId}")
    ResultBody<SystemApi> getApi(@PathVariable("apiId") Long apiId);

    /**
     * 添加接口资源
     *
     * @param apiCode     Api编码
     * @param apiName     Api名称
     * @param apiCategory 接口分类
     * @param serviceId   服务ID
     * @param path        请求路径
     * @param status      是否启用
     * @param priority    优先级越小越靠前
     * @param apiDesc     描述
     * @return
     */
    @PostMapping("/api/add")
    ResultBody<Boolean> addApi(
            @RequestParam(value = "apiCode") String apiCode,
            @RequestParam(value = "apiName") String apiName,
            @RequestParam(value = "apiCategory") String apiCategory,
            @RequestParam(value = "serviceId") String serviceId,
            @RequestParam(value = "path", required = false, defaultValue = "") String path,
            @RequestParam(value = "status", defaultValue = "1") Integer status,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority,
            @RequestParam(value = "apiDesc", required = false, defaultValue = "") String apiDesc
    );

    /**
     * 编辑接口资源
     *
     * @param apiId     ApiID
     * @param apiCode   Api编码
     * @param apiName   Api名称
     * @param apiCategory 接口分类
     * @param serviceId 服务ID
     * @param path      请求路径
     * @param status    是否启用
     * @param priority  优先级越小越靠前
     * @param apiDesc   描述
     * @return
     */
    @PostMapping("/api/update")
    ResultBody<Boolean> updateApi(
            @RequestParam("apiId") Long apiId,
            @RequestParam(value = "apiCode") String apiCode,
            @RequestParam(value = "apiName") String apiName,
            @RequestParam(value = "apiCategory") String apiCategory,
            @RequestParam(value = "serviceId") String serviceId,
            @RequestParam(value = "path", required = false, defaultValue = "") String path,
            @RequestParam(value = "status", defaultValue = "1") Integer status,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority,
            @RequestParam(value = "apiDesc", required = false, defaultValue = "") String apiDesc
    );

    /**
     * 更新状态
     *
     * @param apiId  ApiID
     * @param status 状态
     * @return
     */
    @PostMapping("/api/update/status")
    ResultBody<Boolean> updateStatus(
            @RequestParam("apiId") Long apiId,
            @RequestParam(value = "status", defaultValue = "1") Integer status
    );

    /**
     * 移除Api
     *
     * @param apiId ApiID
     * @return
     */
    @PostMapping("/api/remove")
    ResultBody<Boolean> removeApi(
            @RequestParam("apiId") Long apiId
    );
}
