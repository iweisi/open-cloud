package com.github.lyd.rbac.producer.service;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.rbac.client.entity.Roles;

import java.util.List;

/**
 * 角色管理
 *
 * @author liuyadu
 */
public interface RolesService {

    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    PageList<Roles> findListPage(PageParams pageParams, String keyword);

    /**
     * 获取角色信息
     *
     * @param roleId
     * @return
     */
    Roles getRole(Long roleId);

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
     * 角色授权租户
     *
     * @param tenantId
     * @param roles
     * @return
     */
    boolean saveUserRole(Long tenantId, Long... roles);

    /**
     * 获取角色所有授权租户数量
     *
     * @param roleId
     * @return
     */
    int getCountByRole(Long roleId);

    /**
     * 获取租户角色数量
     *
     * @param tenantId
     * @return
     */
    int getCountByUser(Long tenantId);

    /**
     * 移除角色所有授权租户
     *
     * @param roleId
     * @return
     */
    boolean removeUserRoleByRole(Long roleId);

    /**
     * 移除租户的所有角色
     *
     * @param tenantId
     * @return
     */
    boolean removeUserRoleByUser(Long tenantId);

    /**
     * 检测是否存在
     *
     * @param tenantId
     * @param roleId
     * @return
     */
    boolean isExist(Long tenantId, Long roleId);

    /**
     * 获取租户角色
     *
     * @param tenantId
     * @return
     */
    List<Roles> getTenantRoles(Long tenantId);

}
