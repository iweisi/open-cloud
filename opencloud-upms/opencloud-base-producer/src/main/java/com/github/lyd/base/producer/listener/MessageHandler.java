package com.github.lyd.base.producer.listener;

import com.github.lyd.base.client.constants.BaseConstants;
import com.github.lyd.base.client.entity.SystemAccessLogs;
import com.github.lyd.base.client.entity.SystemApi;
import com.github.lyd.base.producer.mapper.SystemAccessLogsMapper;
import com.github.lyd.base.producer.service.SystemApiService;
import com.github.lyd.common.constants.MqConstants;
import com.github.lyd.common.http.OpenRestTemplate;
import com.github.lyd.common.utils.BeanConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.List;
import java.util.Map;

/**
 * mq消息接收者
 *
 * @author liuyadu
 */
@Configuration
@Slf4j
public class MessageHandler {
    @Autowired
    private SystemApiService apiService;
    @Autowired
    private OpenRestTemplate openRestTemplate;
    @Autowired
    private SystemAccessLogsMapper systemAccessLogsMapper;

    /**
     * 接收API资源扫描消息
     *
     * @param list
     */
    @RabbitListener(queues = MqConstants.QUEUE_SCAN_API_RESOURCE)
    public void ScanApiResourceQueue(@Payload List<Map> list) {
        try {
            if (list != null && list.size() > 0) {
                log.info("【apiResourceQueue监听到消息】" + list.toString());
                for (Map map : list) {
                    try {
                        SystemApi api = BeanConvertUtils.mapToObject(map, SystemApi.class);
                        SystemApi save = apiService.getApi(api.getApiCode(), api.getServiceId());
                        if (save == null) {
                            api.setIsPersist(BaseConstants.ENABLED);
                            apiService.addApi(api);
                        } else {
                            api.setApiId(save.getApiId());
                            apiService.updateApi(api);
                        }
                    } catch (Exception e) {
                        log.error("添加资源error:", e.getMessage());
                    }
                }

                // 重新刷新网关
                openRestTemplate.refreshGateway();
            }
        }catch (Exception e){
            log.error("error:",e);
        }
    }

    /**
     * 接收访问日志
     *
     * @param access
     */
    @RabbitListener(queues = MqConstants.QUEUE_ACCESS_LOGS)
    public void accessLogsQueue(@Payload Map access) {
        try {
            if (access != null) {
                SystemAccessLogs accessLogs = BeanConvertUtils.mapToObject(access, SystemAccessLogs.class);
                if (accessLogs != null) {
                    systemAccessLogsMapper.insertSelective(accessLogs);
                }
            }
        }catch (Exception e){
            log.error("error:",e);
        }
    }
}
