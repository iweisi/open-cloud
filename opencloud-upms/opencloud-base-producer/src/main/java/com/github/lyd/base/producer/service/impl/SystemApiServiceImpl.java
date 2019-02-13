package com.github.lyd.base.producer.service.impl;

import com.github.lyd.base.client.constants.BaseConstants;
import com.github.lyd.base.client.entity.SystemApi;
import com.github.lyd.base.producer.mapper.SystemApiMapper;
import com.github.lyd.base.producer.service.SystemApiService;
import com.github.lyd.base.producer.service.SystemGrantAccessService;
import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
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
public class SystemApiServiceImpl implements SystemApiService {
    @Autowired
    private SystemApiMapper systemApiMapper;
    @Autowired
    private SystemGrantAccessService systemAccessService;

    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    @Override
    public PageList<SystemApi> findListPage(PageParams pageParams, String keyword) {
        PageHelper.startPage(pageParams.getPage(), pageParams.getLimit(), pageParams.getOrderBy());
        ExampleBuilder builder = new ExampleBuilder(SystemApi.class);
        Example example = builder.criteria()
                .orLike("apiCode", keyword)
                .orLike("apiName", keyword).end().build();
        List<SystemApi> list = systemApiMapper.selectByExample(example);
        return new PageList(list);
    }

    /**
     * 查询列表
     *
     * @param keyword
     * @return
     */
    @Override
    public PageList<SystemApi> findList(String keyword) {
        ExampleBuilder builder = new ExampleBuilder(SystemApi.class);
        Example example = builder.criteria()
                .orLike("apiCode", keyword)
                .orLike("apiName", keyword).end().build();
        example.orderBy("apiId").asc().orderBy("priority").asc();
        List<SystemApi> list = systemApiMapper.selectByExample(example);
        return new PageList(list);
    }

    /**
     * 根据主键获取接口
     *
     * @param apiId
     * @return
     */
    @Override
    public SystemApi getApi(Long apiId) {
        return systemApiMapper.selectByPrimaryKey(apiId);
    }


    @Override
    public Boolean isExist(String apiCode) {
        ExampleBuilder builder = new ExampleBuilder(SystemApi.class);
        Example example = builder.criteria()
                .andEqualTo("apiCode", apiCode)
                .end().build();
        int count = systemApiMapper.selectCountByExample(example);
        return count > 0 ? true : false;
    }

    /**
     * 添加接口
     *
     * @param api
     * @return
     */
    @Override
    public Long addApi(SystemApi api) {
        if (isExist(api.getApiCode())) {
            throw new OpenMessageException(String.format("%sApi编码已存在,不允许重复添加", api.getApiCode()));
        }
        if (api.getPriority() == null) {
            api.setPriority(0);
        }
        if (api.getStatus() == null) {
            api.setStatus(BaseConstants.ENABLED);
        }
        if (api.getApiCategory() == null) {
            api.setApiCategory(BaseConstants.DEFAULT_API_CATEGORY);
        }
        if (api.getIsPersist() == null) {
            api.setIsPersist(BaseConstants.DISABLED);
        }
        api.setCreateTime(new Date());
        api.setUpdateTime(api.getCreateTime());
        systemApiMapper.insertSelective(api);
        return api.getApiId();
    }

    /**
     * 修改接口
     *
     * @param api
     * @return
     */
    @Override
    public void updateApi(SystemApi api) {
        if (api.getApiId() == null) {
            throw new OpenMessageException("ID不能为空");
        }
        SystemApi savedApi = getApi(api.getApiId());
        if (savedApi == null) {
            throw new OpenMessageException(String.format("%sApi不存在", api.getApiId()));
        }
        if (!savedApi.getApiCode().equals(api.getApiCode())) {
            // 和原来不一致重新检查唯一性
            if (isExist(api.getApiCode())) {
                throw new OpenMessageException(String.format("%sApi编码已存在,不允许重复添加", api.getApiCode()));
            }
        }
        if (api.getPriority() == null) {
            api.setPriority(0);
        }
        if (api.getApiCategory() == null) {
            api.setApiCategory(BaseConstants.DEFAULT_API_CATEGORY);
        }
        api.setUpdateTime(new Date());
        systemApiMapper.updateByPrimaryKeySelective(api);
        // 同步授权表里的信息
        systemAccessService.updateGrantAccess(BaseConstants.RESOURCE_TYPE_API, api.getApiId());
    }

    /**
     * 查询接口
     *
     * @param apiCode
     * @param serviceId
     * @return
     */
    @Override
    public SystemApi getApi(String apiCode, String serviceId) {
        ExampleBuilder builder = new ExampleBuilder(SystemApi.class);
        Example example = builder.criteria()
                .andEqualTo("apiCode", apiCode)
                .andEqualTo("serviceId", serviceId)
                .end().build();
        return systemApiMapper.selectOneByExample(example);
    }

    /**
     * 更新启用禁用
     *
     * @param apiId
     * @param status
     * @return
     */
    @Override
    public void updateStatus(Long apiId, Integer status) {
        SystemApi api = new SystemApi();
        api.setApiId(apiId);
        api.setStatus(status);
        api.setUpdateTime(new Date());
        systemApiMapper.updateByPrimaryKeySelective(api);
        // 同步授权表里的信息
        systemAccessService.updateGrantAccess(BaseConstants.RESOURCE_TYPE_API, api.getApiId());
    }

    /**
     * 移除接口
     *
     * @param apiId
     * @return
     */
    @Override
    public void removeApi(Long apiId) {
        SystemApi api = getApi(apiId);
        if (api != null && api.getIsPersist().equals(BaseConstants.ENABLED)) {
            throw new OpenMessageException(String.format("保留数据,不允许删除"));
        }
        if (systemAccessService.isExist(apiId, BaseConstants.RESOURCE_TYPE_API)) {
            throw new OpenMessageException(String.format("资源已被授权,不允许删除,请取消授权后,再次尝试!"));
        }
        systemApiMapper.deleteByPrimaryKey(apiId);
    }

    /**
     * 根据编码查询ID
     *
     * @param codes
     * @return
     */
    @Override
    public List<String> findIdsByCodes(String... codes) {
        List codeList = Lists.newArrayList(codes);
        List<String> ids = Lists.newArrayList();
        if (codeList.contains("all")) {
            ids.add("1");
            return ids;
        }
        ExampleBuilder builder = new ExampleBuilder(SystemApi.class);
        Example example = builder.criteria()
                .andIn("apiCode", codeList)
                .end().build();
        List<SystemApi> list = systemApiMapper.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            list.forEach(item -> {
                ids.add(String.valueOf(item.getApiId()));
            });
        }
        return ids;
    }


}
