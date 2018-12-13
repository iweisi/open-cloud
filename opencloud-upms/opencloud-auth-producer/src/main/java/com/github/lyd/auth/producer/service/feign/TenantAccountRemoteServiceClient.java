package com.github.lyd.auth.producer.service.feign;

import com.github.lyd.common.autoconfigure.FeignRequestInterceptor;
import com.github.lyd.common.constants.ServicesConstants;
import com.github.lyd.rbac.client.api.TenantAccountRemoteService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author: liuyadu
 * @date: 2018/10/24 16:49
 * @description:
 */
@Component
@FeignClient(value = ServicesConstants.RBAC_SERVICE, configuration = FeignRequestInterceptor.class)
public interface TenantAccountRemoteServiceClient extends TenantAccountRemoteService {


}
