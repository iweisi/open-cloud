package com.github.lyd.gateway.producer.controller;

import com.github.lyd.common.autoconfigure.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: liuyadu
 * @date: 2018/11/5 16:33
 * @description:
 */
@Controller
public class IndexController {
    @Autowired
    private SwaggerProperties swaggerProperties;

    @GetMapping("/")
    public String index() {
        if (swaggerProperties.getEnabled()) {
            return "redirect:swagger-ui.html";
        }
        return "index";
    }

}
