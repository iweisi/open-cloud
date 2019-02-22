package com.github.lyd.base.provider.service.impl;

import com.github.lyd.base.client.dto.SystemUserDto;
import com.github.lyd.base.client.entity.SystemAction;
import com.github.lyd.base.client.entity.SystemUser;
import com.github.lyd.base.provider.mapper.SystemUserMapper;
import com.github.lyd.base.provider.service.SystemRoleService;
import com.github.lyd.base.provider.service.SystemUserService;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author: liuyadu
 * @date: 2018/10/24 16:33
 * @description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private SystemRoleService systemRoleService;

    /**
     * 更新系统用户
     *
     * @param userProfile
     * @return
     */
    @Override
    public Long addProfile(SystemUserDto userProfile) {
        systemUserMapper.insertSelective(userProfile);
        if (userProfile != null && userProfile.getRoleIds() != null && userProfile.getRoleIds().size() > 0) {
            systemRoleService.saveMemberRoles(userProfile.getUserId(), userProfile.getRoleIds().toArray(new Long[userProfile.getRoleIds().size()]));
        }
        return userProfile.getUserId();
    }

    /**
     * 更新系统用户
     *
     * @param userProfile
     * @return
     */
    @Override
    public Boolean updateProfile(SystemUserDto userProfile) {
        if (userProfile == null || userProfile.getUserId() == null) {
            return false;
        }
        if (userProfile.getUserId() == null) {
            return false;
        }
        systemRoleService.saveMemberRoles(userProfile.getUserId(), userProfile.getRoleIds().toArray(new Long[userProfile.getRoleIds().size()]));
        return systemUserMapper.updateByPrimaryKeySelective(userProfile) > 0;
    }

    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    @Override
    public PageList<SystemUser> findListPage(PageParams pageParams, String keyword) {
        ExampleBuilder builder = new ExampleBuilder(SystemAction.class);
        Example example = builder.build();
        List<SystemUser> list = systemUserMapper.selectByExample(example);
        return new PageList(list);
    }

    /**
     * 查询列表
     *
     * @param keyword
     * @return
     */
    @Override
    public PageList<SystemUser> findList(String keyword) {
        ExampleBuilder builder = new ExampleBuilder(SystemUser.class);
        Example example = builder.criteria()
                .orLike("userName", keyword).end().build();
        example.orderBy("userId").asc();
        List<SystemUser> list = systemUserMapper.selectByExample(example);
        return new PageList(list);
    }

    /**
     * 依据登录名查询系统用户信息
     *
     * @param username
     * @return
     */
    @Override
    public SystemUser getProfile(String username) {
        ExampleBuilder builder = new ExampleBuilder(SystemUser.class);
        Example example = builder.criteria()
                .andEqualTo("userName", username)
                .end().build();
        SystemUser saved = systemUserMapper.selectOneByExample(example);
        return saved;
    }

    /**
     * 依据系统用户Id查询系统用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public SystemUser getProfile(Long userId) {
        return systemUserMapper.selectByPrimaryKey(userId);
    }


}
