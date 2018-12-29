package com.github.lyd.common.autoconfigure;

import com.github.lyd.common.utils.WebUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.UUID;

/**
 * 微服务之间feign调用请求头丢失的问题
 * 加入微服务之间传递的唯一标识,便于追踪
 *
 * @author liuyadu
 * @FeignClient(value = "rbac",configuration = FeignRequestInterceptor.class)
 */
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    /**
     * 微服务之间传递的唯一标识
     */
    private static final String X_REQUEST_SID = "X-Request-serialId";

    @Override
    public void apply(RequestTemplate template) {
        HttpServletRequest request = WebUtils.getHttpServletRequest();
        if (request != null) {
            // 防止丢失请求头
            Enumeration<String> enumeration = request.getHeaderNames();
            if (enumeration != null) {
                while (enumeration.hasMoreElements()) {
                    String key = enumeration.nextElement();
                    String value = request.getHeader(key);
                    template.header(key, value);
                }
            }
            // 微服务之间传递的唯一标识
            if (!template.headers().containsKey(X_REQUEST_SID)) {
                String sid = String.valueOf(UUID.randomUUID());
                template.header(X_REQUEST_SID, sid);
            }
            log.debug("request:{}", template.toString());
        }
    }

}
