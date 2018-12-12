package com.github.lyd.admin.controller;

import com.alibaba.nacos.api.naming.NamingService;
import com.github.lyd.common.autoconfigure.GatewayProperties;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.utils.DateUtils;
import com.github.lyd.common.utils.RandomValueUtils;
import com.github.lyd.common.utils.SignatureUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author liuyadu
 */
@Controller
public class IndexController {
    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;
    @Autowired
    private GatewayProperties gatewayProperties;
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping("home")
    public String welcome() {
        return "home";
    }

    @GetMapping("server")
    @ResponseBody
    public ResultBody<PageList>  getServerInfo(HttpServletRequest request){
        Map map = Maps.newHashMap();
        ServletContext context = request.getServletContext();
        String serverInfo = context.getServerInfo();
        Runtime runtime = Runtime.getRuntime();
        Properties props = System.getProperties();
        double freeMemory = (double) runtime.freeMemory() / (1024 * 1024);
        double totalMemory = (double) runtime.totalMemory() / (1024 * 1024);
        double usedMemory = totalMemory - freeMemory;
        double percentFree = (usedMemory / totalMemory) * 100.0;
        NumberFormat format = NumberFormat.getPercentInstance();
        //设置保留几位小数
        format.setMaximumIntegerDigits(1);
        String percentFreeFmt = format.format(percentFree);

        //JVM可以使用的总内存
        map.put("totalMemory", totalMemory);
        //JVM可以使用的剩余内存
        map.put("freeMemory", freeMemory);
        map.put("usedMemory", usedMemory);
        map.put("percentFree", percentFree);
        map.put("percentFreeFmt", percentFreeFmt);
        //Java的运行环境版本
        map.put("javaVersion", props.getProperty("java.version"));
        map.put("serverInfo", serverInfo);
        map.put("serverDate", new Date());
        map.put("domain", request.getServerName());
        map.put("osName", props.getProperty("os.name"));
        map.put("osArch", props.getProperty("os.arch"));
        map.put("osVersion", props.getProperty("os.version"));
        map.put("apiGatewayAddr", gatewayProperties.getServerAddr());
        map.put("authServerAddr", gatewayProperties.getAuthServerAddr());
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress();
            map.put("ip", ip);
        } catch (Exception e) {
            map.put("ip", "127.0.0.1");
        }
        map.put("discoveryAddr", nacosDiscoveryProperties.getServerAddr());
        try {
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
            map.put("services", namingService.getServicesOfServer(1, 1).getCount());

        } catch (Exception e) {
            map.put("services", 0);
        }
        List list = Lists.newArrayList();
        list.add(map);
        PageList pageList = new PageList();
        pageList.setList(list);
        pageList.setTotal(1);
        return ResultBody.success(pageList);
    }

    /**
     * 参数签名
     *
     * @param params
     * @return
     */
    @PostMapping(value = "sign")
    @ResponseBody
    public ResultBody sign(@RequestParam Map params) {
        params.put("clientId", oAuth2ClientProperties.getClientId());
        params.put("nonce", RandomValueUtils.uuid().substring(0, 16));
        params.put("timestamp", DateUtils.getTimestampStr());
        params.put("signType", "SHA256");
        String sign = SignatureUtils.getSign(params, oAuth2ClientProperties.getClientSecret());
        params.put("sign", sign);
        return ResultBody.success(params);
    }

}