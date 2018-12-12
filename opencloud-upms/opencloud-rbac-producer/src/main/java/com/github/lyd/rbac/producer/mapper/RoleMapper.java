package com.github.lyd.rbac.producer.mapper;

import com.github.lyd.common.mapper.CrudMapper;
import com.github.lyd.rbac.client.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuyadu
 */
@Repository
public interface RoleMapper extends CrudMapper<Role> {

    List<Role> selectByCondition(Map params);
}