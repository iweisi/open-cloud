package com.github.lyd.gateway.producer.filter;

import com.github.lyd.common.constants.ResultEnum;
import com.github.lyd.common.exception.OpenExceptionHandler;
import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.utils.StringUtils;
import com.github.lyd.common.utils.WebUtils;
import com.github.lyd.gateway.producer.service.AccessLogsService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * zuul错误响应过滤器
 *
 * @author liuyadu
 */
@Slf4j
public class ZuulErrorFilter extends ZuulFilter {
    @Autowired
    private AccessLogsService accessLogsService;

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        Throwable throwable = ctx.getThrowable();
        Exception ex = (Exception) throwable;
        if (StringUtils.toBoolean(ctx.get("rateLimitExceeded"))) {
            ex = new OpenMessageException(ResultEnum.TOO_MANY_REQUEST.getCode(), ResultEnum.TOO_MANY_REQUEST.getMessage());
        }
        try {
            accessLogsService.addLogs(ctx);
        } catch (Exception e) {
            log.error("添加访问日志异常:", e);
        }
        ResultBody responseData = OpenExceptionHandler.resolveException(ex, request, response);
        WebUtils.writeJson(ctx.getResponse(), responseData);
        return null;
    }
}
