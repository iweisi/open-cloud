package com.github.lyd.auth.producer.service.impl;

import com.github.lyd.common.model.PageList;
import com.github.lyd.auth.client.dto.ClientDetailsDto;
import com.github.lyd.auth.producer.service.ClientDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: liuyadu
 * @date: 2018/11/12 16:26
 * @description:
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Autowired
    private JdbcClientDetailsService jdbcClientDetailsService;


    /**
     * 查询所以客户端
     *
     * @return
     */
    @Override
    public PageList<ClientDetailsDto> findAllList() {
        return new PageList(jdbcClientDetailsService.listClientDetails());
    }

    /**
     * 获取客户端信息
     *
     * @param clientId
     * @return
     */
    @Override
    public ClientDetailsDto getClinet(String clientId) {
        ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null) {
            return null;
        }
        ClientDetailsDto clientInfo = new ClientDetailsDto(clientDetails);
        return clientInfo;
    }

    /**
     * 添加客户端
     *
     * @param clientId     应用ID
     * @param clientSecret 应用秘钥
     * @param grantTypes   授权类型
     * @param autoApproveScopes  自动授权
     * @param redirectUrls 授权重定向地址
     * @param scopes       授权范围
     * @param resourceIds  资源服务ID
     * @param authorities  权限
     * @param clientInfo   客户端附加信息,json字符串
     */
    @Override
    public boolean addClient(String clientId, String clientSecret, String grantTypes, String autoApproveScopes, String redirectUrls, String scopes, String resourceIds, String authorities, String clientInfo) {
        ClientDetailsDto client = new ClientDetailsDto(clientId, clientSecret, grantTypes, autoApproveScopes, redirectUrls, scopes, resourceIds, authorities, clientInfo);
        try {
            jdbcClientDetailsService.addClientDetails(client);
        } catch (Exception e) {
            log.error("addClient error:{}",e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 更新客户端
     *
     * @param clientId     应用ID
     * @param grantTypes   授权类型
     * @param autoApproveScopes  自动授权
     * @param redirectUrls 授权重定向地址
     * @param scopes       授权范围
     * @param resourceIds  资源服务ID
     * @param authorities  权限
     * @param clientInfo   客户端附加信息,json字符串
     */
    @Override
    public boolean updateClient(String clientId, String grantTypes, String autoApproveScopes, String redirectUrls, String scopes, String resourceIds, String authorities, String clientInfo) {
        ClientDetailsDto client = new ClientDetailsDto(clientId, null, grantTypes, autoApproveScopes, redirectUrls, scopes, resourceIds, authorities, clientInfo);
        try {
            jdbcClientDetailsService.updateClientDetails(client);
        } catch (Exception e) {
            log.error("updateClient error:{}", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 重置秘钥
     *
     * @param clientId
     * @param clientSecret
     * @return
     */
    @Override
    public boolean restSecret(String clientId, String clientSecret) {
        try {
            jdbcClientDetailsService.updateClientSecret(clientId, clientSecret);
        } catch (Exception e) {
            log.error("restSecret error:{}", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 删除应用
     *
     * @param clientId
     * @return
     */
    @Override
    public boolean removeClinet(String clientId) {
        try {
            jdbcClientDetailsService.removeClientDetails(clientId);
        } catch (Exception e) {
            log.error("deleteClinet error:{}", e.getMessage());
            return false;
        }
        return true;
    }


}
