package com.github.lyd.gateway.producer.filter;

import com.github.lyd.gateway.producer.service.AccessLogsService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * zuul代理前置过滤器
 * @author liuyadu
 */
@Slf4j
public class ZuulLogsFilter extends ZuulFilter {

    /**
     * 是否应该执行该过滤器，如果是false，则不执行该filter
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Autowired
    private AccessLogsService accessLogsService;
    /**
     * 过滤器类型
     * 顺序: pre ->routing -> post ,以上3个顺序出现异常时都可以触发error类型的filter
     */
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    /**
     * 同filterType类型中，order值越大，优先级越低
     */
    @Override
    public int filterOrder() {
         return FilterConstants.PRE_DECORATION_FILTER_ORDER + 2;
    }

    /**
     */
    @Override
    public Object run() {
       RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        try {
            accessLogsService.addLogs(ctx);
        }catch (Exception e){
            log.error("添加访问日志异常:",e);
        }
        return null;
    }


}
