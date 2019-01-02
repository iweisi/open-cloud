package com.github.lyd.base.producer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.lyd.base.client.constants.BaseConstants;
import com.github.lyd.base.client.dto.SystemAppDto;
import com.github.lyd.base.client.entity.SystemApp;
import com.github.lyd.base.producer.mapper.SystemAppMapper;
import com.github.lyd.base.producer.service.SystemApiService;
import com.github.lyd.base.producer.service.SystemAppService;
import com.github.lyd.base.producer.service.feign.ClientDetailsRemoteServiceClient;
import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.gen.SnowflakeIdGenerator;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.utils.RandomValueUtils;
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
    public Boolean addAppInfo(SystemAppDto app) {
        String clientId = String.valueOf(idGenerator.nextId());
        String clientSecret = RandomValueUtils.uuid();
        app.setAppId(clientId);
        app.setAppSecret(clientSecret);
        app.setCreateTime(new Date());
        app.setUpdateTime(app.getCreateTime());
        resetAppDevInfo(app);
        int result = systemAppMapper.insertSelective(app);
        String clientInfoJson = JSONObject.toJSONString(app);
        ResultBody<Boolean> resp = clientDetailsRemoteServiceClient.addClient(clientId, clientSecret, BaseConstants.DEFAULT_OAUTH2_GRANT_TYPES, false, app.getRedirectUrls(), app.getScopes(), app.getResourceIds(), app.getAuthorities(), clientInfoJson);
        if (!(resp.isOk() && resp.getData())) {
            // 回滚事物
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result > 0 && resp.isOk() && resp.getData();
    }

    /**
     * 修改应用
     *
     * @param app 应用
     * @return 应用信息
     */
    @Override
    public Boolean updateInfo(SystemAppDto app) {
        SystemApp appInfo = getAppInfo(app.getAppId());
        if (appInfo == null) {
            throw new OpenMessageException(app.getAppId() + "应用不存在!");
        }
        appInfo.setUpdateTime(new Date());
        resetAppDevInfo(app);
        int result = systemAppMapper.updateByPrimaryKeySelective(appInfo);
        String clientInfoJson = JSONObject.toJSONString(appInfo);
        ResultBody<Boolean> resp = clientDetailsRemoteServiceClient.updateClient(app.getAppId(), app.getGrantTypes(), false, app.getRedirectUrls(), app.getScopes(), app.getResourceIds(), app.getAuthorities(), clientInfoJson);
        if (!(resp.isOk() && resp.getData())) {
            // 手动事物回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result > 0 && resp.isOk() && resp.getData();
    }

    private void resetAppDevInfo(SystemAppDto app) {
        if (app.getScopes() != null) {
            String[] scopes = app.getScopes().split(",");
            for (String scope : scopes) {
                if (!systemApiService.isExist(scope)) {
                    throw new OpenMessageException(String.format("scope=%s缺少对应接口", scope));
                }
            }
            String authorities = app.getAuthorities() != null && !app.getAuthorities().startsWith(",") ? "," + app.getAuthorities() : app.getAuthorities();
            // 把授权范围添加到接口权限中
            app.setAuthorities(app.getScopes().concat(authorities));
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
        if (!(resp.isOk() && resp.getData())) {
            // 手动事物回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return (result > 0 && resp.isOk() && resp.getData()) ? clientSecret : null;
    }

    /**
     * 删除应用
     *
     * @param appId
     * @return
     */
    @Override
    public Boolean removeApp(String appId) {
        SystemApp appInfo = getAppInfo(appId);
        if (appInfo == null) {
            throw new OpenMessageException(appId + "应用不存在!");
        }
        int result = systemAppMapper.deleteByPrimaryKey(appInfo.getAppId());
        ResultBody<Boolean> resp = clientDetailsRemoteServiceClient.removeClinet(appInfo.getAppId());
        if (!(resp.isOk() && resp.getData())) {
            // 回滚事物
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result > 0 && resp.isOk() && resp.getData();
    }

}
