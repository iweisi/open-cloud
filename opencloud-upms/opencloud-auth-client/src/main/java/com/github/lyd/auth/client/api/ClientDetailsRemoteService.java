package com.github.lyd.auth.client.api;

import com.github.lyd.auth.client.dto.ClientDetailsDto;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.ResultBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 客户端信息API接口
 *
 * @author: liuyadu
 * @date: 2018/11/2 12:05
 * @description:
 */
public interface ClientDetailsRemoteService {
    /**
     * 获取客户端列表
     *
     * @return
     */
    @GetMapping("/client")
    ResultBody<PageList<ClientDetailsDto>> client();


    /**
     * 获取客户端信息
     *
     * @param clientId 客户端Id
     * @return 客户端信息
     */
    @GetMapping("/client/{clientId}")
    ResultBody<ClientDetailsDto> getClient(
            @PathVariable("clientId") String clientId
    );

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
    ResultBody<Boolean> addClient(
            @RequestParam(value = "clientId") String clientId,
            @RequestParam(value = "clientSecret") String clientSecret,
            @RequestParam(value = "grantTypes", required = false) String grantTypes,
            @RequestParam(value = "autoApprove", required = false) boolean autoApprove,
            @RequestParam(value = "redirectUrls", required = false) String redirectUrls,
            @RequestParam(value = "scopes", required = false) String scopes,
            @RequestParam(value = "resourceIds", required = false) String resourceIds,
            @RequestParam(value = "authorities", required = false) String authorities,
            @RequestParam(value = "clientInfo", required = false) String clientInfo
    );

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
    ResultBody<Boolean> updateClient(
            @RequestParam(value = "clientId") String clientId,
            @RequestParam(value = "grantTypes", required = false) String grantTypes,
            @RequestParam(value = "autoApprove", required = false) boolean autoApprove,
            @RequestParam(value = "redirectUrls", required = false) String redirectUrls,
            @RequestParam(value = "scopes", required = false) String scopes,
            @RequestParam(value = "resourceIds", required = false) String resourceIds,
            @RequestParam(value = "authorities", required = false) String authorities,
            @RequestParam(value = "clientInfo", required = false) String clientInfo
    );

    /**
     * 重置秘钥
     *
     * @param clientId     客户端ID
     * @param clientSecret 客户端秘钥
     * @return
     */
    @PostMapping("/client/reset")
    ResultBody<Boolean> resetSecret(
            @RequestParam(value = "clientId") String clientId,
            @RequestParam(value = "clientSecret") String clientSecret
    );


    /**
     * 删除客户端
     *
     * @param clientId 客户端Id
     * @return 客户端信息
     */
    @PostMapping("/client/remove")
    ResultBody removeClinet(
            @RequestParam(value = "clientId") String clientId
    );
}
