package com.changgou.goods.feign;

import com.changgou.goods.pojo.Spu;
import com.github.pagehelper.PageInfo;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.goods.feign
 * @Author: Administrator
 * @CreateTime: 2020-11-30 14:39
 * @Description:
 */
@FeignClient(value="goods")//指定要调用方法的服务名称
@RequestMapping("/spu")
public interface SpuFeign {

    /***
     * 根据SpuID查询Spu信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Spu> findById(@PathVariable(name = "id") Long id);

    /***
     * Spu分页条件搜索实现
     * @param spu
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  Spu spu, @PathVariable("page")  int page, @PathVariable("size")  int size);

    /***
     * 查询Spu全部数据
     * @return
     */
    @GetMapping
    Result<List<Spu>> findAll();
}
