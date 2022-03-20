package com.changgou.reception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.reception
 * @Author: yang
 * @CreateTime: 2020-10-27 14:38
 * @Description: 前台
 */
@SpringBootApplication
@EnableEurekaClient
public class ReceptionApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReceptionApplication.class,args);
    }
}
