package com.github.lyd.rbac.client.entity;

import com.github.lyd.common.gen.SnowflakeId;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 角色成员
 *
 * @author: liuyadu
 * @date: 2018/10/24 16:21
 * @description:
 */
@Table(name = "platform_roles_member")
public class RoleMember implements Serializable {
    private static final long serialVersionUID = -667816444278087761L;
    /**
     * 租户ID
     */
    @Column(name = "tenant_id")
    @KeySql(genId = SnowflakeId.class)
    private Long tenantId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}