package com.github.lyd.rbac.producer.mapper;


import com.github.lyd.common.mapper.CrudMapper;
import com.github.lyd.rbac.client.dto.AppInfoDto;
import com.github.lyd.rbac.client.entity.AppInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuyadu
 */
@Repository
public interface AppInfoMapper extends CrudMapper<AppInfo> {

    /**
     * 条件查询APP列表
     *
     * @param params
     * @return
     */
    List<AppInfoDto> selectAppInfo(Map params);

    /**
     * 获取APP详情
     *
     * @param appId
     * @return
     */
    AppInfoDto getAppInfo(@Param("appId") String appId);
}