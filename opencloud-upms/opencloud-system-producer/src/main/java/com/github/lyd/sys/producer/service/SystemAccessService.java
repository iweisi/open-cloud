package com.github.lyd.sys.producer.service;

import com.github.lyd.sys.client.entity.SystemAccess;

import java.util.List;

/**
 * 资源授权
 *
 * @author liuyadu
 */
public interface SystemAccessService {

    /**
     * 获取系统用户已授权资源(包含个人特殊权限和所拥有角色的所以权限)
     *
     * @param userId       系统用户ID
     * @param resourceType 资源类型
     * @return
     */
    List<SystemAccess> getUserAccess(Long userId, String resourceType);

    /**
     * 获取系统用户授权列表
     *
     * @param userId
     * @return
     */
    List<SystemAccess> getUserPrivateAccess(Long userId);

    /**
     * 获取所有授权列表
     *
     * @return
     */
    List<SystemAccess> getAccessList();

    /**
     * 添加授权
     *
     * @param identityId      所有者ID
     * @param identityPrefix    所有者类型:user-系统用户 role-角色
     * @param resourceType 资源类型:
     * @param resourceIds
     * @return
     */
    Boolean addAccess(Long identityId, String identityPrefix, String resourceType, Long... resourceIds);

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
