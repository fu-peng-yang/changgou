package com.changgou;


import entity.FeignInterceptor;
import entity.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

/**
*
*@ClassName: GoodsApplication
*@Description 
*@Author yang
*@Date 2020/9/21
*@Time 18:31
*/
@SpringBootApplication
@EnableEurekaClient//启动Eureka客户端
/**
 * 开启通用Mapper的包扫描
 * 注意包名:tk.mybatis.spring.annotation.MapperScan
 */
@MapperScan(basePackages = {"com.changgou.goods.dao"})
public class GoodsApplication {


    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class,args);
    }

    @Bean
    public IdWorker idWorker() {

        return new IdWorker(0, 0);
    }
    @Bean
    public FeignInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }
}
