package com.github.lyd.base.provider.service.feign;

import com.github.lyd.common.constants.ServicesConstants;
import com.github.lyd.auth.client.api.ClientDetailsRemoteService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author liuyadu
 */
@Component
@FeignClient(value = ServicesConstants.AUTH_SERVICE)
public interface ClientDetailsRemoteServiceClient extends ClientDetailsRemoteService {
}
