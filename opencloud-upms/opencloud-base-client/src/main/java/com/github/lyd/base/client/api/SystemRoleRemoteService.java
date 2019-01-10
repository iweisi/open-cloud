package com.github.lyd.base.client.api;

import com.github.lyd.base.client.entity.SystemGrantAccess;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.base.client.entity.SystemRole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: liuyadu
 * @date: 2018/11/19 16:27
 * @description:
 */
public interface SystemRoleRemoteService {

    /**
     * 系统用户列表
     *
     * @return
     */
    @PostMapping("/role")
    ResultBody<PageList<SystemRole>> role(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    );

    /**
     * 获取角色信息
     *
     * @param roleId
     * @return
     */
    @GetMapping("/role/{roleId}")
    ResultBody<SystemRole> getRole(@PathVariable(value = "roleId") Long roleId);

    /**
     * 添加角色
     *
     * @param roleCode    角色编码
     * @param roleName    角色显示名称
     * @param description 描述
     * @param status      启用禁用
     * @return
     */
    @PostMapping("/role/add")
    ResultBody<Boolean> addRole(
            @RequestParam(value = "roleCode") String roleCode,
            @RequestParam(value = "roleName") String roleName,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "status", defaultValue = "1", required = false) Integer status
    );

    /**
     * 更新角色
     *
     * @param roleId      角色ID
     * @param roleCode    角色编码
     * @param roleName    角色显示名称
     * @param description 描述
     * @param status      启用禁用
     * @return
     */
    @PostMapping("/role/update")
    ResultBody<Boolean> updateRole(
            @RequestParam(value = "roleId") Long roleId,
            @RequestParam(value = "roleCode") String roleCode,
            @RequestParam(value = "roleName") String roleName,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "status", defaultValue = "1", required = false) Integer status
    );


    /**
     * 更新状态
     *
     * @param roleId 角色ID
     * @param status 状态
     * @return
     */
    @PostMapping("/role/update/status")
    ResultBody<Boolean> updateStatus(
            @RequestParam("roleId") Long roleId,
            @RequestParam(value = "status", defaultValue = "1") Integer status
    );

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return
     */
    @PostMapping("/role/remove")
    ResultBody<Boolean> removeRole(
            @RequestParam(value = "roleId") Long roleId
    );

    /**
     * 菜单授权
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID.多个以,隔开
     * @return
     */
    @PostMapping("/role/grant/menu")
    ResultBody<Boolean> roleGrantMenu(
            @RequestParam(value = "roleId") Long roleId,
            @RequestParam("menuIds") String menuIds
    );


    /**
     * 操作授权
     *
     * @param roleId    角色ID
     * @param actionIds 操作ID.多个以,隔开
     * @return
     */
    @PostMapping("/role/grant/action")
    ResultBody<Boolean> roleGrantAction(
            @RequestParam(value = "roleId") Long roleId,
            @RequestParam("actionIds") String actionIds
    );

    /**
     * 接口授权
     *
     * @param roleId    角色ID
     * @param apiIds 接口ID.多个以,隔开
     * @return
     */
    @PostMapping("/role/grant/api")
    ResultBody<Boolean> roleGrantApi(
            @RequestParam(value = "roleId") Long roleId,
            @RequestParam("apiIds") String apiIds
    );


    /**
     * 获取角色已授权菜单资源
     *
     * @param roleId  角色ID
     * @return
     */
    @PostMapping("/role/granted/menu")
    ResultBody<PageList<SystemGrantAccess>> roleGrantedMenu(
            @RequestParam(value = "roleId") Long roleId
    );

    /**
     * 获取角色已授权操作资源
     *
     * @param roleId  角色ID
     * @return
     */
    @PostMapping("/role/granted/action")
    ResultBody<PageList<SystemGrantAccess>> roleGrantedAction(
            @RequestParam(value = "roleId") Long roleId
    );
}
