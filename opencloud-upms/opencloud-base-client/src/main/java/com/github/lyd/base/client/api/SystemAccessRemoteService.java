package com.github.lyd.base.client.api;

import com.github.lyd.common.model.ResultBody;
import com.github.lyd.base.client.entity.SystemAccess;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface SystemAccessRemoteService {

    /**
     * 获取系统用户私有权限
     *
     * @return
     */
    @GetMapping("/access")
    ResultBody<List<SystemAccess>> access();

}
