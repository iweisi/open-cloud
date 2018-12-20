package com.github.lyd.gateway.producer.service.feign;

import com.github.lyd.common.autoconfigure.FeignRequestInterceptor;
import com.github.lyd.common.constants.ServicesConstants;
import com.github.lyd.sys.client.api.SystemAccessRemoteService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author: liuyadu
 * @date: 2018/10/24 16:49
 * @description:
 */
@Component
@FeignClient(value = ServicesConstants.SYSTEM_SERVICE, configuration = FeignRequestInterceptor.class)
public interface SystemAccessApi extends SystemAccessRemoteService {


}
