package com.github.lyd.base.provider.controller;

import com.github.lyd.base.client.api.SystemUserRemoteService;
import com.github.lyd.base.client.dto.SystemUserDto;
import com.github.lyd.base.client.entity.SystemRole;
import com.github.lyd.base.client.entity.SystemUser;
import com.github.lyd.base.provider.service.SystemAccountService;
import com.github.lyd.base.provider.service.SystemRoleService;
import com.github.lyd.base.provider.service.SystemUserService;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统用户信息
 *
 * @author liuyadu
 */
@Api(tags = "系统用户管理")
@RestController
public class SystemUserController implements SystemUserRemoteService {
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private SystemAccountService systemAccountService;
    @Autowired
    private SystemRoleService systemRoleService;

    /**
     * 系统用户分页列表
     *
     * @return
     */
    @ApiOperation(value = "系统用户分页列表", notes = "系统用户分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "显示条数:最大999", paramType = "form"),
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/user")
    @Override
    public ResultBody<PageList<SystemUser>> user(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        return ResultBody.success(systemUserService.findListPage(new PageParams(page, limit), keyword));
    }

    /**
     * 获取用户列表
     *
     * @param keyword
     * @return
     */
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/user/list")
    @Override
    public ResultBody<PageList<SystemRole>> userList(String keyword) {
        return ResultBody.success(systemUserService.findList(keyword));
    }

    /**
     * 添加系统用户
     *
     * @param userName
     * @param password
     * @param nickName
     * @param status
     * @param userType
     * @param email
     * @param mobile
     * @param userDesc
     * @param avatar
     * @return
     */
    @ApiOperation(value = "添加系统用户", notes = "添加系统用户")
    @Override
    @PostMapping("/user/add")
    public ResultBody<Long> addUser(
            @RequestParam(value = "userName") String userName,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "nickName") String nickName,
            @RequestParam(value = "status") Integer status,
            @RequestParam(value = "userType") String userType,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "userDesc", required = false) String userDesc,
            @RequestParam(value = "avatar", required = false) String avatar,
            @RequestParam(value = "roleIds", required = false) String roleIds
    ) {
        SystemUserDto user = new SystemUserDto();
        user.setUserName(userName);
        user.setPassword(password);
        user.setNickName(nickName);
        user.setStatus(status);
        user.setUserType(userType);
        user.setEmail(email);
        user.setMobile(mobile);
        user.setUserDesc(userDesc);
        user.setAvatar(avatar);
        if(StringUtils.isNotBlank(roleIds)){
            List<Long> ids = Arrays.asList(roleIds.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            user.setRoleIds(ids);
        }
        Long userId = systemAccountService.register(user);
        return ResultBody.success(userId);
    }

    /**
     * 更新系统用户
     *
     * @param userId
     * @param userName
     * @param nickName
     * @param status
     * @param userType
     * @param email
     * @param mobile
     * @param userDesc
     * @param avatar
     * @return
     */
    @ApiOperation(value = "更新系统用户", notes = "更新系统用户")
    @PostMapping("/user/update")
    @Override
    public ResultBody updateUser(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "userName") String userName,
            @RequestParam(value = "nickName") String nickName,
            @RequestParam(value = "status") Integer status,
            @RequestParam(value = "userType") String userType,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "userDesc", required = false) String userDesc,
            @RequestParam(value = "avatar", required = false) String avatar,
            @RequestParam(value = "roleIds", required = false) String roleIds
    ) {
        SystemUserDto user = new SystemUserDto();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setNickName(nickName);
        user.setStatus(status);
        user.setUserType(userType);
        user.setEmail(email);
        user.setMobile(mobile);
        user.setUserDesc(userDesc);
        user.setAvatar(avatar);
        if(StringUtils.isNotBlank(roleIds)){
            List<Long> ids = Arrays.asList(roleIds.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            user.setRoleIds(ids);
        }
        systemUserService.updateProfile(user);
        return ResultBody.success();
    }


    /**
     * 获取用户角色
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取用户角色", notes = "获取用户角色")
    @PostMapping("/user/roles")
    @Override
    public ResultBody<PageList<SystemRole>> getUserRoles(
            @RequestParam(value = "userId") Long userId
    ) {
        return ResultBody.success(new PageList<>(systemRoleService.getUserRoles(userId)));
    }
}
