package com.changgou.backstage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.backstage
 * @Author: yang
 * @CreateTime: 2020-10-27 14:36
 * @Description: 后台
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.changgou.goods.feign")
public class BackstageApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackstageApplication.class,args);
    }
}
