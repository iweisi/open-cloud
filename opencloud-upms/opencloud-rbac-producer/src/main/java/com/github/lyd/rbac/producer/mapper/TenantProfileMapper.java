package com.github.lyd.rbac.producer.mapper;

import com.github.lyd.common.mapper.CrudMapper;
import com.github.lyd.rbac.client.dto.TenantProfileDto;
import com.github.lyd.rbac.client.entity.TenantProfile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuyadu
 */
@Repository
public interface TenantProfileMapper extends CrudMapper<TenantProfile> {

    /**
     * 条件查询租户资料
     *
     * @param params
     * @return
     */
    List<TenantProfileDto> selectTenantProfileDto(Map params);
}