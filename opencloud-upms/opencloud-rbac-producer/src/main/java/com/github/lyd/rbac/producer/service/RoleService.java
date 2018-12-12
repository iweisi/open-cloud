package com.github.lyd.rbac.producer.service;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.rbac.client.entity.Role;

import java.util.List;

/**
 * 角色管理
 *
 * @author liuyadu
 */
public interface RoleService {

    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    PageList<Role> findListPage(PageParams pageParams, String keyword);

    /**
     * 获取角色信息
     *
     * @param roleId
     * @return
     */
    Role getRole(Long roleId);

    /**
     * 添加角色
     *
     * @param roleCode    角色编码
     * @param roleName    角色显示名称
     * @param description 描述
     * @param enable      启用禁用
     * @return
     */
    boolean addRole(String roleCode, String roleName, String description, boolean enable);

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
    boolean updateRole(Long roleId, String roleCode, String roleName, String description, boolean enable);

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return
     */
    boolean removeRole(Long roleId);

    /**
     * 检测角色编码是否存在
     *
     * @param roleCode
     * @return
     */
    boolean isExist(String roleCode);

    /**
     * 角色授权用户
     *
     * @param userId
     * @param roles
     * @return
     */
    boolean saveUserRole(Long userId, Long... roles);

    /**
     * 获取角色所有授权用户数量
     *
     * @param roleId
     * @return
     */
    int getCountByRole(Long roleId);

    /**
     * 获取用户角色数量
     *
     * @param userId
     * @return
     */
    int getCountByUser(Long userId);

    /**
     * 移除角色所有授权用户
     *
     * @param roleId
     * @return
     */
    boolean removeUserRoleByRole(Long roleId);

    /**
     * 移除用户的所有角色
     *
     * @param userId
     * @return
     */
    boolean removeUserRoleByUser(Long userId);

    /**
     * 检测是否存在
     *
     * @param userId
     * @param roleId
     * @return
     */
    boolean isExist(Long userId, Long roleId);

    /**
     * 获取用户角色
     *
     * @param userId
     * @return
     */
    List<Role> getUserRoles(Long userId);

}
