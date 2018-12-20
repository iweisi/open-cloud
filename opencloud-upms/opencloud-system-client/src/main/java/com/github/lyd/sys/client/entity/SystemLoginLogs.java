package com.github.lyd.sys.client.entity;

import com.github.lyd.common.gen.SnowflakeId;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author liuyadu
 */
@Table(name = "system_login_logs")
public class SystemLoginLogs {
    @Id
    @KeySql(genId = SnowflakeId.class)
    private Long id;

    @Column(name = "login_time")
    private Date loginTime;

    /**
     * 登录Ip
     */
    @Column(name = "login_ip")
    private String loginIp;

    /**
     * 登录设备
     */
    @Column(name = "login_agent")
    private String loginAgent;

    /**
     * 登录次数
     */
    @Column(name = "login_nums")
    private Integer loginNums;

    @Column(name = "user_id")
    private Long userId;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return login_time
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * @param loginTime
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 获取登录Ip
     *
     * @return login_ip - 登录Ip
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * 设置登录Ip
     *
     * @param loginIp 登录Ip
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    /**
     * 获取登录设备
     *
     * @return login_agent - 登录设备
     */
    public String getLoginAgent() {
        return loginAgent;
    }

    /**
     * 设置登录设备
     *
     * @param loginAgent 登录设备
     */
    public void setLoginAgent(String loginAgent) {
        this.loginAgent = loginAgent;
    }

    /**
     * 获取登录次数
     *
     * @return login_nums - 登录次数
     */
    public Integer getLoginNums() {
        return loginNums;
    }

    /**
     * 设置登录次数
     *
     * @param loginNums 登录次数
     */
    public void setLoginNums(Integer loginNums) {
        this.loginNums = loginNums;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}