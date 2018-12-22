package com.github.lyd.base.client.api;

import com.github.lyd.base.client.entity.SystemAction;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.ResultBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liuyadu
 */
public interface SystemActionRemoteService {
    /**
     * 获取动作资源列表
     *
     * @return
     */
    @PostMapping("/action")
    ResultBody<PageList<SystemAction>> action(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    );


    /**
     * 获取动作资源
     *
     * @param actionId actionId
     * @return 应用信息
     */
    @GetMapping("/action/{actionId}")
    ResultBody<SystemAction> getAction(@PathVariable("actionId") Long actionId);

    /**
     * 添加动作资源
     *
     * @param actionCode  动作编码
     * @param actionName  动作名称
     * @param menuId      归属菜单
     * @param url         请求路径
     * @param status     是否启用
     * @param priority    优先级越小越靠前
     * @param actionDesc 描述
     * @return
     */
    @PostMapping("/action/add")
    ResultBody<Boolean> addAction(
            @RequestParam(value = "actionCode") String actionCode,
            @RequestParam(value = "actionName") String actionName,
            @RequestParam(value = "menuId") Long menuId,
            @RequestParam(value = "url", required = false, defaultValue = "") String url,
            @RequestParam(value = "status", defaultValue = "1") Integer status,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority,
            @RequestParam(value = "actionDesc", required = false, defaultValue = "") String actionDesc
    );

    /**
     * 编辑动作资源
     *
     * @param actionId    动作ID
     * @param actionCode  动作编码
     * @param actionName  动作名称
     * @param menuId      归属菜单
     * @param url         请求路径
     * @param status     是否启用
     * @param priority    优先级越小越靠前
     * @param actionDesc 描述
     * @return
     */
    @PostMapping("/action/update")
    ResultBody<Boolean> updateAction(
            @RequestParam("actionId") Long actionId,
            @RequestParam(value = "actionCode") String actionCode,
            @RequestParam(value = "actionName") String actionName,
            @RequestParam(value = "menuId") Long menuId,
            @RequestParam(value = "url", required = false, defaultValue = "") String url,
            @RequestParam(value = "status", defaultValue = "1") Integer status,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority,
            @RequestParam(value = "actionDesc", required = false, defaultValue = "") String actionDesc
    );

    /**
     * 更新状态
     *
     * @param actionId 动作ID
     * @param status 状态
     * @return
     */
    @PostMapping("/action/update/status")
    ResultBody<Boolean> updateStatus(
            @RequestParam("actionId") Long actionId,
            @RequestParam(value = "status", defaultValue = "1") Integer status
    );

    /**
     * 移除动作
     *
     * @param actionId 动作ID
     * @return
     */
    @PostMapping("/action/remove")
    ResultBody<Boolean> removeAction(
            @RequestParam("actionId") Long actionId
    );
}
