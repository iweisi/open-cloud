package com.github.lyd.rbac.producer.service;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.rbac.client.dto.TenantProfileDto;
import com.github.lyd.rbac.client.entity.TenantProfile;

/**
 * 租户资料管理
 *
 * @author: liuyadu
 * @date: 2018/10/24 16:38
 * @description:
 */
public interface TenantProfileService {

    /**
     * 更新租户
     *
     * @param profileDto
     * @return
     */
    Boolean addProfile(TenantProfile profileDto);

    /**
     * 更新租户
     *
     * @param profileDto
     * @return
     */
    Boolean updateProfile(TenantProfile profileDto);

    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    PageList<TenantProfileDto> findListPage(PageParams pageParams, String keyword);

    /**
     * 依据登录名查询租户信息
     *
     * @param username
     * @return
     */
    TenantProfile getProfile(String username);

    /**
     * 依据租户Id查询租户信息
     *
     * @param tenantId
     * @return
     */
    TenantProfile getProfile(Long tenantId);
}
