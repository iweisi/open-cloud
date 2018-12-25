package com.github.lyd.base.client.api;

import com.github.lyd.base.client.entity.SystemAction;
import com.github.lyd.base.client.entity.SystemMenu;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.ResultBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface SystemMenuRemoteService {
    /**
     * 获取菜单资源列表
     *
     * @return
     */
    @PostMapping("/menu")
    ResultBody<PageList<SystemMenu>> menu(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    );

    /**
     * 获取菜单资源列表
     *
     * @return
     */
    @PostMapping("/menu/list")
    ResultBody<PageList<SystemMenu>> menuList(
            @RequestParam(name = "keyword", required = false) String keyword
    );

    /**
     * 获取菜单资源
     *
     * @param menuId menuId
     * @return
     */
    @GetMapping("/menu/{menuId}")
    ResultBody<SystemMenu> getMenu(@PathVariable("menuId") Long menuId);

    /**
     * 添加菜单资源
     *
     * @param menuCode    菜单编码
     * @param menuName    菜单名称
     * @param icon        图标
     * @param path         请求路径
     * @param status      是否启用
     * @param parentId    父节点ID
     * @param priority    优先级越小越靠前
     * @param description 描述
     * @return
     */
    @PostMapping("/menu/add")
    ResultBody<Boolean> addMenu(
            @RequestParam(value = "menuCode") String menuCode,
            @RequestParam(value = "menuName") String menuName,
            @RequestParam(value = "icon") String icon,
            @RequestParam(value = "prefix", required = false, defaultValue = "/") String prefix,
            @RequestParam(value = "path", required = false, defaultValue = "") String path,
            @RequestParam(value = "target", required = false, defaultValue = "_self") String target,
            @RequestParam(value = "status", defaultValue = "1") Integer status,
            @RequestParam(value = "parentId", required = false, defaultValue = "0") Long parentId,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority,
            @RequestParam(value = "description", required = false, defaultValue = "") String description
    );

    /**
     * 编辑菜单资源
     *
     * @param menuId      菜单ID
     * @param menuCode    菜单编码
     * @param menuName    菜单名称
     * @param icon        图标
     * @param path         请求路径
     * @param status      是否启用
     * @param parentId    父节点ID
     * @param priority    优先级越小越靠前
     * @param description 描述
     * @return
     */
    @PostMapping("/menu/update")
    ResultBody<Boolean> updateMenu(
            @RequestParam("menuId") Long menuId,
            @RequestParam(value = "menuCode") String menuCode,
            @RequestParam(value = "menuName") String menuName,
            @RequestParam(value = "icon") String icon,
            @RequestParam(value = "prefix", required = false, defaultValue = "/") String prefix,
            @RequestParam(value = "path", required = false, defaultValue = "") String path,
            @RequestParam(value = "target", required = false, defaultValue = "_self") String target,
            @RequestParam(value = "status", defaultValue = "1") Integer status,
            @RequestParam(value = "parentId", required = false, defaultValue = "0") Long parentId,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority,
            @RequestParam(value = "description", required = false, defaultValue = "") String description
    );

    /**
     * 更新状态
     *
     * @param menuId 菜单ID
     * @param status 状态
     * @return
     */
    @PostMapping("/menu/update/status")
    ResultBody<Boolean> updateStatus(
            @RequestParam("menuId") Long menuId,
            @RequestParam(value = "status", defaultValue = "1") Integer status
    );


    /**
     * 移除菜单
     *
     * @param menuId 菜单ID
     * @return
     */
    @PostMapping("/menu/remove")
    ResultBody<Boolean> removeMenu(
            @RequestParam("menuId") Long menuId
    );
}
