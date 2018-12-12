package com.github.lyd.rbac.producer.mapper;

import com.github.lyd.common.mapper.CrudMapper;
import com.github.lyd.rbac.client.entity.Role;
import com.github.lyd.rbac.client.entity.RoleUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuyadu
 */
@Repository
public interface RoleUserMapper extends CrudMapper<RoleUser> {
    /**
     * 查询用户角色
     *
     * @param userId
     * @return
     */
    List<Role> findUserRoles(@Param("userId") Long userId);
}