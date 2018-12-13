package com.github.lyd.rbac.producer.mapper;


import com.github.lyd.common.mapper.CrudMapper;
import com.github.lyd.rbac.client.dto.AppDetailsDto;
import com.github.lyd.rbac.client.entity.AppDetails;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuyadu
 */
@Repository
public interface AppDetailsMapper extends CrudMapper<AppDetails> {

    /**
     * 条件查询APP列表
     *
     * @param params
     * @return
     */
    List<AppDetailsDto> selectAppInfo(Map params);

    /**
     * 获取APP详情
     *
     * @param appId
     * @return
     */
    AppDetailsDto getAppInfo(@Param("appId") String appId);
}