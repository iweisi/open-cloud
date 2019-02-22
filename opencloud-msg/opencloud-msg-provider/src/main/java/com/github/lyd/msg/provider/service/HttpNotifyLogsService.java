package com.github.lyd.msg.provider.service;

import com.github.lyd.msg.client.entity.MessageHttpNotifyLogs;

/**
 * 异步通知日志接口
 *
 * @author: liuyadu
 * @date: 2019/2/13 14:39
 * @description:
 */
public interface HttpNotifyLogsService {

    /**
     * 添加日志
     *
     * @param log
     */
    void addLog(MessageHttpNotifyLogs log);

    /**
     * 更细日志
     *
     * @param log
     */
    void modifyLog(MessageHttpNotifyLogs log);


    /**
     * 根据主键获取日志
     *
     * @param msgId
     * @return
     */
    MessageHttpNotifyLogs getLog(String msgId);
}
