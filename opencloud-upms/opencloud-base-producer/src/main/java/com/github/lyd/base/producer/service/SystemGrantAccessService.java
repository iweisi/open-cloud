package com.github.lyd.base.producer.service;

import com.github.lyd.base.client.entity.SystemGrantAccess;

import java.util.List;

/**
 * 资源授权
 *
 * @author liuyadu
 */
public interface SystemGrantAccessService {

    /**
     * 获取系统用户已授权资源(包含个人特殊权限和所拥有角色的所以权限)
     *
     * @param userId       系统用户ID
     * @param resourceType 资源类型
     * @return
     */
    List<SystemGrantAccess> getUserAccess(Long userId, String resourceType);

    /**
     * 获取系统用户授权列表
     *
     * @param userId
     * @return
     */
    List<SystemGrantAccess> getUserPrivateAccess(Long userId);

    /**
     * 获取所有授权列表
     *
     * @return
     */
    List<SystemGrantAccess> getAccessList();

    /**
     * 添加授权
     *
     * @param grantOwnerId      所有者ID
     * @param grantOwnerType    所有者类型:user-系统用户 role-角色
     * @param resourceType 资源类型:
     * @param resourceIds
     * @return
     */
    Boolean addAccess(Long grantOwnerId, String grantOwnerType, String resourceType, Long... resourceIds);

    /**
     * 更新授权信息
     * 支持更新name,url,resourcePid,serviceId
     * 不允许修改权限标识
     *
     * @param resourceType
     * @param resourceId
     * @return
     */
    Boolean updateAccess(String resourceType, Long resourceId);

    /**
     * 检查资源是否存在
     *
     * @param resourceId
     * @param resourceType
     * @return
     */
    Boolean isExist(Long resourceId, String resourceType);
}
