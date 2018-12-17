package com.github.lyd.rbac.producer.service.impl;

import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.rbac.client.dto.TenantProfileDto;
import com.github.lyd.rbac.client.entity.TenantProfile;
import com.github.lyd.rbac.producer.mapper.TenantProfileMapper;
import com.github.lyd.rbac.producer.service.TenantProfileService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author: liuyadu
 * @date: 2018/10/24 16:33
 * @description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TenantProfileServiceImpl implements TenantProfileService {

    @Autowired
    private TenantProfileMapper tenantProfileMapper;

    /**
     * 更新租户
     *
     * @param tenantProfile
     * @return
     */
    @Override
    public Boolean addProfile(TenantProfile tenantProfile) {
        if (tenantProfile == null || tenantProfile.getTenantId() == null) {
            return false;
        }
        return tenantProfileMapper.insertSelective(tenantProfile) > 0;
    }

    /**
     * 更新租户
     *
     * @param tenantProfile
     * @return
     */
    @Override
    public Boolean updateProfile(TenantProfile tenantProfile) {
        if (tenantProfile == null || tenantProfile.getTenantId() == null) {
            return false;
        }
        if (tenantProfile.getTenantId() == null) {
            return false;
        }
        return tenantProfileMapper.updateByPrimaryKeySelective(tenantProfile) > 0;
    }

    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    @Override
    public PageList<TenantProfileDto> findListPage(PageParams pageParams, String keyword) {
        PageHelper.startPage(pageParams.getPage(), pageParams.getLimit(), pageParams.getOrderBy());
        List<TenantProfileDto> list = tenantProfileMapper.selectTenantProfileDto(null);
        return new PageList(list);
    }

    /**
     * 依据登录名查询租户信息
     *
     * @param username
     * @return
     */
    @Override
    public TenantProfile getProfile(String username) {
        ExampleBuilder builder = new ExampleBuilder(TenantProfile.class);
        Example example = builder.criteria()
                .andEqualTo("userName", username)
                .end().build();
        TenantProfile saved = tenantProfileMapper.selectOneByExample(example);
        return saved;
    }

    /**
     * 依据租户Id查询租户信息
     *
     * @param tenantId
     * @return
     */
    @Override
    public TenantProfile getProfile(Long tenantId) {
        return tenantProfileMapper.selectByPrimaryKey(tenantId);
    }


}
