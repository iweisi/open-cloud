package com.github.lyd.base.producer.mapper;

import com.github.lyd.common.mapper.CrudMapper;
import com.github.lyd.base.client.entity.SystemRole;
import com.github.lyd.base.client.entity.SystemUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuyadu
 */
@Repository
public interface SystemUserRoleMapper extends CrudMapper<SystemUserRole> {
    /**
     * 查询系统用户角色
     *
     * @param userId
     * @return
     */
    List<SystemRole> selectUserRoleList(@Param("userId") Long userId);
}