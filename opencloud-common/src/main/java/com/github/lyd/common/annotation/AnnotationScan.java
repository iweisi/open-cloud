package com.github.lyd.common.annotation;

import com.github.lyd.common.utils.StringUtils;
import com.github.lyd.common.autoconfigure.MqAutoConfiguration;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 自定义注解扫描
 *
 * @author liuyadu
 */
@Slf4j
public class AnnotationScan implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 初始化方法
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        amqpTemplate = applicationContext.getBean(RabbitTemplate.class);
        Environment env = applicationContext.getEnvironment();
        String serviceId = env.getProperty("spring.application.name", "application");
        List<Map<String, Object>> list = Lists.newArrayList();
        List<Map<String, Object>> limitList = Lists.newArrayList();
        log.info("ApplicationReadyEvent:{}", serviceId);
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(RestController.class);
        //遍历Bean
        Set<Map.Entry<String, Object>> entries = beans.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> map = iterator.next();
            Class<?> aClass = map.getValue().getClass();
            String prefix = "";
            if (aClass.isAnnotationPresent(RequestMapping.class)) {
                prefix = aClass.getAnnotation(RequestMapping.class).value()[0];
            }
            Method[] methods = aClass.getMethods();
            for (Method m : methods) {
                String path = getPath(m);
                if (StringUtils.isBlank(path)) {
                    continue;
                }
                //api 资源
                String code = m.getName();
                String name = "";
                String desc = "";
                if (m.isAnnotationPresent(ApiOperation.class)) {
                    ApiOperation operation = m.getAnnotation(ApiOperation.class);
                    name = operation.value();
                    desc = operation.notes();
                }

                if (StringUtils.isBlank(name)) {
                    name = code;
                }
                path = prefix + path;
                Map<String, Object> api = Maps.newHashMap();
                api.put("apiCode", code);
                api.put("apiName", name);
                api.put("serviceId", serviceId);
                api.put("url", path);
                api.put("description", desc);
                list.add(api);
                Map<String, Object> limit = Maps.newHashMap();
                // 限流
                if (m.isAnnotationPresent(ApiRateLimit.class)) {
                    ApiRateLimit rateLimit = m.getAnnotation(ApiRateLimit.class);
                    limit.put("types",rateLimit.types());
                    limit.put("serviceId", serviceId);
                    limit.put("limit",rateLimit.limit());
                    limit.put("interval",rateLimit.interval());
                    limit.put("quota",rateLimit.quota());
                    limitList.add(limit);
                }
            }
        }
        if (amqpTemplate != null) {
            amqpTemplate.convertAndSend(MqAutoConfiguration.QUEUE_API_RESOURCE, list);
            amqpTemplate.convertAndSend(MqAutoConfiguration.QUEUE_API_RESOURCE_LIMIT, limitList);
        }
    }

    private String getPath(Method method) {
        StringBuilder path = new StringBuilder();
        if (method.isAnnotationPresent(GetMapping.class)) {
            path.append(method.getAnnotation(GetMapping.class).value()[0]);
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            path.append(method.getAnnotation(PostMapping.class).value()[0]);
        } else if (method.isAnnotationPresent(RequestMapping.class)) {
            path.append(method.getAnnotation(RequestMapping.class).value()[0]);
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            path.append(method.getAnnotation(PutMapping.class).value()[0]);
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            path.append(method.getAnnotation(DeleteMapping.class).value()[0]);
        }
        return path.toString();
    }
}