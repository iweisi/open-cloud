package com.github.lyd.base.producer.mapper;

import com.github.lyd.base.client.entity.SystemRole;
import com.github.lyd.common.mapper.CrudMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuyadu
 */
@Repository
public interface SystemRoleMapper extends CrudMapper<SystemRole> {

    List<SystemRole> selectRoleList(Map params);
}
