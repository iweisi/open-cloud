package com.github.lyd.sys.producer.controller;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.sys.client.api.SystemMenuRemoteService;
import com.github.lyd.sys.client.entity.SystemMenu;
import com.github.lyd.sys.producer.service.SystemMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuyadu
 */
@Api(tags = "菜单资源")
@RestController
public class SystemMenuController implements SystemMenuRemoteService {
    @Autowired
    private SystemMenuService menuService;

    /**
     * 菜单列表
     *
     * @return
     */
    @ApiOperation(value = "菜单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "显示条数:最大999", paramType = "form"),
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/menus")
    @Override
    public ResultBody<PageList<SystemMenu>> menus(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        return ResultBody.success(menuService.findListPage(new PageParams(page, limit), keyword));
    }

    /**
     * 获取菜单资源列表
     *
     * @param keyword
     * @return
     */
    @ApiOperation(value = "菜单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/menus/all")
    @Override
    public ResultBody<PageList<SystemMenu>> menusAll(String keyword) {
        return ResultBody.success(menuService.findList(keyword));
    }

    /**
     * 获取菜单资源
     *
     * @param menuId 应用menuId
     * @return 应用信息
     */
    @ApiOperation(value = "获取菜单资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", required = true, value = "menuId"),
    })
    @GetMapping("/menus/{menuId}")
    @Override
    public ResultBody<SystemMenu> getMenu(@PathVariable("menuId") Long menuId) {
        return ResultBody.success(menuService.getMenu(menuId));
    }

    /**
     * 添加菜单资源
     *
     * @param menuCode    菜单编码
     * @param menuName    菜单名称
     * @param icon        图标
     * @param url         请求路径
     * @param enabled     是否启用
     * @param parentId    父节点ID
     * @param priority    优先级越小越靠前
     * @param menuDesc 描述
     * @return
     */
    @ApiOperation(value = "添加菜单资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiCode", required = true, value = "菜单编码", paramType = "form"),
            @ApiImplicitParam(name = "apiName", required = true, value = "菜单名称", paramType = "form"),
            @ApiImplicitParam(name = "icon", required = true, value = "图标", paramType = "form"),
            @ApiImplicitParam(name = "url", required = false, value = "请求路径", paramType = "form"),
            @ApiImplicitParam(name = "parentId", required = false, defaultValue = "0", value = "父节点ID", paramType = "form"),
            @ApiImplicitParam(name = "enabled", required = true, defaultValue = "true", allowableValues = "true,false", value = "是否启用", paramType = "form"),
            @ApiImplicitParam(name = "priority", required = false, value = "优先级越小越靠前", paramType = "form"),
            @ApiImplicitParam(name = "menuDesc", required = false, value = "描述", paramType = "form"),
    })
    @PostMapping("/menus/add")
    @Override
    public ResultBody<Boolean> addMenu(
            @RequestParam(value = "menuCode") String menuCode,
            @RequestParam(value = "menuName") String menuName,
            @RequestParam(value = "icon") String icon,
            @RequestParam(value = "url", required = false) String url,
            @RequestParam(value = "enabled", defaultValue = "true") Boolean enabled,
            @RequestParam(value = "parentId", required = false, defaultValue = "0") Long parentId,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority,
            @RequestParam(value = "menuDesc", required = false, defaultValue = "") String menuDesc
    ) {
        SystemMenu menu = new SystemMenu();
        menu.setMenuCode(menuCode);
        menu.setMenuName(menuName);
        menu.setIcon(icon);
        menu.setUrl(url);
        menu.setEnabled(enabled);
        menu.setParentId(parentId);
        menu.setPriority(priority);
        menu.setMenuDesc(menuDesc);
        return ResultBody.success(menuService.addMenu(menu));
    }

    /**
     * 编辑菜单资源
     *
     * @param menuId      菜单ID
     * @param menuCode    菜单编码
     * @param menuName    菜单名称
     * @param icon        图标
     * @param url         请求路径
     * @param enabled     是否启用
     * @param parentId    父节点ID
     * @param priority    优先级越小越靠前
     * @param menuDesc 描述
     * @return
     */
    @ApiOperation(value = "编辑菜单资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", required = true, value = "菜单ID", paramType = "form"),
            @ApiImplicitParam(name = "apiCode", required = true, value = "菜单编码", paramType = "form"),
            @ApiImplicitParam(name = "apiName", required = true, value = "菜单名称", paramType = "form"),
            @ApiImplicitParam(name = "icon", required = true, value = "图标", paramType = "form"),
            @ApiImplicitParam(name = "url", required = false, value = "请求路径", paramType = "form"),
            @ApiImplicitParam(name = "parentId", required = false, defaultValue = "0", value = "父节点ID", paramType = "form"),
            @ApiImplicitParam(name = "enabled", required = true, defaultValue = "true", allowableValues = "true,false", value = "是否启用", paramType = "form"),
            @ApiImplicitParam(name = "priority", required = false, value = "优先级越小越靠前", paramType = "form"),
            @ApiImplicitParam(name = "menuDesc", required = false, value = "描述", paramType = "form"),
    })
    @PostMapping("/menus/update")
    @Override
    public ResultBody<Boolean> updateMenu(
            @RequestParam("menuId") Long menuId,
            @RequestParam(value = "menuCode") String menuCode,
            @RequestParam(value = "menuName") String menuName,
            @RequestParam(value = "icon") String icon,
            @RequestParam(value = "url", required = false) String url,
            @RequestParam(value = "enabled", defaultValue = "true") Boolean enabled,
            @RequestParam(value = "parentId", required = false, defaultValue = "0") Long parentId,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority,
            @RequestParam(value = "menuDesc", required = false, defaultValue = "") String menuDesc
    ) {
        SystemMenu menu = new SystemMenu();
        menu.setMenuId(menuId);
        menu.setMenuCode(menuCode);
        menu.setMenuName(menuName);
        menu.setIcon(icon);
        menu.setUrl(url);
        menu.setEnabled(enabled);
        menu.setParentId(parentId);
        menu.setPriority(priority);
        menu.setMenuDesc(menuDesc);
        return ResultBody.success(menuService.updateMenu(menu));
    }

    /**
     * 禁用菜单资源
     *
     * @param menuId 菜单ID
     * @return
     */
    @ApiOperation(value = "禁用菜单资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", required = true, value = "menuId", paramType = "form"),
    })
    @PostMapping("/menus/disable")
    @Override
    public ResultBody<Boolean> disableMenu(
            @RequestParam("menuId") Long menuId
    ) {
        return ResultBody.success(menuService.updateEnable(menuId, false));
    }

    /**
     * 启用菜单资源
     *
     * @param menuId 菜单ID
     * @return
     */
    @ApiOperation(value = "启用菜单资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", required = true, value = "menuId", paramType = "form"),
    })
    @PostMapping("/menus/enable")
    @Override
    public ResultBody<Boolean> enableMenu(
            @RequestParam("menuId") Long menuId
    ) {
        return ResultBody.success(menuService.updateEnable(menuId, true));
    }

    /**
     * 移除菜单
     *
     * @param menuId 菜单ID
     * @return
     */
    @ApiOperation(value = "移除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", required = true, value = "menuId", paramType = "form"),
    })
    @PostMapping("/menus/remove")
    @Override
    public ResultBody<Boolean> removeMenu(
            @RequestParam("menuId") Long menuId
    ) {
        return ResultBody.success(menuService.removeMenu(menuId));
    }
}
