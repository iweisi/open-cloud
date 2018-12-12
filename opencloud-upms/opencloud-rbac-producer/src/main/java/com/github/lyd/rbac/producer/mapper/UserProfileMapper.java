package com.github.lyd.rbac.producer.mapper;

import com.github.lyd.common.mapper.CrudMapper;
import com.github.lyd.rbac.client.dto.UserProfileDto;
import com.github.lyd.rbac.client.entity.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuyadu
 */
@Repository
public interface UserProfileMapper extends CrudMapper<UserProfile> {

    /**
     * 条件查询用户资料
     *
     * @param params
     * @return
     */
    List<UserProfileDto> selectUserProfileDto(Map params);
}