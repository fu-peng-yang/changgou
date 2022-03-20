package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
*
*@ClassName: EurekaApplication
*@Description
*@Author yang
*@Date 2020/9/21
*@Time 16:40
*/

@SpringBootApplication
@EnableEurekaServer//开启Eureka
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class,args);
    }
}
