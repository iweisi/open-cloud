package com.github.lyd.gateway.producer.locator;

import com.github.lyd.base.client.entity.SystemGrantAccess;
import com.github.lyd.common.utils.StringUtils;
import com.github.lyd.gateway.producer.service.feign.SystemGrantAccessApi;
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
public class GrantAccessLocator {
    private HashMap<String, Collection<ConfigAttribute>> map;
    private SystemGrantAccessApi systemAccessApi;
    private ZuulRouteLocator zuulRoutesLocator;
    private List<SystemGrantAccess> accessList;
    public List<SystemGrantAccess> getAccessList() {
        return accessList;
    }

    public void setAccessList(List<SystemGrantAccess> accessList) {
        this.accessList = accessList;
    }

    public GrantAccessLocator(SystemGrantAccessApi systemAccessApi, ZuulRouteLocator zuulRoutesLocator) {
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
                    String prefix = assess.getResource().getOrDefault("prefix", "/").toString();
                    String path = prefix + assess.getPath();
                    return route.getPrefix().concat(path);
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
        map = Maps.newHashMap();
        try {
            Collection<ConfigAttribute> array;
            ConfigAttribute cfg;
            accessList = systemAccessApi.grantAccessList().getData();
            if (accessList != null) {
                for (SystemGrantAccess assess : accessList) {
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
