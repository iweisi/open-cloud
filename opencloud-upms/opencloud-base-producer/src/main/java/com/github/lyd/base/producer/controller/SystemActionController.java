package com.github.lyd.base.producer.controller;

import com.github.lyd.base.producer.service.SystemActionService;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.base.client.api.SystemActionRemoteService;
import com.github.lyd.base.client.entity.SystemAction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuyadu
 */
@Api(tags = "动作资源管理")
@RestController
public class SystemActionController implements SystemActionRemoteService {
    @Autowired
    private SystemActionService actionService;

    /**
     * 动作列表
     *
     * @return
     */
    @ApiOperation(value = "动作列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "显示条数:最大999", paramType = "form"),
            @ApiImplicitParam(name = "keyword", value = "查询字段", paramType = "form"),
    })
    @PostMapping("/actions")
    @Override
    public ResultBody<PageList<SystemAction>> actions(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        return ResultBody.success(actionService.findListPage(new PageParams(page, limit), keyword));
    }

    /**
     * 获取动作资源
     *
     * @param actionId 动作Id
     * @return 应用信息
     */
    @ApiOperation(value = "获取动作资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "actionId", required = true, value = "动作Id", paramType = "path"),
    })
    @GetMapping("/actions/{actionId}")
    @Override
    public ResultBody<SystemAction> getAction(@PathVariable("actionId") Long actionId) {
        return ResultBody.success(actionService.getAction(actionId));
    }

    /**
     * 添加动作资源
     *
     * @param actionCode  动作编码
     * @param actionName  动作名称
     * @param menuId      归属菜单
     * @param url         请求路径
     * @param enabled     是否启用
     * @param priority    优先级越小越靠前
     * @param actionDesc 描述
     * @return
     */
    @ApiOperation(value = "添加动作资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "actionCode", required = true, value = "动作编码", paramType = "form"),
            @ApiImplicitParam(name = "actionName", required = true, value = "动作名称", paramType = "form"),
            @ApiImplicitParam(name = "menuId", required = true, value = "归属菜单", paramType = "form"),
            @ApiImplicitParam(name = "url", required = false, value = "请求路径", paramType = "form"),
            @ApiImplicitParam(name = "enabled", required = true, defaultValue = "true", allowableValues = "true,false", value = "是否启用", paramType = "form"),
            @ApiImplicitParam(name = "priority", required = false, value = "优先级越小越靠前", paramType = "form"),
            @ApiImplicitParam(name = "actionDesc", required = false, value = "描述", paramType = "form"),
    })
    @PostMapping("/actions/add")
    @Override
    public ResultBody<Boolean> addAction(
            @RequestParam(value = "actionCode") String actionCode,
            @RequestParam(value = "actionName") String actionName,
            @RequestParam(value = "menuId") Long menuId,
            @RequestParam(value = "url", required = false, defaultValue = "") String url,
            @RequestParam(value = "enabled", defaultValue = "true") Boolean enabled,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority,
            @RequestParam(value = "actionDesc", required = false, defaultValue = "") String actionDesc
    ) {
        SystemAction action = new SystemAction();
        action.setActionCode(actionCode);
        action.setActionName(actionName);
        action.setMenuId(menuId);
        action.setUrl(url);
        action.setEnabled(enabled);
        action.setPriority(priority);
        action.setActionDesc(actionDesc);
        return ResultBody.success(actionService.addAction(action));
    }

    /**
     * 编辑动作资源
     *
     * @param actionId    动作ID
     * @param actionCode  动作编码
     * @param actionName  动作名称
     * @param menuId      归属菜单
     * @param url         请求路径
     * @param enabled     是否启用
     * @param priority    优先级越小越靠前
     * @param actionDesc 描述
     * @return
     */
    @ApiOperation(value = "编辑动作资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "actionId", required = true, value = "动作ID", paramType = "form"),
            @ApiImplicitParam(name = "actionCode", required = true, value = "动作编码", paramType = "form"),
            @ApiImplicitParam(name = "actionName", required = true, value = "动作名称", paramType = "form"),
            @ApiImplicitParam(name = "menuId", required = true, value = "归属菜单", paramType = "form"),
            @ApiImplicitParam(name = "url", required = false, value = "请求路径", paramType = "form"),
            @ApiImplicitParam(name = "enabled",required = true, defaultValue = "true", allowableValues = "true,false", value = "是否启用", paramType = "form"),
            @ApiImplicitParam(name = "priority", required = false, value = "优先级越小越靠前", paramType = "form"),
            @ApiImplicitParam(name = "actionDesc", required = false, value = "描述", paramType = "form"),
    })
    @PostMapping("/actions/update")
    @Override
    public ResultBody<Boolean> updateAction(
            @RequestParam("actionId") Long actionId,
            @RequestParam(value = "actionCode") String actionCode,
            @RequestParam(value = "actionName") String actionName,
            @RequestParam(value = "menuId") Long menuId,
            @RequestParam(value = "url", required = false, defaultValue = "") String url,
            @RequestParam(value = "enabled", defaultValue = "true") Boolean enabled,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority,
            @RequestParam(value = "actionDesc", required = false, defaultValue = "") String actionDesc
    ) {
        SystemAction action = new SystemAction();
        action.setActionId(actionId);
        action.setActionCode(actionCode);
        action.setActionName(actionName);
        action.setMenuId(menuId);
        action.setUrl(url);
        action.setEnabled(enabled);
        action.setPriority(priority);
        action.setActionDesc(actionDesc);
        return ResultBody.success(actionService.updateAction(action));
    }

    /**
     * 禁用动作资源
     *
     * @param actionId 动作ID
     * @return
     */
    @ApiOperation(value = "禁用动作资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "actionId", required = true, value = "动作ID", paramType = "form")
    })
    @PostMapping("/actions/disable")
    @Override
    public ResultBody<Boolean> disableAction(
            @RequestParam("actionId") Long actionId
    ) {
        return ResultBody.success(actionService.updateEnable(actionId, false));
    }

    /**
     * 启用动作资源
     *
     * @param actionId 动作ID
     * @return
     */
    @ApiOperation(value = "启用动作资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "actionId", required = true, value = "动作ID", paramType = "form")
    })
    @PostMapping("/actions/enable")
    @Override
    public ResultBody<Boolean> enableAction(
            @RequestParam("actionId") Long actionId
    ) {
        return ResultBody.success(actionService.updateEnable(actionId, true));
    }

    /**
     * 移除动作
     *
     * @param actionId 动作ID
     * @return
     */
    @ApiOperation(value = "移除动作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "actionId", required = true, value = "动作ID", paramType = "form")
    })
    @PostMapping("/actions/remove")
    @Override
    public ResultBody<Boolean> removeAction(
            @RequestParam("actionId") Long actionId
    ) {
        return ResultBody.success(actionService.removeAction(actionId));
    }
}
