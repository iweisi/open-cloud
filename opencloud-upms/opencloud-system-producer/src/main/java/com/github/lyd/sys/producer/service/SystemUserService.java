package com.github.lyd.sys.producer.service;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.sys.client.dto.SystemUserDto;
import com.github.lyd.sys.client.entity.SystemUser;

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
    Boolean addProfile(SystemUser profileDto);

    /**
     * 更新系统用户
     *
     * @param profileDto
     * @return
     */
    Boolean updateProfile(SystemUser profileDto);

    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    PageList<SystemUserDto> findListPage(PageParams pageParams, String keyword);

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
