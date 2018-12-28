package com.github.lyd.auth.producer.controller;

import com.github.lyd.auth.client.api.ClientDetailsRemoteService;
import com.github.lyd.auth.client.dto.ClientDetailsDto;
import com.github.lyd.auth.producer.service.ClientDetailsService;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 认证客户端管理
 *
 * @author: liuyadu
 * @date: 2018/11/2 11:47
 * @description:
 */

@RestController
public class ClientDetailsController implements ClientDetailsRemoteService {

    @Autowired
    private ClientDetailsService clinetInfoService;


    /**
     * 客户端列表
     *
     * @return
     */
    @GetMapping("/client")
    @Override
    public ResultBody<PageList<ClientDetailsDto>> client() {
        return ResultBody.success(clinetInfoService.findAllList());
    }

    /**
     * 获取客户端信息
     *
     * @param clientId 客户端Id
     * @return 客户端信息
     */
    @GetMapping("/client/{clientId}")
    @Override
    public ResultBody<ClientDetailsDto> getClient(
            @PathVariable("clientId") String clientId
    ) {
        return ResultBody.success(clinetInfoService.getClinet(clientId));
    }

    /**
     * 添加客户端
     *
     * @param clientId     客户端ID
     * @param clientSecret 客户端秘钥
     * @param grantTypes   授权类型
     * @param autoApprove  自动授权
     * @param redirectUrls 授权重定向地址
     * @param scopes       授权范围
     * @param resourceIds  资源服务ID
     * @param authorities  权限
     * @param clientInfo   客户端附加信息,json字符串
     * @return
     */
    @PostMapping("/client/add")
    @Override
    public ResultBody<Boolean> addClient(
            @RequestParam(value = "clientId") String clientId,
            @RequestParam(value = "clientSecret") String clientSecret,
            @RequestParam(value = "grantTypes", required = false) String grantTypes,
            @RequestParam(value = "autoApprove", required = false) boolean autoApprove,
            @RequestParam(value = "redirectUrls", required = false) String redirectUrls,
            @RequestParam(value = "scopes", required = false) String scopes,
            @RequestParam(value = "resourceIds", required = false) String resourceIds,
            @RequestParam(value = "authorities", required = false) String authorities,
            @RequestParam(value = "clientInfo", required = false) String clientInfo
    ) {
        return ResultBody.success(clinetInfoService.addClient(clientId, clientSecret, grantTypes, autoApprove, redirectUrls, scopes, resourceIds, authorities, clientInfo));
    }

    /**
     * 更新客户端
     *
     * @param clientId     客户端ID
     * @param grantTypes   授权类型
     * @param autoApprove  自动授权
     * @param redirectUrls 授权重定向地址
     * @param scopes       授权范围
     * @param resourceIds  资源服务ID
     * @param authorities  权限
     * @param clientInfo   客户端附加信息,json字符串
     * @return
     */
    @PostMapping("/client/update")
    @Override
    public ResultBody<Boolean> updateClient(
            @RequestParam(value = "clientId") String clientId,
            @RequestParam(value = "grantTypes", required = false) String grantTypes,
            @RequestParam(value = "autoApprove", required = false) boolean autoApprove,
            @RequestParam(value = "redirectUrls", required = false) String redirectUrls,
            @RequestParam(value = "scopes", required = false) String scopes,
            @RequestParam(value = "resourceIds", required = false) String resourceIds,
            @RequestParam(value = "authorities", required = false) String authorities,
            @RequestParam(value = "clientInfo", required = false) String clientInfo
    ) {
        return ResultBody.success(clinetInfoService.updateClient(clientId, grantTypes, autoApprove, redirectUrls, scopes, resourceIds, authorities, clientInfo));
    }

    /**
     * 重置秘钥
     *
     * @param clientId     客户端ID
     * @param clientSecret 客户端秘钥
     * @return
     */
    @PostMapping("/client/reset")
    @Override
    public ResultBody<Boolean> resetSecret(
            @RequestParam(value = "clientId") String clientId,
            @RequestParam(value = "clientSecret") String clientSecret
    ) {
        return ResultBody.success(clinetInfoService.restSecret(clientId, clientSecret));
    }

    /**
     * 删除客户端
     *
     * @param clientId 客户端Id
     * @return 客户端信息
     */
    @PostMapping("/client/remove")
    @Override
    public ResultBody removeClinet(
            @RequestParam(value = "clientId") String clientId
    ) {
        return ResultBody.success(clinetInfoService.removeClinet(clientId));
    }
}
