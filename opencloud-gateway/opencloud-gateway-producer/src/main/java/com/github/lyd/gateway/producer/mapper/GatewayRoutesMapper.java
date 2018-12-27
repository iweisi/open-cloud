package com.github.lyd.gateway.producer.mapper;

import com.github.lyd.common.mapper.CrudMapper;
import com.github.lyd.gateway.client.entity.GatewayRoute;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

/**
 * @author liuyadu
 */
@Repository
@CacheNamespace
public interface GatewayRoutesMapper extends CrudMapper<GatewayRoute> {
}
