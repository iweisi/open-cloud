package com.github.lyd.base.client.entity;

import com.github.lyd.common.gen.SnowflakeId;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 操作表
 *
 * @author: liuyadu
 * @date: 2018/10/24 16:21
 * @description:
 */
@Table(name = "system_action")
public class SystemAction implements Serializable {
    private static final long serialVersionUID = 1471599074044557390L;
    /**
     * 资源ID
     */
    @Id
    @Column(name = "action_id")
    @KeySql(genId = SnowflakeId.class)
    private Long actionId;

    /**
     * 资源编码
     */
    @Column(name = "action_code")
    private String actionCode;

    /**
     * 资源名称
     */
    @Column(name = "action_name")
    private String actionName;

    /**
     * 资源路径
     */
    private String path;

    /**
     * 资源父节点
     */
    @Column(name = "menu_id")
    private Long menuId;

    /**
     * 优先级 越小越靠前
     */
    private Integer priority;

    /**
     * 资源描述
     */
    @Column(name = "action_desc")
    private String actionDesc;

    /**
     * 状态:0-无效 1-有效
     */
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取资源ID
     *
     * @return action_id - 资源ID
     */
    public Long getActionId() {
        return actionId;
    }

    /**
     * 设置资源ID
     *
     * @param actionId 资源ID
     */
    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    /**
     * 获取资源编码
     *
     * @return action_code - 资源编码
     */
    public String getActionCode() {
        return actionCode;
    }

    /**
     * 设置资源编码
     *
     * @param actionCode 资源编码
     */
    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    /**
     * 获取资源名称
     *
     * @return action_name - 资源名称
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * 设置资源名称
     *
     * @param actionName 资源名称
     */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取资源父节点
     *
     * @return menu_id - 资源父节点
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 设置资源父节点
     *
     * @param menuId 资源父节点
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * 获取优先级 越小越靠前
     *
     * @return priority - 优先级 越小越靠前
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * 设置优先级 越小越靠前
     *
     * @param priority 优先级 越小越靠前
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
