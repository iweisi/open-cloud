package com.github.lyd.base.producer.mapper;

import com.github.lyd.base.client.dto.SystemUserDto;
import com.github.lyd.base.client.entity.SystemUser;
import com.github.lyd.common.mapper.CrudMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuyadu
 */
@Repository
@CacheNamespace
public interface SystemUserMapper extends CrudMapper<SystemUser> {

    /**
     * 条件查询系统用户资料
     *
     * @param params
     * @return
     */
    List<SystemUserDto> selectUserList(Map params);
}
