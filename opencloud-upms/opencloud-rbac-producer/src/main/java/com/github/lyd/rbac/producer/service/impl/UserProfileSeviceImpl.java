package com.github.lyd.rbac.producer.service.impl;

import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.utils.StringUtils;
import com.github.lyd.rbac.client.constans.RbacConstans;
import com.github.lyd.rbac.client.dto.UserProfileDto;
import com.github.lyd.rbac.client.entity.UserProfile;
import com.github.lyd.rbac.producer.mapper.UserProfileMapper;
import com.github.lyd.rbac.producer.service.UserAccountService;
import com.github.lyd.rbac.producer.service.UserProfileService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author: liuyadu
 * @date: 2018/10/24 16:33
 * @description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserProfileSeviceImpl implements UserProfileService {

    @Autowired
    private UserProfileMapper userProfileMapper;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 添加用户
     *
     * @param userProfile
     * @return
     */
    @Override
    public boolean addUser(UserProfileDto userProfile) {
        if (userProfile == null) {
            return false;
        }
        if (StringUtils.isBlank(userProfile.getUserName())) {
            throw new OpenMessageException("账号不能为空!");
        }
        if (StringUtils.isBlank(userProfile.getPassword())) {
            throw new OpenMessageException("密码不能为空!");
        }
        UserProfile saved = getUserProfile(userProfile.getUserName());
        if (saved != null) {
            // 已注册
            throw new OpenMessageException("用户名已经被注册!");
        }
        //未注册
        saved = new UserProfile();
        BeanUtils.copyProperties(userProfile, saved);
        //加密
        String encodePassword = passwordEncoder.encode(userProfile.getPassword());
        if (StringUtils.isBlank(saved.getNickName())) {
            saved.setNickName(saved.getUserName());
        }
        saved.setState(RbacConstans.USER_STATE_NORMAL);
        saved.setCreateTime(new Date());
        saved.setUpdateTime(saved.getCreateTime());
        saved.setRegisterTime(saved.getCreateTime());
        //保存用户信息
        userProfileMapper.insertSelective(saved);
        //绑定用户名登陆
        Boolean suceess = userAccountService.bindUsernameOnAccount(saved.getUserId(), saved.getUserName(), encodePassword);
        if (StringUtils.isNotBlank(saved.getEmail())) {
            //绑定email账号登陆
            suceess = userAccountService.bindEmailOnAccount(saved.getUserId(), saved.getEmail(), encodePassword);
        }
        if (StringUtils.isNotBlank(saved.getMobile())) {
            //绑定手机号账号登陆
            suceess = userAccountService.bindMobileOnAccount(saved.getUserId(), saved.getMobile(), encodePassword);
        }
        return suceess;
    }

    /**
     * 更新用户
     *
     * @param userProfile
     * @return
     */
    @Override
    public boolean updateUser(UserProfileDto userProfile) {
        if(userProfile==null || userProfile.getUserId()==null)
        {
            return false;
        }
        UserProfile saved = getUserProfile(userProfile.getUserId());
        if(saved==null){
            throw new OpenMessageException("用户不存在");
        }
        return false;
    }

    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    @Override
    public PageList<UserProfileDto> findListPage(PageParams pageParams,String keyword) {
        PageHelper.startPage(pageParams.getPage(), pageParams.getLimit(), pageParams.getOrderBy());
        List<UserProfileDto> list = userProfileMapper.selectUserProfileDto(null);
        return new PageList(list);
    }

    /**
     * 依据登录名查询用户信息
     *
     * @param username
     * @return
     */
    @Override
    public UserProfile getUserProfile(String username) {
        ExampleBuilder builder = new ExampleBuilder(UserProfile.class);
        Example example = builder.criteria()
                .andEqualTo("userName", username)
                .end().build();
        UserProfile saved = userProfileMapper.selectOneByExample(example);
        return saved;
    }

    /**
     * 依据用户Id查询用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserProfile getUserProfile(Long userId) {
        return userProfileMapper.selectByPrimaryKey(userId);
    }


}
