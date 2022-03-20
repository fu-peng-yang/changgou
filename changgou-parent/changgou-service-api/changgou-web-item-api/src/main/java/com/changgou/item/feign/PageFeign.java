package com.changgou.item.feign;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.item.feign
 * @Author: yang
 * @CreateTime: 2021-05-29 17:55
 * @Description:
 */
@FeignClient(name="item")
@RequestMapping("/page")
public interface PageFeign {
    /**
     * 生成静态页面
     * @param id
     * @return
     */
    @RequestMapping("/createHtml/{id}")
    Result createHtml(@PathVariable(name="id") Long id);
}
