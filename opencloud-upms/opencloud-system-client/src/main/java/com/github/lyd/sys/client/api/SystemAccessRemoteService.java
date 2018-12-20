package com.github.lyd.sys.client.api;

import com.github.lyd.common.model.ResultBody;
import com.github.lyd.sys.client.entity.SystemAccess;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface SystemAccessRemoteService {

    /**
     * 获取系统用户私有权限
     *
     * @return 应用信息
     */
    @GetMapping("/access")
    ResultBody<List<SystemAccess>> access();

}
