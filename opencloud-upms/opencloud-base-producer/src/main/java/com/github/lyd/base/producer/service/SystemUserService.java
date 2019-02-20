package com.github.lyd.base.producer.service;

import com.github.lyd.base.client.dto.SystemUserDto;
import com.github.lyd.base.client.entity.SystemUser;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;

/**
 * 系统用户资料管理
 *
 * @author: liuyadu
 * @date: 2018/10/24 16:38
 * @description:
 */
public interface SystemUserService {

    /**
     * 更新系统用户
     *
     * @param profileDto
     * @return
     */
    Long addProfile(SystemUserDto profileDto);

    /**
     * 更新系统用户
     *
     * @param profileDto
     * @return
     */
    Boolean updateProfile(SystemUserDto profileDto);

    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    PageList<SystemUser> findListPage(PageParams pageParams, String keyword);

    /**
     * 查询列表
     *
     * @param keyword
     * @return
     */
    PageList<SystemUser> findList(String keyword);

    /**
     * 依据登录名查询系统用户信息
     *
     * @param username
     * @return
     */
    SystemUser getProfile(String username);

    /**
     * 依据系统用户Id查询系统用户信息
     *
     * @param userId
     * @return
     */
    SystemUser getProfile(Long userId);
}
