package com.changgou.backstage.controller;

import com.changgou.goods.feign.CategoryFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.pojo.Spu;
import com.github.pagehelper.PageInfo;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.backstage.controller.goods
 * @Author: yang
 * @CreateTime: 2020-10-27 17:09
 * @Description:
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private CategoryFeign categoryFeign;
    @Autowired
    private SpuFeign spuFeign;

    @RequestMapping("/list/{pid}")
    public String findByParentId(@PathVariable("pid") Integer pid, Model model) {
        Result<List<Category>> categoryList = categoryFeign.findByParentId(pid);
        model.addAttribute("categoryList", categoryList);
        return "选择商品分类";
    }

    /*@RequestMapping("/list/{page}/{size}")
    public String showGoodsList(Model model,Spu spu){
        Result<PageInfo> spuPageList = spuFeign.findPage(spu);

        System.out.println(spuPageList);

        model.addAttribute("spuList",spuPageList);
        return "商品列表";
    }*/

    /***
     * 查询Spu全部数据
     * @return
     */
    @RequestMapping("list")
    public String findAll(Model model){
        Result<List<Spu>> all = spuFeign.findAll();
        model.addAttribute("spuList",all);
        return "商品列表";
    }
}
