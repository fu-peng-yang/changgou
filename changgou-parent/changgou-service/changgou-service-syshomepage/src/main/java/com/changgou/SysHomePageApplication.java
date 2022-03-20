package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou
 * @Author: yang
 * @CreateTime: 2021-08-15 17:04
 * @Description: 系统首页
 */
/*@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication*/
@SpringCloudApplication
public class SysHomePageApplication {
    public static void main(String[] args) {
        SpringApplication.run(SysHomePageApplication.class,args);
    }
}
