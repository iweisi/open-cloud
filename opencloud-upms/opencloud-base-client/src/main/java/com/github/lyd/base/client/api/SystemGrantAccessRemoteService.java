package com.github.lyd.base.client.api;

import com.github.lyd.base.client.entity.SystemGrantAccess;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.ResultBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author liuyadu
 */
public interface SystemGrantAccessRemoteService {


    /**
     * 获取已授权访问列表
     *
     * @return
     */
    @PostMapping("/grant/access")
    ResultBody<PageList<SystemGrantAccess>> grantAccess(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "keyword", required = false) String keyword
    );


    /**
     * 获取已授权访问列表
     *
     * @return
     */
    @GetMapping("/grant/access/list")
    ResultBody<List<SystemGrantAccess>> grantAccessList();

}
