package com.github.lyd.gateway.producer.filter;

import com.github.lyd.common.exception.OpenExceptionHandler;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.utils.WebUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZuulErrorFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable ex = ctx.getThrowable();
        ResultBody responseData = OpenExceptionHandler.resolveException((Exception) ex, ctx.getRequest(), ctx.getResponse());
        WebUtils.writeJson(ctx.getResponse(), responseData);
        return null;
    }
}
