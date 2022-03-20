package com.changgou.item.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.item.config
 * @Author: yang
 * @CreateTime: 2020-11-30 15:02
 * @Description:
 */
@ControllerAdvice
@Configuration
public class EnableMvcConfig implements WebMvcConfigurer{

    /***
     * 静态资源放行
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/items/**").addResourceLocations("classpath:/templates/items/");
    }
}
