package com.github.lyd.base.producer.mapper;


import com.github.lyd.base.client.dto.SystemAppDto;
import com.github.lyd.base.client.entity.SystemApp;
import com.github.lyd.common.mapper.CrudMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuyadu
 */
@Repository
@CacheNamespace
public interface SystemAppMapper extends CrudMapper<SystemApp> {

    /**
     * 条件查询APP列表
     *
     * @param params
     * @return
     */
    List<SystemAppDto> selectAppList(Map params);

    /**
     * 获取APP详情
     *
     * @param appId
     * @return
     */
    SystemAppDto getApp(@Param("appId") String appId);
}
