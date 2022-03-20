package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
*
*@ClassName: FileApplication
*@Description
*@Author yang
*@Date 2020/9/22
*@Time 17:16
*/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//排除掉数据库自动加载
@EnableEurekaClient
public class FileApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class,args);
    }
}
