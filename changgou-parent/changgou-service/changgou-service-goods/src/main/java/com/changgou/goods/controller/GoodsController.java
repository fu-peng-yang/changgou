package com.changgou.goods.controller;

import com.changgou.goods.pojo.Spu;
import com.changgou.goods.service.GoodsService;
import com.changgou.goods.service.SpuService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.goods.controller
 * @Author: yang
 * @CreateTime: 2020-11-17 20:18
 * @Description: 商品控制层
 */
@RestController
@RequestMapping("goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SpuService spuService;
    /**
     * 分页显示商品列表
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/list/{page}/{size}")
    public Result<Spu> findByPage(@PathVariable Integer page, @PathVariable Integer size){
        PageInfo<Spu> spuPages = goodsService.findPage(page, size);
        return new Result<>(true,StatusCode.OK,"查询成功",spuPages);
    }

    /**
     * 根据名称/货号 分页显示商品数据
     * @param id
     * @param sn
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search/{page}/{size}")
    public Result<Spu> findPageByExample(@PathVariable Integer page, @PathVariable Integer size,@RequestParam(required = false) String name,@RequestParam(required = false) String sn){
        PageInfo<Spu> pageByExample = goodsService.findPageByExample(page, size, name, sn);
        return new Result<Spu>(true,StatusCode.OK,"查询成功",pageByExample);
    }

    /**
     * 统计所有商品数据
     * @param spu
     * @return
     */
    @RequestMapping("/count")
    public Result spuCount(Spu spu){
        int count = spuService.spuCount(spu);
        return  new Result(true,StatusCode.OK,"查询成功",count);
    }

    /**
     * 统计已上架商品数量
     * @return
     */
    @RequestMapping("/countIsMarketable")
    public Result spuCountIsMarketable(/*@RequestParam("isMarketable") String isMarketable*/){
        int marketable = spuService.spuCountIsMarketable();
        return new Result(true,StatusCode.OK,"查询成功",marketable);
    }

    /**
     * 统计未上架商品数量
     * @return
     */
    @RequestMapping("/countIsNotMarketable")
    public Result spuCountIsNotMarketable(){
        int marketable = spuService.spuCountIsNotMarketable();
        return new Result(true,StatusCode.OK,"查询成功",marketable);
    }

    /**
     * 统计待审核商品数量
     * @return
     */
    @RequestMapping("/countWaitStatus")
    public Result selectCountWaitStatus(){
        int count = spuService.selectCountByWaitStatus();
        return new Result(true,StatusCode.OK,"查询成功",count);
    }

    /**
     * 统计未通过商品数量
     * @return
     */
    @RequestMapping("/countNotStatus")
    public Result selectCountNotStatus(){
        int count = spuService.selectCountByNotStatus();
        return new Result(true,StatusCode.OK,"查询成功",count);
    }
    }
