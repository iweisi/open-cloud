package com.github.lyd.rbac.producer.service.feign;

import com.github.lyd.common.autoconfigure.FeignRequestInterceptor;
import com.github.lyd.common.constants.ServicesConstants;
import com.github.lyd.auth.client.api.ClientDetailsRemoteService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author liuyadu
 */
@Component
@FeignClient(value = ServicesConstants.OAUTH_SERVICE, configuration = FeignRequestInterceptor.class)
public interface ClientDetailsRemoteServiceClient extends ClientDetailsRemoteService {
}
