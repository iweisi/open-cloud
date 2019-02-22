package com.github.lyd.acms.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;


/**
 * @author liuyadu
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = "com.github.lyd.acms.provider.mapper")
public class AcmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcmsApplication.class, args);
    }
}
