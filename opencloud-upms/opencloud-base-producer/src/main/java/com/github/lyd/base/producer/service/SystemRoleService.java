package com.github.lyd.base.producer.service;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.base.client.entity.SystemRole;

import java.util.List;

/**
 * 角色管理
 *
 * @author liuyadu
 */
public interface SystemRoleService {

    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    PageList<SystemRole> findListPage(PageParams pageParams, String keyword);

    /**
     * 获取角色信息
     *
     * @param roleId
     * @return
     */
    SystemRole getRole(Long roleId);

    /**
     * 添加角色
     *
     * @param roleCode    角色编码
     * @param roleName    角色显示名称
     * @param description 描述
     * @param enable      启用禁用
     * @return
     */
    Boolean addRole(String roleCode, String roleName, String description, Boolean enable);

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
    Boolean updateRole(Long roleId, String roleCode, String roleName, String description, Boolean enable);

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return
     */
    Boolean removeRole(Long roleId);

    /**
     * 检测角色编码是否存在
     *
     * @param roleCode
     * @return
     */
    Boolean isExist(String roleCode);

    /**
     * 角色授权组员
     *
     * @param userId
     * @param roles
     * @return
     */
    Boolean saveMemberRoles(Long userId, Long... roles);

    /**
     * 获取角色所有授权组员数量
     *
     * @param roleId
     * @return
     */
    int getCountByRole(Long roleId);

    /**
     * 获取组员角色数量
     *
     * @param userId
     * @return
     */
    int getCountByUser(Long userId);

    /**
     * 移除角色所有组员
     *
     * @param roleId
     * @return
     */
    Boolean removeRoleMembers(Long roleId);

    /**
     * 移除组员的所有角色
     *
     * @param userId
     * @return
     */
    Boolean removeMemberRoles(Long userId);

    /**
     * 检测是否存在
     *
     * @param userId
     * @param roleId
     * @return
     */
    Boolean isExist(Long userId, Long roleId);

    /**
     * 获取组员角色
     *
     * @param userId
     * @return
     */
    List<SystemRole> getUserRoles(Long userId);

}
