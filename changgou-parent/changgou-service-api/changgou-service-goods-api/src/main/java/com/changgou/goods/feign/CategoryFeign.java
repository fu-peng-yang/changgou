package com.changgou.goods.feign;

import com.changgou.goods.pojo.Category;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.goods.feign
 * @Author: Administrator
 * @CreateTime: 2020-10-27 17:01
 * @Description:
 */
@FeignClient(value="goods")//指定要调用方法的服务名称
@RequestMapping("/category")
public interface CategoryFeign {
    /**
     * 根据父节点Id查询子分类
     * @param pid
     * @return
     */
    @GetMapping(value = "/list/{pid}")
    Result<List<Category>> findByParentId(@PathVariable("pid")Integer pid);

    /**
     * 获取分类的对象信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
     Result<Category> findById(@PathVariable(name = "id") Integer id);
}
