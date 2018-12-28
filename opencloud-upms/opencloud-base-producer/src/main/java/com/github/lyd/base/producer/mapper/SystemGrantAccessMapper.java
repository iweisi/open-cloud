package com.github.lyd.base.producer.mapper;

import com.github.lyd.base.client.entity.SystemGrantAccess;
import com.github.lyd.common.mapper.CrudMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

/**
 * @author liuyadu
 */
@Repository
public interface SystemGrantAccessMapper extends CrudMapper<SystemGrantAccess> {
}
