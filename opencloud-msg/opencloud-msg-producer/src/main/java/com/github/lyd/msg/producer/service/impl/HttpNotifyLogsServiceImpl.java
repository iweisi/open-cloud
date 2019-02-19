package com.github.lyd.msg.producer.service.impl;

import com.github.lyd.msg.client.entity.MessageHttpNotifyLogs;
import com.github.lyd.msg.producer.mapper.MessageHttpNotifyLogsMapper;
import com.github.lyd.msg.producer.service.HttpNotifyLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 异步通知日志接口
 *
 * @author: liuyadu
 * @date: 2019/2/13 14:39
 * @description:
 */
@Service
public class HttpNotifyLogsServiceImpl implements HttpNotifyLogsService {
    @Autowired
    private MessageHttpNotifyLogsMapper messageHttpNotifyLogsMapper;

    /**
     * 添加日志
     *
     * @param log
     */
    @Override
    public void addLog(MessageHttpNotifyLogs log) {
        messageHttpNotifyLogsMapper.insertSelective(log);
    }

    /**
     * 更细日志
     *
     * @param log
     */
    @Override
    public void modifyLog(MessageHttpNotifyLogs log) {
        messageHttpNotifyLogsMapper.updateByPrimaryKeySelective(log);
    }

    /**
     * 根据主键获取日志
     *
     * @param msgId
     * @return
     */
    @Override
    public MessageHttpNotifyLogs getLog(String msgId) {
        return messageHttpNotifyLogsMapper.selectByPrimaryKey(msgId);
    }
}
