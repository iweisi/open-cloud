package com.github.lyd.rbac.client.api;

import com.github.lyd.common.model.ResultBody;
import com.github.lyd.rbac.client.entity.ResourcePermission;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface PermissionRemoteService {

    /**
     * 获取用户私有权限
     *
     * @return 应用信息
     */
    @GetMapping("/permissions")
    ResultBody<List<ResourcePermission>> permissions();

}
