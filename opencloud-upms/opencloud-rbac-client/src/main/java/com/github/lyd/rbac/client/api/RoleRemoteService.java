package com.github.lyd.rbac.client.api;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.rbac.client.entity.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: liuyadu
 * @date: 2018/11/19 16:27
 * @description:
 */
public interface RoleRemoteService {

    /**
     * 用户列表
     *
     * @return
     */
    @PostMapping("/roles")
    ResultBody<PageList<Role>> roles(
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
    @GetMapping("/roles/{roleId}")
    ResultBody<Role> getRole(@PathVariable(value = "roleId") Long roleId);

    /**
     * 添加角色
     *
     * @param roleCode    角色编码
     * @param roleName    角色显示名称
     * @param description 描述
     * @param enable      启用禁用
     * @return
     */
    @PostMapping("/roles/add")
    ResultBody<Boolean> addRole(
            @RequestParam(value = "roleCode") String roleCode,
            @RequestParam(value = "roleName") String roleName,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "enable", required = false) boolean enable
    );

    /**
     * 更新角色
     *
     * @param roleId      角色ID
     * @param roleCode    角色编码
     * @param roleName    角色显示名称
     * @param description 描述
     * @param enable      启用禁用
     * @return
     */
    @PostMapping("/roles/update")
    ResultBody<Boolean> updateRole(
            @RequestParam(value = "roleId") Long roleId,
            @RequestParam(value = "roleCode") String roleCode,
            @RequestParam(value = "roleName") String roleName,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "enable", required = false) boolean enable
    );


    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return
     */
    @PostMapping("/roles/remove")
    ResultBody<Boolean> removeRole(
            @RequestParam(value = "roleId") Long roleId
    );
}
