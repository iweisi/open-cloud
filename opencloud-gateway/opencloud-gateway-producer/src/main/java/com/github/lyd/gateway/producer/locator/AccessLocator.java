package com.github.lyd.gateway.producer.locator;

import com.github.lyd.common.utils.StringUtils;
import com.github.lyd.gateway.producer.service.feign.SystemGrantAccessApi;
import com.github.lyd.base.client.entity.SystemGrantAccess;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author liuyadu
 */
@Slf4j
public class AccessLocator {
    private HashMap<String, Collection<ConfigAttribute>> map = Maps.newHashMap();
    private SystemGrantAccessApi systemAccessApi;
    private ZuulRoutesLocator zuulRoutesLocator;

    public AccessLocator(SystemGrantAccessApi systemAccessApi, ZuulRoutesLocator zuulRoutesLocator) {
        this.systemAccessApi = systemAccessApi;
        this.zuulRoutesLocator = zuulRoutesLocator;
    }

    /**
     * 刷新限流配置
     */
    public void doRefresh() {
        map = loadResourceDefine();
    }

    /**
     * 获取路由后的地址
     *
     * @param assess
     * @return
     */
    protected String getZuulPath(SystemGrantAccess assess) {
        List<Route> rotes = zuulRoutesLocator.getRoutes();
        if (rotes != null && !rotes.isEmpty()) {
            for (Route route : rotes) {
                // 服务ID相同
                if (route.getId().equals(assess.getServiceId())) {
                    String path = assess.getPath();
                    String prefix = assess.getResource().getOrDefault("prefix", "/").toString();
                    path = prefix + assess.getPath();
                    if (route.isPrefixStripped()) {
                        return path;
                    } else {
                        return route.getPrefix().concat(path);
                    }
                }
            }
        }
        return assess.getPath();
    }

    /**
     * 加载授权列表
     */
    public HashMap<String, Collection<ConfigAttribute>> loadResourceDefine() {
        log.debug("=============加载动态权限==============");
        try {
            Collection<ConfigAttribute> array;
            ConfigAttribute cfg;
            List<SystemGrantAccess> assesss = systemAccessApi.grantAccessList().getData();
            if (assesss != null) {
                for (SystemGrantAccess assess : assesss) {
                    if (StringUtils.isBlank(assess.getPath())) {
                        continue;
                    }
                    String url = getZuulPath(assess);
                    array = map.get(url);
                    if (array == null) {
                        array = new ArrayList<>();
                    }
                    if (!array.contains(assess.getAuthority())) {
                        cfg = new SecurityConfig(assess.getAuthority());
                        array.add(cfg);
                    }
                    map.put(url, array);
                }
            }
        } catch (Exception e) {
            log.error("加载动态权限错误:{}", e.getMessage());
        }
        return map;
    }

    public HashMap<String, Collection<ConfigAttribute>> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Collection<ConfigAttribute>> map) {
        this.map = map;
    }
}
