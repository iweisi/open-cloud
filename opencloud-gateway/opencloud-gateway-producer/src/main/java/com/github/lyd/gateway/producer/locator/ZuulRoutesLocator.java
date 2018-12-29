package com.github.lyd.gateway.producer.locator;

import com.github.lyd.base.client.entity.SystemGatewayRoute;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态路由加载器
 *
 * @author: liuyadu
 * @date: 2018/10/23 10:31
 * @description:
 */
@Slf4j
public class ZuulRoutesLocator extends SimpleRouteLocator {

    private JdbcTemplate jdbcTemplate;
    private ZuulProperties properties;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate = jdbcTemplate;
    }

    public ZuulRoutesLocator(String servletPath, ZuulProperties properties, JdbcTemplate jdbcTemplate) {
        super(servletPath, properties);
        this.properties = properties;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 加载数据库路由配置
     *
     * @return
     */
    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        log.debug("=============加载动态路由==============");
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = Maps.newLinkedHashMap();
        routesMap.putAll(super.locateRoutes());
        //从db中加载路由信息
        routesMap.putAll(loadRouteWithDb());
        //优化一下配置
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = Maps.newLinkedHashMap();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            // Prepend with slash if not already present.
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return values;
    }

    @Override
    public void doRefresh() {
        super.doRefresh();
    }

    /**
     * @return
     * @description 加载路由配置，由子类去实现
     * @date 2017年7月3日 下午6:04:42
     * @version 1.0.0
     */
    public Map<String, ZuulRoute> loadRouteWithDb() {
        Map<String, ZuulProperties.ZuulRoute> routes = Maps.newLinkedHashMap();
        try {
            List<SystemGatewayRoute> results = jdbcTemplate.query("select * from system_gateway_route where status = 1 ", new
                    BeanPropertyRowMapper<>(SystemGatewayRoute.class));
            if (results != null && results.size() > 0) {
                for (SystemGatewayRoute result : results) {
                    if (StringUtils.isEmpty(result.getPath())) {
                        continue;
                    }
                    if (StringUtils.isEmpty(result.getServiceId()) && StringUtils.isEmpty(result.getUrl())) {
                        continue;
                    }
                    ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();

                    BeanUtils.copyProperties(result, zuulRoute);
                    routes.put(zuulRoute.getPath(), zuulRoute);
                }
            }
        } catch (Exception e) {
            log.error("加载动态路由错误:{}", e.getMessage());
        }
        return routes;
    }
}
