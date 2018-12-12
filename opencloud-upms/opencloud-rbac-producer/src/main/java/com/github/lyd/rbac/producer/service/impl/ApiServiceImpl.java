package com.github.lyd.rbac.producer.service.impl;

import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.rbac.client.constans.RbacConstans;
import com.github.lyd.rbac.client.entity.ResourceApi;
import com.github.lyd.rbac.producer.mapper.ResourceApiMapper;
import com.github.lyd.rbac.producer.service.ApiService;
import com.github.lyd.rbac.producer.service.PermissionService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author liuyadu
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiServiceImpl implements ApiService {
    @Autowired
    private ResourceApiMapper resourceApiMapper;
    @Autowired
    private PermissionService permissionService;

    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    @Override
    public PageList<ResourceApi> findListPage(PageParams pageParams, String keyword) {
        PageHelper.startPage(pageParams.getPage(), pageParams.getLimit(), pageParams.getOrderBy());
        ExampleBuilder builder = new ExampleBuilder(ResourceApi.class);
        Example example = builder.criteria()
                .orLike("apiCode", keyword)
                .orLike("apiName", keyword).end().build();
        List<ResourceApi> list = resourceApiMapper.selectByExample(example);
        return new PageList(list);
    }

    /**
     * 根据主键获取Api
     *
     * @param apiId
     * @return
     */
    @Override
    public ResourceApi getApi(Long apiId) {
        return resourceApiMapper.selectByPrimaryKey(apiId);
    }


    /**
     * 检查Api编码是否存在
     *
     * @param apiCode
     * @return
     */
    @Override
    public boolean isExist(String apiCode) {
        return isExist(apiCode, null);
    }

    @Override
    public boolean isExist(String apiCode, String serviceId) {
        ExampleBuilder builder = new ExampleBuilder(ResourceApi.class);
        Example example = builder.criteria()
                .andEqualTo("apiCode", apiCode)
                .andEqualTo("serviceId", serviceId)
                .end().build();
        int count = resourceApiMapper.selectCountByExample(example);
        return count > 0 ? true : false;
    }

    /**
     * 添加Api动作
     *
     * @param api
     * @return
     */
    @Override
    public boolean addApi(ResourceApi api) {
        if (isExist(api.getApiCode(), api.getServiceId())) {
            throw new OpenMessageException(String.format("%sApi编码已存在,不允许重复添加", api.getApiCode()));
        }
        if (api.getPriority() == null) {
            api.setPriority(0);
        }
        if (api.getEnabled() == null) {
            api.setEnabled(true);
        }
        api.setCreateTime(new Date());
        api.setUpdateTime(api.getCreateTime());
        int count = resourceApiMapper.insertSelective(api);
        return count > 0;
    }

    /**
     * 修改Api动作
     *
     * @param api
     * @return
     */
    @Override
    public boolean updateApi(ResourceApi api) {
        ResourceApi savedApi = getApi(api.getApiId());
        if (savedApi == null) {
            throw new OpenMessageException(String.format("%sApi不存在", api.getApiId()));
        }
        if (!savedApi.getApiCode().equals(api.getApiCode())) {
            // 和原来不一致重新检查唯一性
            if (isExist(api.getApiCode(), api.getServiceId())) {
                throw new OpenMessageException(String.format("%sApi编码已存在,不允许重复添加", api.getApiCode()));
            }
        }
        if (api.getPriority() == null) {
            api.setPriority(0);
        }
        api.setUpdateTime(new Date());
        int count = resourceApiMapper.updateByPrimaryKeySelective(api);
        // 同步授权表里的资源
        permissionService.updatePermission(RbacConstans.RESOURCE_TYPE_API, api.getApiId());
        return count > 0;
    }

    /**
     * 查询api
     *
     * @param apiCode
     * @param serviceId
     * @return
     */
    @Override
    public ResourceApi getApi(String apiCode, String serviceId) {
        ExampleBuilder builder = new ExampleBuilder(ResourceApi.class);
        Example example = builder.criteria()
                .andEqualTo("apiCode", apiCode)
                .andEqualTo("serviceId", serviceId)
                .end().build();
        return resourceApiMapper.selectOneByExample(example);
    }

    /**
     * 更新启用禁用
     *
     * @param apiId
     * @param enable
     * @return
     */
    @Override
    public boolean updateEnable(Long apiId, Boolean enable) {
        ResourceApi api = new ResourceApi();
        api.setApiId(apiId);
        api.setEnabled(enable);
        api.setUpdateTime(new Date());
        int count = resourceApiMapper.updateByPrimaryKeySelective(api);
        return count > 0;
    }

    /**
     * 移除Api
     *
     * @param apiId
     * @return
     */
    @Override
    public boolean removeApi(Long apiId) {
        if (permissionService.isExist(apiId, RbacConstans.RESOURCE_TYPE_API)) {
            throw new OpenMessageException(String.format("%s已被授权使用,不允许删除!", apiId));
        }
        int count = resourceApiMapper.deleteByPrimaryKey(apiId);
        return count > 0;
    }


}
