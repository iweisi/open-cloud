package com.github.lyd.rbac.producer.service;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.rbac.client.dto.UserProfileDto;
import com.github.lyd.rbac.client.entity.UserProfile;

/**
 * 用户资料管理
 *
 * @author: liuyadu
 * @date: 2018/10/24 16:38
 * @description:
 */
public interface UserProfileService {
    /**
     * 添加用户
     *
     * @param userProfile
     * @return
     */
    boolean addUser(UserProfileDto userProfile);

    /**
     * 更新用户
     *
     * @param userProfile
     * @return
     */
    boolean updateUser(UserProfileDto userProfile);

    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    PageList<UserProfileDto> findListPage(PageParams pageParams,String keyword);

    /**
     * 依据登录名查询用户信息
     *
     * @param username
     * @return
     */
    UserProfile getUserProfile(String username);

    /**
     * 依据用户Id查询用户信息
     *
     * @param userId
     * @return
     */
    UserProfile getUserProfile(Long userId);
}
