package com.github.lyd.rbac.producer.mapper;

import com.github.lyd.common.mapper.CrudMapper;
import com.github.lyd.rbac.client.entity.ResourcePermission;
import org.springframework.stereotype.Repository;

/**
 * @author liuyadu
 */
@Repository
public interface PermissionMapper extends CrudMapper<ResourcePermission> {
}