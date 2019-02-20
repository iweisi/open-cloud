package com.github.lyd.base.producer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.lyd.base.client.constants.BaseConstants;
import com.github.lyd.base.client.dto.SystemAppDto;
import com.github.lyd.base.client.entity.SystemApp;
import com.github.lyd.base.producer.mapper.SystemAppMapper;
import com.github.lyd.base.producer.service.SystemApiService;
import com.github.lyd.base.producer.service.SystemAppService;
import com.github.lyd.base.producer.service.SystemGrantAccessService;
import com.github.lyd.base.producer.service.feign.ClientDetailsRemoteServiceClient;
import com.github.lyd.common.autoconfigure.GatewayProperties;
import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.gen.SnowflakeIdGenerator;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.utils.RandomValueUtils;
import com.github.lyd.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author: liuyadu
 * @date: 2018/11/12 16:26
 * @description:
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemAppServiceImpl implements SystemAppService {

    @Autowired
    private SystemAppMapper systemAppMapper;
    @Autowired
    private ClientDetailsRemoteServiceClient clientDetailsRemoteServiceClient;
    @Autowired
    private SnowflakeIdGenerator idGenerator;
    @Autowired
    private SystemApiService systemApiService;
    @Autowired
    private SystemGrantAccessService systemGrantAccessService;
    @Autowired
    private GatewayProperties gatewayProperties;

    /**
     * 查询应用列表
     *
     * @param pageParams
     * @return
     */
    @Override
    public PageList<SystemApp> findListPage(PageParams pageParams, String keyword) {
        ExampleBuilder builder = new ExampleBuilder(SystemApp.class);
        Example example = builder.criteria()
                .orLike("appName", keyword)
                .orLike("appNameEn", keyword).end().build();
        List<SystemApp> list = systemAppMapper.selectByExample(example);
        return new PageList(list);
    }

    /**
     * 获取app详情
     *
     * @param appId
     * @return
     */
    @Override
    public SystemApp getAppInfo(String appId) {
        return systemAppMapper.selectByPrimaryKey(appId);
    }

    /**
     * 获取app和应用信息
     *
     * @param appId
     * @return
     */
    @Override
    public SystemAppDto getAppWithClientInfo(String appId) {
        SystemApp appInfo = getAppInfo(appId);
        if (appInfo == null) {
            return null;
        }
        SystemAppDto appDto = new SystemAppDto();
        BeanUtils.copyProperties(appInfo, appDto);
        try {
            appDto.setClientInfo(clientDetailsRemoteServiceClient.getClient(appInfo.getAppId()).getData());
        } catch (Exception e) {
            log.error("clientDetailsRemoteServiceClient.getClient error:{}", e.getMessage());
        }
        return appDto;
    }

    /**
     * 添加应用
     *
     * @param app
     * @return 应用信息
     */
    @Override
    public String addAppInfo(SystemAppDto app) {
        String clientId = String.valueOf(idGenerator.nextId());
        String clientSecret = RandomValueUtils.uuid();
        app.setAppId(clientId);
        app.setAppSecret(clientSecret);
        app.setCreateTime(new Date());
        app.setUpdateTime(app.getCreateTime());
        if (app.getIsPersist() == null) {
            app.setIsPersist(BaseConstants.DISABLED);
        }
        systemAppMapper.insertSelective(app);
        String clientInfoJson = JSONObject.toJSONString(app);
        // 功能授权
        if(StringUtils.isNotBlank(app.getAuthorities())){
            app.setAuthorities(grantAccess(app.getAppId(), app.getAuthorities().split(",")));
        }
        // 保持客户端信息
        ResultBody<Boolean> resp = clientDetailsRemoteServiceClient.addClient(clientId, clientSecret, BaseConstants.DEFAULT_OAUTH2_GRANT_TYPES, "", app.getRedirectUrls(), app.getScopes(), app.getResourceIds(), app.getAuthorities(), clientInfoJson);
        if (!resp.isOk()) {
            // 回滚事物
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return app.getAppId();
    }

    /**
     * 修改应用
     *
     * @param app 应用
     * @return 应用信息
     */
    @Override
    public void updateInfo(SystemAppDto app) {
        SystemApp appInfo = getAppInfo(app.getAppId());
        if (appInfo == null) {
            throw new OpenMessageException(app.getAppId() + "应用不存在!");
        }
        BeanUtils.copyProperties(app, appInfo);
        appInfo.setUpdateTime(new Date());
        int result = systemAppMapper.updateByPrimaryKeySelective(appInfo);
        String clientInfoJson = JSONObject.toJSONString(appInfo);
        // 功能授权
        app.setAuthorities(grantAccess(app.getAppId(), app.getAuthorities().split(",")));
        // 修改客户端信息
        ResultBody<Boolean> resp = clientDetailsRemoteServiceClient.updateClient(app.getAppId(), app.getGrantTypes(), "", app.getRedirectUrls(), app.getScopes(), app.getResourceIds(), app.getAuthorities(), clientInfoJson);
        if (!resp.isOk()) {
            // 手动事物回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    /**
     * 重置秘钥
     *
     * @param appId
     * @return
     */
    @Override
    public String restSecret(String appId) {
        if (gatewayProperties.getClientId().equals(appId)) {
            throw new OpenMessageException(String.format("保留数据,不允许修改"));
        }
        SystemApp appInfo = getAppInfo(appId);
        if (appInfo == null) {
            throw new OpenMessageException(appId + "应用不存在!");
        }
        // 生成新的密钥
        String clientSecret = RandomValueUtils.uuid();
        appInfo.setAppSecret(clientSecret);
        appInfo.setUpdateTime(new Date());
        int result = systemAppMapper.updateByPrimaryKeySelective(appInfo);
        ResultBody<Boolean> resp = clientDetailsRemoteServiceClient.resetSecret(appInfo.getAppId(), clientSecret);
        if (!resp.isOk()) {
            // 手动事物回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return (result > 0 && resp.isOk()) ? clientSecret : null;
    }

    /**
     * 删除应用
     *
     * @param appId
     * @return
     */
    @Override
    public void removeApp(String appId) {
        SystemApp appInfo = getAppInfo(appId);
        if (appInfo == null) {
            throw new OpenMessageException(appId + "应用不存在!");
        }
        if (appInfo.getIsPersist().equals(BaseConstants.ENABLED)) {
            throw new OpenMessageException(String.format("保留数据,不允许删除"));
        }
        systemAppMapper.deleteByPrimaryKey(appInfo.getAppId());
        // 移除授权
        systemGrantAccessService.removeGrantAccess(appInfo.getAppId(), BaseConstants.AUTHORITY_PREFIX_APP, null);
        ResultBody<Boolean> resp = clientDetailsRemoteServiceClient.removeClinet(appInfo.getAppId());
        if (!resp.isOk()) {
            // 回滚事物
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    /**
     * 授权功能
     *
     * @param appId    应用ID
     * @param apiCodes api编码
     * @return authorities 授权后的权限标识
     */
    @Override
    public String grantAccess(String appId, String... apiCodes) {
        List<String> apiIds = systemApiService.findIdsByCodes(apiCodes);
        return systemGrantAccessService.addGrantAccess(appId, BaseConstants.AUTHORITY_PREFIX_APP, BaseConstants.RESOURCE_TYPE_API, apiIds.toArray(new String[apiIds.size()]));
    }

}
