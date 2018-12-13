package com.github.lyd.rbac.producer.mapper;

import com.github.lyd.common.mapper.CrudMapper;
import com.github.lyd.rbac.client.entity.Roles;
import com.github.lyd.rbac.client.entity.RoleMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuyadu
 */
@Repository
public interface RolesMemberMapper extends CrudMapper<RoleMember> {
    /**
     * 查询租户角色
     *
     * @param tenantId
     * @return
     */
    List<Roles> findTenantRoles(@Param("tenantId") Long tenantId);
}