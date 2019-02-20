package com.github.lyd.base.client.api;

import com.github.lyd.base.client.entity.SystemGrantAccess;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.ResultBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author liuyadu
 */
public interface SystemGrantAccessRemoteService {


    /**
     * 获取已授权访问列表
     *
     * @return
     */
    @PostMapping("/grant/access")
    ResultBody<PageList<SystemGrantAccess>> grantAccess(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    );

    /**
     * 获取已授权访问列表
     *
     * @return
     */
    @GetMapping("/grant/access/list")
    ResultBody<List<SystemGrantAccess>> grantAccessList();


    /**
     * 角色菜单授权
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID.多个以,隔开
     * @return
     */
    @PostMapping("/grant/role/menu")
    ResultBody grantRoleMenu(
            @RequestParam(value = "roleId") Long roleId,
            @RequestParam(value = "menuIds",required = false) String menuIds
    );


    /**
     * 角色操作授权
     *
     * @param roleId    角色ID
     * @param actionIds 操作ID.多个以,隔开
     * @return
     */
    @PostMapping("/grant/role/action")
    ResultBody grantRoleAction(
            @RequestParam(value = "roleId") Long roleId,
            @RequestParam(value = "actionIds",required = false) String actionIds
    );

    /**
     * 角色接口授权
     *
     * @param roleId 角色ID
     * @param apiIds 接口ID.多个以,隔开
     * @return
     */
    @PostMapping("/grant/role/api")
    ResultBody grantRoleApi(
            @RequestParam(value = "roleId") Long roleId,
            @RequestParam(value = "apiIds",required = false) String apiIds
    );


    /**
     * 获取角色已授权菜单资源
     *
     * @param roleId 角色ID
     * @return
     */
    @PostMapping("/granted/role/menu")
    ResultBody<PageList<SystemGrantAccess>> grantedRoleMenu(
            @RequestParam(value = "roleId") Long roleId
    );

    /**
     * 获取角色已授权操作资源
     *
     * @param roleId 角色ID
     * @return
     */
    @PostMapping("/granted/role/action")
    ResultBody<PageList<SystemGrantAccess>> grantedRoleAction(
            @RequestParam(value = "roleId") Long roleId
    );

    /**
     * 获取角色已授权接口资源
     *
     * @param roleId 角色ID
     * @return
     */
    @PostMapping("/granted/role/api")
    ResultBody<PageList<SystemGrantAccess>> grantedRoleApi(
            @RequestParam(value = "roleId") Long roleId
    );

    /**
     * 用户菜单授权
     *
     * @param userId  用户ID
     * @param menuIds 菜单ID.多个以,隔开
     * @return
     */
    @PostMapping("/grant/user/menu")
    ResultBody grantUserMenu(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "menuIds",required = false) String menuIds
    );


    /**
     * 用户操作授权
     *
     * @param userId    用户ID
     * @param actionIds 操作ID.多个以,隔开
     * @return
     */
    @PostMapping("/grant/user/action")
    ResultBody grantUserAction(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "actionIds",required = false) String actionIds
    );

    /**
     * 用户接口授权
     *
     * @param userId 用户ID
     * @param apiIds 接口ID.多个以,隔开
     * @return
     */
    @PostMapping("/grant/user/api")
    ResultBody grantUserApi(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "apiIds",required = false) String apiIds
    );


    /**
     * 获取用户已授权菜单资源
     *
     * @param userId 用户ID
     * @return
     */
    @PostMapping("/granted/user/menu")
    ResultBody<PageList<SystemGrantAccess>> grantedUserMenu(
            @RequestParam(value = "userId") Long userId
    );

    /**
     * 获取用户已授权操作资源
     *
     * @param userId 用户ID
     * @return
     */
    @PostMapping("/granted/user/action")
    ResultBody<PageList<SystemGrantAccess>> grantedUserAction(
            @RequestParam(value = "userId") Long userId
    );

    /**
     * 获取用户已授权接口资源
     *
     * @param userId 用户ID
     * @return
     */
    @PostMapping("/granted/user/api")
    ResultBody<PageList<SystemGrantAccess>> grantedUserApi(
            @RequestParam(value = "userId") Long userId
    );
}
