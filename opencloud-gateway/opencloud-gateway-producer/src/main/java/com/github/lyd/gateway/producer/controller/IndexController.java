package com.github.lyd.gateway.producer.controller;

import com.github.lyd.common.autoconfigure.SwaggerProperties;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.utils.SignatureUtils;
import com.github.lyd.common.utils.WebUtils;
import com.github.lyd.rbac.client.api.AppInfoRemoteService;
import com.github.lyd.rbac.client.dto.AppInfoDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author: liuyadu
 * @date: 2018/11/5 16:33
 * @description:
 */
@Controller
public class IndexController {
    @Autowired
    private SwaggerProperties swaggerProperties;
    @Autowired
    private AppInfoRemoteService appInfoRemoteService;

    @GetMapping("/")
    public String index() {
        if (swaggerProperties.getEnabled()) {
            return "redirect:swagger-ui.html";
        }
        return "index";
    }

    /**
     * 参数签名
     *
     * @param
     * @return
     */
    @ApiOperation(value = "请求参数数字签名", notes = "只适用于内部应用,外部不能使用。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientId", required = true, value = "应用ID", paramType = "form"),
            @ApiImplicitParam(name = "nonce", required = true, value = "随机字符串", paramType = "form"),
            @ApiImplicitParam(name = "timestamp", required = true, value = "请求时间戳:yyyyMMddHHmmss", paramType = "form"),
            @ApiImplicitParam(name = "signType", required = true, allowableValues = "SHA256,MD5", value = "签名类型", paramType = "form"),
            @ApiImplicitParam(name = "params1", value = "参数1", paramType = "form"),
            @ApiImplicitParam(name = "params2", value = "参数2", paramType = "form"),
            @ApiImplicitParam(name = "paramsN", value = "参数...", paramType = "form"),
    })
    @PostMapping(value = "sign")
    @ResponseBody
    public ResultBody sign(HttpServletRequest request) throws Exception {
        Map<String, String> params = WebUtils.getParameterMap(request);
        //验证参数
        SignatureUtils.validateParams(params);
        String clientId = params.get("clientId");
        AppInfoDto app = appInfoRemoteService.getApp(clientId).getData();
        Assert.notNull(app, "应用不存在");
        String sign = SignatureUtils.getSign(params, app.getAppSecret());
        params.put("sign", sign);
        return ResultBody.success(sign);
    }
}
