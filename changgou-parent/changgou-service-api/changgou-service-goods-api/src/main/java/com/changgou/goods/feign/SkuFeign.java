package com.changgou.goods.feign;
import com.changgou.goods.pojo.Sku;
import com.changgou.order.pojo.OrderItem;
import com.github.pagehelper.PageInfo;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/****
 * @Author:admin
 * @Description:
 * @Date 2019/6/18 13:58
 *****/
@FeignClient(value="goods")//指定要调用方法的服务名称
@RequestMapping("/sku")
public interface SkuFeign {

    /**
     * 商品信息递减
     * Map<key,value>  key:要递减的商品Id
     *                 value:要递减的商品数量
     * @param decrmap
     * @return
     */
    @GetMapping(value = "/decr/count")
    Result decrCount(@RequestParam Map<String, Integer> decrmap);
    /*@PostMapping(value = "/decr/count")
    public Result decrCount(@RequestBody OrderItem orderItem);*/
    /***
     * 查询Sku全部数据
     * @return
     */
    @GetMapping
    public Result<List<Sku>> findAll();

    /**
     * 根据条件搜索
     * @param sku
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<Sku>> findList(@RequestBody(required = false) Sku sku);

    /***
     * 根据ID查询Sku数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Sku> findById(@PathVariable(value = "id",required = true) Long id);
}
