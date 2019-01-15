package com.github.lyd.base.producer.listener;

import com.github.lyd.base.client.constants.BaseConstants;
import com.github.lyd.base.producer.service.SystemApiService;
import com.github.lyd.common.autoconfigure.MqAutoConfiguration;
import com.github.lyd.common.http.OpenRestTemplate;
import com.github.lyd.common.utils.BeanConvertUtils;
import com.github.lyd.base.client.entity.SystemApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * mq消息接收者
 *
 * @author liuyadu
 */
@Configuration
@Slf4j
public class MsgReceiverListener {
    @Autowired
    private SystemApiService apiService;
    @Autowired
    private OpenRestTemplate platformRestTemplate;

    /**
     * 接收API资源扫描消息
     *
     * @param list
     */
    @RabbitListener(queues = MqAutoConfiguration.QUEUE_SCAN_API_RESOURCE)
    public void ScanApiResourceQueue(List<Map> list) {
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
            platformRestTemplate.refreshGateway();
        }
    }
}
