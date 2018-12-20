package com.github.lyd.base.producer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.lyd.base.producer.mapper.SystemAppMapper;
import com.github.lyd.base.producer.service.SystemAppService;
import com.github.lyd.base.producer.service.feign.ClientDetailsRemoteServiceClient;
import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.gen.SnowflakeIdGenerator;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.utils.RandomValueUtils;
import com.github.lyd.base.client.constans.RbacConstans;
import com.github.lyd.base.client.dto.SystemAppDto;
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
    private SystemAppMapper appInfoMapper;

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
        List<SystemAppDto> list = appInfoMapper.selectAppList(null);
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
        return appInfoMapper.getApp(appId);
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
     * @param appName      应用名称
     * @param appNameEn    应用英文名称
     * @param appType      应用类型:server-应用服务 app-手机应用 pc-PC网页应用 wap-手机网页应用
     * @param appIcon      应用图标
     * @param description  应用说明
     * @param os           手机应用操作系统:ios-苹果 android-安卓
     * @param redirectUrls 重定向地址
     * @param scopes       授权范围
     * @param resourceIds  资源服务器ID
     * @param authorities  应用权限,多个用逗号隔开
     * @return 应用信息
     */
    @Override
    public Boolean addAppInfo(String appName, String appNameEn, String appType, String appIcon, String description, String os, String redirectUrls, String scopes, String resourceIds, String authorities) {
        String clientId = String.valueOf(idGenerator.nextId());
        String clientSecret = RandomValueUtils.uuid();
        SystemAppDto appInfo = new SystemAppDto();
        appInfo.setAppName(appName);
        appInfo.setAppNameEn(appNameEn);
        appInfo.setAppType(appType);
        appInfo.setAppIcon(appIcon);
        appInfo.setAppDesc(description);
        appInfo.setAppOs(os);
        appInfo.setAppId(clientId);
        appInfo.setAppSecret(clientSecret);
        appInfo.setCreateTime(new Date());
        appInfo.setUpdateTime(appInfo.getCreateTime());
        int result = appInfoMapper.insertSelective(appInfo);
        String clientInfoJson = JSONObject.toJSONString(appInfo);
        String grantTypes = RbacConstans.getGrantTypes(appType);
        Boolean autoApprove = RbacConstans.isAutoApprove(appType);
        ResultBody<Boolean> resp = clientDetailsRemoteServiceClient.addClient(clientId, clientSecret, grantTypes, autoApprove, redirectUrls, scopes, resourceIds, authorities, clientInfoJson);
        if (!(resp.isOk() && resp.getData())) {
            // 回滚事物
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result > 0 && resp.isOk() && resp.getData();
    }

    /**
     * 修改应用
     *
     * @param appId        应用ID
     * @param appName      应用名称
     * @param appNameEn    应用英文名称
     * @param appType      应用类型:server-应用服务 app-手机应用 pc-PC网页应用 wap-手机网页应用
     * @param appIcon      应用图标
     * @param description  应用说明
     * @param os           手机应用操作系统:ios-苹果 android-安卓
     * @param redirectUrls 重定向地址
     * @param scopes       授权范围
     * @param resourceIds  资源服务器ID
     * @param authorities  应用权限,多个用逗号隔开
     * @return 应用信息
     */
    @Override
    public Boolean updateInfo(String appId, String appName, String appNameEn, String appType, String appIcon, String description, String os, String redirectUrls, String scopes, String resourceIds, String authorities) {
        SystemAppDto appInfo = getAppInfo(appId);
        if (appInfo == null) {
            throw new OpenMessageException(appId + "应用不存在!");
        }
        appInfo.setAppName(appName);
        appInfo.setAppNameEn(appNameEn);
        appInfo.setAppIcon(appIcon);
        appInfo.setAppType(appType);
        appInfo.setAppDesc(description);
        appInfo.setAppOs(os);
        appInfo.setUpdateTime(new Date());
        int result = appInfoMapper.updateByPrimaryKeySelective(appInfo);
        String clientInfoJson = JSONObject.toJSONString(appInfo);
        String grantTypes = RbacConstans.getGrantTypes(appType);
        Boolean autoApprove = RbacConstans.isAutoApprove(appType);
        ResultBody<Boolean> resp = clientDetailsRemoteServiceClient.updateClient(appInfo.getAppId(), grantTypes, autoApprove, redirectUrls, scopes, resourceIds, authorities, clientInfoJson);
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
        int result = appInfoMapper.updateByPrimaryKeySelective(appInfo);
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
        int result = appInfoMapper.deleteByPrimaryKey(appInfo.getAppId());
        ResultBody<Boolean> resp = clientDetailsRemoteServiceClient.removeClinet(appInfo.getAppId());
        if (!(resp.isOk() && resp.getData())) {
            // 回滚事物
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result > 0 && resp.isOk() && resp.getData();
    }

}
