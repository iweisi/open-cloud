package com.github.lyd.acms.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;


/**
 * @author liuyadu
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.github.lyd"})
@MapperScan(basePackages = "com.github.lyd.acms.producer.mapper")
public class AcmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcmsApplication.class, args);
    }
}
