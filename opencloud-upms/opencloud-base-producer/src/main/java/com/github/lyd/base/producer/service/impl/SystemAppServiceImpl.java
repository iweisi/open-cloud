package com.github.lyd.base.producer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.lyd.base.client.dto.SystemAppDto;
import com.github.lyd.base.producer.mapper.SystemAppMapper;
import com.github.lyd.base.producer.service.SystemAppService;
import com.github.lyd.base.producer.service.feign.ClientDetailsRemoteServiceClient;
import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.gen.SnowflakeIdGenerator;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.utils.RandomValueUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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

    /**
     * 查询应用列表
     *
     * @param pageParams
     * @return
     */
    @Override
    public PageList<SystemAppDto> findListPage(PageParams pageParams, String keyword) {
        PageHelper.startPage(pageParams.getPage(),pageParams.getLimit(),pageParams.getOrderBy());
        List<SystemAppDto> list = systemAppMapper.selectAppList(null);
        return new PageList(list);
    }

    /**
     * 获取app详情
     *
     * @param appId
     * @return
     */
    @Override
    public SystemAppDto getAppInfo(String appId) {
        return systemAppMapper.getApp(appId);
    }

    /**
     * 获取app和应用信息
     *
     * @param appId
     * @return
     */
    @Override
    public SystemAppDto getAppWithClientInfo(String appId) {
        SystemAppDto appInfo = getAppInfo(appId);
        if (appInfo == null) {
            return null;
        }
        try {
            appInfo.setClientInfo(clientDetailsRemoteServiceClient.getClient(appInfo.getAppId()).getData());
        }catch (Exception e){
            log.error("clientDetailsRemoteServiceClient.getClient error:{}",e.getMessage());
        }
        return appInfo;
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
        int result = systemAppMapper.insertSelective(app);
        String clientInfoJson = JSONObject.toJSONString(app);
        ResultBody<Boolean> resp = clientDetailsRemoteServiceClient.addClient(clientId, clientSecret, app.getGrantTypes(), false,  app.getRedirectUrls(), app.getScopes(), app.getResourceIds(), app.getAuthorities(), clientInfoJson);
        if (!(resp.isOk() && resp.getData())) {
            // 回滚事物
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result > 0 && resp.isOk() && resp.getData();
    }

    /**
     * 修改应用
     *
     * @param app        应用
     * @return 应用信息
     */
    @Override
    public Boolean updateInfo(SystemAppDto app) {
        SystemAppDto appInfo = getAppInfo(app.getAppId());
        if (appInfo == null) {
            throw new OpenMessageException(app.getAppId() + "应用不存在!");
        }
        appInfo.setUpdateTime(new Date());
        int result = systemAppMapper.updateByPrimaryKeySelective(appInfo);
        String clientInfoJson = JSONObject.toJSONString(appInfo);
        ResultBody<Boolean> resp = clientDetailsRemoteServiceClient.updateClient(app.getAppId(),  app.getGrantTypes(), false,  app.getRedirectUrls(), app.getScopes(), app.getResourceIds(), app.getAuthorities(), clientInfoJson);
        if (!(resp.isOk() && resp.getData())) {
            // 手动事物回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result > 0 && resp.isOk() && resp.getData();
    }

    /**
     * 重置秘钥
     *
     * @param appId
     * @return
     */
    @Override
    public Boolean restSecret(String appId) {
        SystemAppDto appInfo = getAppInfo(appId);
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
        return result > 0 && resp.isOk() && resp.getData();
    }

    /**
     * 删除应用
     *
     * @param appId
     * @return
     */
    @Override
    public Boolean removeApp(String appId) {
        SystemAppDto appInfo = getAppInfo(appId);
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
