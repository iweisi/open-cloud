package com.github.lyd.base.provider.controller;

import com.github.lyd.base.client.api.SystemMenuRemoteService;
import com.github.lyd.base.client.dto.SystemMenuDto;
import com.github.lyd.base.client.entity.SystemMenu;
import com.github.lyd.base.provider.service.SystemMenuService;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuyadu
 */
@Api(tags = "菜单资源管理")
@RestController
public class SystemMenuController implements SystemMenuRemoteService {
    @Autowired
    private SystemMenuService menuService;

    /**
     * 菜单资源分页列表
     *
     * @return
     */
    @ApiOperation(value = "菜单资源分页列表", notes = "菜单资源分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "显示条数:最大999", paramType = "form"),
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/menu")
    @Override
    public ResultBody<PageList<SystemMenu>> menu(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        return ResultBody.success(menuService.findListPage(new PageParams(page, limit), keyword));
    }

    /**
     * 菜单资源列表
     *
     * @param keyword
     * @return
     */
    @ApiOperation(value = "菜单资源列表", notes = "菜单资源列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/menu/list")
    @Override
    public ResultBody<PageList<SystemMenu>> menuList(String keyword) {
        return ResultBody.success(menuService.findList(keyword));
    }

    /**
     * 获取菜单和操作列表
     *
     * @param keyword
     * @return
     */
    @ApiOperation(value = "获取菜单和操作列表", notes = "获取菜单和操作列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/menu/action/list")
    @Override
    public ResultBody<PageList<SystemMenuDto>> menuActionList(
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        return ResultBody.success(menuService.findWithActionList(keyword));
    }

    /**
     * 获取菜单资源
     *
     * @param menuId 应用menuId
     * @return 应用信息
     */
    @ApiOperation(value = "获取菜单资源", notes = "获取菜单资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", required = true, value = "menuId"),
    })
    @GetMapping("/menu/{menuId}")
    @Override
    public ResultBody<SystemMenu> getMenu(@PathVariable("menuId") Long menuId) {
        return ResultBody.success(menuService.getMenu(menuId));
    }

    /**
     * 添加菜单资源
     *
     * @param menuCode 菜单编码
     * @param menuName 菜单名称
     * @param icon     图标
     * @param prefix   请求前缀
     * @param path     请求路径
     * @param target   打开方式
     * @param status   是否启用
     * @param parentId 父节点ID
     * @param priority 优先级越小越靠前
     * @param menuDesc 描述
     * @return
     */
    @ApiOperation(value = "添加菜单资源", notes = "添加菜单资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuCode", required = true, value = "菜单编码", paramType = "form"),
            @ApiImplicitParam(name = "menuName", required = true, value = "菜单名称", paramType = "form"),
            @ApiImplicitParam(name = "icon", required = false, value = "图标", paramType = "form"),
            @ApiImplicitParam(name = "prefix", required = false, value = "请求路径", allowableValues = "/,http://,https://", paramType = "form"),
            @ApiImplicitParam(name = "path", required = false, value = "请求路径", paramType = "form"),
            @ApiImplicitParam(name = "target", required = false, value = "请求路径", allowableValues = "_self,_blank", paramType = "form"),
            @ApiImplicitParam(name = "parentId", required = false, defaultValue = "0", value = "父节点ID", paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, defaultValue = "1", allowableValues = "0,1", value = "是否启用", paramType = "form"),
            @ApiImplicitParam(name = "priority", required = false, value = "优先级越小越靠前", paramType = "form"),
            @ApiImplicitParam(name = "menuDesc", required = false, value = "描述", paramType = "form"),
    })
    @PostMapping("/menu/add")
    @Override
    public ResultBody<Long> addMenu(
            @RequestParam(value = "menuCode") String menuCode,
            @RequestParam(value = "menuName") String menuName,
            @RequestParam(value = "icon", required = false) String icon,
            @RequestParam(value = "prefix", required = false, defaultValue = "/") String prefix,
            @RequestParam(value = "path", required = false, defaultValue = "") String path,
            @RequestParam(value = "target", required = false, defaultValue = "_self") String target,
            @RequestParam(value = "status", defaultValue = "1") Integer status,
            @RequestParam(value = "parentId", required = false, defaultValue = "0") Long parentId,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority,
            @RequestParam(value = "menuDesc", required = false, defaultValue = "") String menuDesc
    ) {
        SystemMenu menu = new SystemMenu();
        menu.setMenuCode(menuCode);
        menu.setMenuName(menuName);
        menu.setIcon(icon);
        menu.setPath(path);
        menu.setPrefix(prefix);
        menu.setTarget(target);
        menu.setStatus(status);
        menu.setParentId(parentId);
        menu.setPriority(priority);
        menu.setMenuDesc(menuDesc);
        Long result = menuService.addMenu(menu);
        return ResultBody.success(result);
    }

    /**
     * 编辑菜单资源
     *
     * @param menuCode 菜单编码
     * @param menuName 菜单名称
     * @param icon     图标
     * @param prefix   请求前缀
     * @param path     请求路径
     * @param target   打开方式
     * @param status   是否启用
     * @param parentId 父节点ID
     * @param priority 优先级越小越靠前
     * @param menuDesc 描述
     * @return
     */
    @ApiOperation(value = "编辑菜单资源", notes = "编辑菜单资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", required = true, value = "菜单ID", paramType = "form"),
            @ApiImplicitParam(name = "menuCode", required = true, value = "菜单编码", paramType = "form"),
            @ApiImplicitParam(name = "menuName", required = true, value = "菜单名称", paramType = "form"),
            @ApiImplicitParam(name = "icon", required = false, value = "图标", paramType = "form"),
            @ApiImplicitParam(name = "prefix", required = false, value = "请求路径", allowableValues = "/,http://,https://", paramType = "form"),
            @ApiImplicitParam(name = "path", required = false, value = "请求路径", paramType = "form"),
            @ApiImplicitParam(name = "target", required = false, value = "请求路径", allowableValues = "_self,_blank", paramType = "form"),
            @ApiImplicitParam(name = "parentId", required = false, defaultValue = "0", value = "父节点ID", paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, defaultValue = "1", allowableValues = "0,1", value = "是否启用", paramType = "form"),
            @ApiImplicitParam(name = "priority", required = false, value = "优先级越小越靠前", paramType = "form"),
            @ApiImplicitParam(name = "menuDesc", required = false, value = "描述", paramType = "form"),
    })
    @PostMapping("/menu/update")
    @Override
    public ResultBody updateMenu(
            @RequestParam("menuId") Long menuId,
            @RequestParam(value = "menuCode") String menuCode,
            @RequestParam(value = "menuName") String menuName,
            @RequestParam(value = "icon", required = false) String icon,
            @RequestParam(value = "prefix", required = false, defaultValue = "/") String prefix,
            @RequestParam(value = "path", required = false, defaultValue = "") String path,
            @RequestParam(value = "target", required = false, defaultValue = "_self") String target,
            @RequestParam(value = "status", defaultValue = "1") Integer status,
            @RequestParam(value = "parentId", required = false, defaultValue = "0") Long parentId,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority,
            @RequestParam(value = "menuDesc", required = false, defaultValue = "") String menuDesc
    ) {
        SystemMenu menu = new SystemMenu();
        menu.setMenuId(menuId);
        menu.setMenuCode(menuCode);
        menu.setMenuName(menuName);
        menu.setIcon(icon);
        menu.setPath(path);
        menu.setPrefix(prefix);
        menu.setTarget(target);
        menu.setStatus(status);
        menu.setParentId(parentId);
        menu.setPriority(priority);
        menu.setMenuDesc(menuDesc);
        menuService.updateMenu(menu);
        return ResultBody.success();
    }

    /**
     * 更新菜单资源状态
     *
     * @param menuId 菜单ID
     * @return
     */
    @ApiOperation(value = "更新菜单资源状态", notes = "更新菜单资源状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", required = true, value = "menuId", paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, defaultValue = "1", allowableValues = "0,1", value = "是否启用", paramType = "form")
    })
    @PostMapping("/menu/update/status")
    @Override
    public ResultBody<Boolean> updateStatus(
            @RequestParam("menuId") Long menuId,
            @RequestParam(value = "status", defaultValue = "1") Integer status
    ) {
        menuService.updateStatus(menuId, status);
        return ResultBody.success();
    }

    /**
     * 移除菜单资源
     *
     * @param menuId 菜单ID
     * @return
     */
    @ApiOperation(value = "移除菜单资源", notes = "移除菜单资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", required = true, value = "menuId", paramType = "form"),
    })
    @PostMapping("/menu/remove")
    @Override
    public ResultBody<Boolean> removeMenu(
            @RequestParam("menuId") Long menuId
    ) {
        menuService.removeMenu(menuId);
        return ResultBody.success();
    }
}
