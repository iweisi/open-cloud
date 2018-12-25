package com.github.lyd.gateway.producer.listener;

import com.github.lyd.common.autoconfigure.MqAutoConfiguration;
import com.github.lyd.common.http.OpenRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author liuyadu
 */
@Configuration
@Slf4j
public class ReceiverListener {
    @Autowired
    private OpenRestTemplate platformRestTemplate;

    @RabbitListener(queues = MqAutoConfiguration.QUEUE_SCAN_API_RESOURCE_LIMIT)
    public void ScanResourceLimitQueue(List<Map> list) {
        if (list != null && list.size() > 0) {
            log.info("【apiResourceLimitQueue监听到消息】" + list.toString());
            // 重新刷新网关
            platformRestTemplate.refreshGateway();
        }
    }

}
