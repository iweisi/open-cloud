package com.github.lyd.common.autoconfigure;

import com.github.lyd.common.gen.SnowflakeIdGenerator;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 微服务之间feign调用请求头丢失的问题
 * 加入微服务之间传递的唯一标识,便于追踪
 * @author liuyadu
 * @FeignClient(value = "rbac",configuration = FeignRequestConfig.class)
 */
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    private static final String AUTHORIZATION_HEADER = "authorization";
    /**
     * 微服务之间传递的唯一标识
     */
    private static final String X_REQUEST_SID = "X-Request-serialId";

    private SnowflakeIdGenerator idGenerator;

    public FeignRequestInterceptor(SnowflakeIdGenerator snowflakeIdGenerator) {
        idGenerator = snowflakeIdGenerator;
    }

    @Override
    public void apply(RequestTemplate template) {
        HttpServletRequest httpServletRequest =   getHttpServletRequest();
        if(httpServletRequest!=null){
            // 防止oauth2请求头丢失
            template.header(AUTHORIZATION_HEADER, getHeaders(httpServletRequest).get(AUTHORIZATION_HEADER));
            // 微服务之间传递的唯一标识
            if (getHeaders(httpServletRequest).get(X_REQUEST_SID) == null) {
                String sid = String.valueOf(idGenerator.nextId());
                template.header(X_REQUEST_SID, sid);
            }
            log.debug(template.url());
            log.debug("request:{}", template.toString());
            log.debug("request headers:{}", template.headers());
        }
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        if(enumeration!=null){
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                map.put(key, value);
            }
        }
        return map;
    }

}