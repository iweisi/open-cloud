package com.github.lyd.rbac.producer.service.impl;

import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.utils.StringUtils;
import com.github.lyd.rbac.client.constans.RbacConstans;
import com.github.lyd.rbac.client.dto.TenantProfileDto;
import com.github.lyd.rbac.client.entity.TenantProfile;
import com.github.lyd.rbac.producer.mapper.TenantProfileMapper;
import com.github.lyd.rbac.producer.service.TenantAccountService;
import com.github.lyd.rbac.producer.service.TenantProfileService;
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
public class TenantProfileServiceImpl implements TenantProfileService {

    @Autowired
    private TenantProfileMapper tenantProfileMapper;
    @Autowired
    private TenantAccountService tenantAccountService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 添加租户
     *
     * @param profileDto
     * @return
     */
    @Override
    public Boolean add(TenantProfileDto profileDto) {
        if (profileDto == null) {
            return false;
        }
        if (StringUtils.isBlank(profileDto.getUserName())) {
            throw new OpenMessageException("账号不能为空!");
        }
        if (StringUtils.isBlank(profileDto.getPassword())) {
            throw new OpenMessageException("密码不能为空!");
        }
        TenantProfile saved = getProfile(profileDto.getUserName());
        if (saved != null) {
            // 已注册
            throw new OpenMessageException("登录名已经被注册!");
        }
        //未注册
        saved = new TenantProfile();
        BeanUtils.copyProperties(profileDto, saved);
        //加密
        String encodePassword = passwordEncoder.encode(profileDto.getPassword());
        if (StringUtils.isBlank(saved.getNickName())) {
            saved.setNickName(saved.getUserName());
        }
        saved.setState(RbacConstans.USER_STATE_NORMAL);
        saved.setCreateTime(new Date());
        saved.setUpdateTime(saved.getCreateTime());
        saved.setRegisterTime(saved.getCreateTime());
        //保存租户信息
        tenantProfileMapper.insertSelective(saved);
        //绑定租户名登陆
        Boolean suceess = tenantAccountService.bindUsernameAccount(saved.getTenantId(), saved.getUserName(), encodePassword);
        if (StringUtils.isNotBlank(saved.getEmail())) {
            //绑定email账号登陆
            suceess = tenantAccountService.bindEmailAccount(saved.getTenantId(), saved.getEmail(), encodePassword);
        }
        if (StringUtils.isNotBlank(saved.getMobile())) {
            //绑定手机号账号登陆
            suceess = tenantAccountService.bindMobileAccount(saved.getTenantId(), saved.getMobile(), encodePassword);
        }
        return suceess;
    }

    /**
     * 更新租户
     *
     * @param profileDto
     * @return
     */
    @Override
    public Boolean update(TenantProfileDto profileDto) {
        if (profileDto == null || profileDto.getTenantId() == null) {
            return false;
        }
        TenantProfile saved = getProfile(profileDto.getTenantId());
        if (saved == null) {
            throw new OpenMessageException("信息不存在");
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
    public PageList<TenantProfileDto> findListPage(PageParams pageParams, String keyword) {
        PageHelper.startPage(pageParams.getPage(), pageParams.getLimit(), pageParams.getOrderBy());
        List<TenantProfileDto> list = tenantProfileMapper.selectTenantProfileDto(null);
        return new PageList(list);
    }

    /**
     * 依据登录名查询租户信息
     *
     * @param username
     * @return
     */
    @Override
    public TenantProfile getProfile(String username) {
        ExampleBuilder builder = new ExampleBuilder(TenantProfile.class);
        Example example = builder.criteria()
                .andEqualTo("userName", username)
                .end().build();
        TenantProfile saved = tenantProfileMapper.selectOneByExample(example);
        return saved;
    }

    /**
     * 依据租户Id查询租户信息
     *
     * @param tenantId
     * @return
     */
    @Override
    public TenantProfile getProfile(Long tenantId) {
        return tenantProfileMapper.selectByPrimaryKey(tenantId);
    }


}
