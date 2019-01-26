package com.github.lyd.gateway.producer.service;

import com.alibaba.fastjson.JSONObject;
import com.github.lyd.common.autoconfigure.MqAutoConfiguration;
import com.github.lyd.common.utils.WebUtils;
import com.google.common.collect.Maps;
import com.netflix.zuul.context.RequestContext;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@Component
public class AccessLogsService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void  addLogs(RequestContext ctx){
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        String method = request.getMethod();
        Throwable throwable = ctx.getThrowable();
        Map headers = WebUtils.getHttpHeaders();
        String path = request.getRequestURI();
        Map data = WebUtils.getParameterMap(request);
        String ip = WebUtils.getIpAddr(request);
        int httpStatus = response.getStatus();
        Map<String, Object> msg = Maps.newHashMap();
        Date accessTime = new Date();
        msg.put("headers", JSONObject.toJSON(headers));
        msg.put("path", path);
        msg.put("data", JSONObject.toJSON(data));
        msg.put("ip", ip);
        msg.put("httpStatus", httpStatus);
        msg.put("accessTime", accessTime);
        msg.put("method", method);
        amqpTemplate.convertAndSend(MqAutoConfiguration.QUEUE_ACCESS_LOGS, msg);
        if(throwable!=null){
            msg.put("exception", throwable.getMessage());
        }
    }
}
