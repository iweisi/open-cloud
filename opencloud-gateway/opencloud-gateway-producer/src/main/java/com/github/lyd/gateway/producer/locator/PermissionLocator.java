package com.github.lyd.gateway.producer.locator;

import com.github.lyd.common.utils.StringUtils;
import com.github.lyd.gateway.producer.service.feign.PermissionRemoteServiceClient;
import com.github.lyd.rbac.client.entity.ResourcePermission;
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
public class PermissionLocator {
    private HashMap<String, Collection<ConfigAttribute>> map = Maps.newHashMap();
    private PermissionRemoteServiceClient permissionRemoteServiceClient;
    private ZuulRoutesLocator zuulRoutesLocator;

    public PermissionLocator(PermissionRemoteServiceClient permissionRemoteServiceClient, ZuulRoutesLocator zuulRoutesLocator) {
        this.permissionRemoteServiceClient = permissionRemoteServiceClient;
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
     * @param permission
     * @return
     */
    protected String getZuulPath(ResourcePermission permission) {
        List<Route> rotes = zuulRoutesLocator.getRoutes();
        if (rotes != null && !rotes.isEmpty()) {
            for (Route route : rotes) {
                // 服务ID相同
                if (route.getId().equals(permission.getServiceId())) {
                    String path = permission.getUrl();
                    if (!path.startsWith("/")) {
                        path = "/" + path;
                    }
                    if (route.isPrefixStripped()) {
                        return path;
                    } else {
                        return route.getPrefix().concat(path);
                    }
                }
            }
        }
        return permission.getUrl();
    }

    /**
     * 加载授权列表
     */
    public HashMap<String, Collection<ConfigAttribute>> loadResourceDefine() {
        log.debug("=============加载动态权限==============");
        try {
            Collection<ConfigAttribute> array;
            ConfigAttribute cfg;
            List<ResourcePermission> permissions = permissionRemoteServiceClient.permissions().getData();
            if (permissions != null) {
                for (ResourcePermission permission : permissions) {
                    if (StringUtils.isBlank(permission.getUrl())) {
                        continue;
                    }
                    String url = getZuulPath(permission);
                    array = map.get(url);
                    if (array == null) {
                        array = new ArrayList<>();
                    }
                    if (!array.contains(permission.getIdentityCode())) {
                        cfg = new SecurityConfig(permission.getIdentityCode());
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
