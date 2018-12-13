package com.github.lyd.rbac.producer.mapper;

import com.github.lyd.common.mapper.CrudMapper;
import com.github.lyd.rbac.client.entity.Roles;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuyadu
 */
@Repository
public interface RolesMapper extends CrudMapper<Roles> {

    List<Roles> selectByCondition(Map params);
}