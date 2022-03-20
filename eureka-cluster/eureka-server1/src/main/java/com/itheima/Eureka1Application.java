package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.itheima
 * @Author: yang
 * @CreateTime: 2021-07-18 17:37
 * @Description:
 */

    @SpringBootApplication
    @EnableEurekaServer//开启Eureka
    public class Eureka1Application {
        public static void main(String[] args) {
            SpringApplication.run(Eureka1Application.class,args);
        }
    }

