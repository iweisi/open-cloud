package com.github.lyd.base.client.api;

import com.github.lyd.common.model.ResultBody;
import com.github.lyd.base.client.entity.SystemGrantAccess;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author liuyadu
 */
public interface SystemGrantAccessRemoteService {

    /**
     * 获取系统用户私有权限
     *
     * @return
     */
    @GetMapping("/grant/access")
    ResultBody<List<SystemGrantAccess>> grantAccess();

}
