package com.github.lyd.base.client.entity;

import com.github.lyd.common.gen.SnowflakeId;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "system_access_logs")
public class SystemAccessLogs implements Serializable {
    /**
     * 访问ID
     */
    @Id
    @Column(name = "access_id")
    @KeySql(genId = SnowflakeId.class)
    private Long accessId;

    /**
     * 访问路径
     */
    private String path;

    private String method;

    /**
     * 请求IP
     */
    private String ip;

    /**
     * 响应状态
     */
    @Column(name = "http_status")
    private String httpStatus;

    /**
     * 访问时间
     */
    @Column(name = "access_time")
    private Date accessTime;

    /**
     * 请求数据
     */
    private String data;

    /**
     * 请求头
     */
    private String headers;

    /**
     * 异常
     */
    private String exception;

    private static final long serialVersionUID = 1L;

    /**
     * 获取访问ID
     *
     * @return access_id - 访问ID
     */
    public Long getAccessId() {
        return accessId;
    }

    /**
     * 设置访问ID
     *
     * @param accessId 访问ID
     */
    public void setAccessId(Long accessId) {
        this.accessId = accessId;
    }

    /**
     * 获取访问路径
     *
     * @return path - 访问路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置访问路径
     *
     * @param path 访问路径
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * @return method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method
     */
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    /**
     * 获取请求IP
     *
     * @return ip - 请求IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置请求IP
     *
     * @param ip 请求IP
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 获取响应状态
     *
     * @return http_status - 响应状态
     */
    public String getHttpStatus() {
        return httpStatus;
    }

    /**
     * 设置响应状态
     *
     * @param httpStatus 响应状态
     */
    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus == null ? null : httpStatus.trim();
    }

    /**
     * 获取访问时间
     *
     * @return access_time - 访问时间
     */
    public Date getAccessTime() {
        return accessTime;
    }

    /**
     * 设置访问时间
     *
     * @param accessTime 访问时间
     */
    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    /**
     * 获取请求数据
     *
     * @return data - 请求数据
     */
    public String getData() {
        return data;
    }

    /**
     * 设置请求数据
     *
     * @param data 请求数据
     */
    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }

    /**
     * 获取请求头
     *
     * @return headers - 请求头
     */
    public String getHeaders() {
        return headers;
    }

    /**
     * 设置请求头
     *
     * @param headers 请求头
     */
    public void setHeaders(String headers) {
        this.headers = headers == null ? null : headers.trim();
    }

    /**
     * 获取异常
     *
     * @return exception - 异常
     */
    public String getException() {
        return exception;
    }

    /**
     * 设置异常
     *
     * @param exception 异常
     */
    public void setException(String exception) {
        this.exception = exception == null ? null : exception.trim();
    }
}
