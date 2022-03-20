package com.changgou.search.controller;

import com.changgou.search.service.SkuService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * controller
 * 用于接收页面传递的请求 来测试 导入数据
 * 实现搜索的功能
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.search.controller *
 * @since 1.0
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/search")
public class SkuController {


    @Autowired
    private SkuService skuService;

    /**
     * 导入数据
     * @return
     */
    @GetMapping(value = "/import")
    public Result importDate(){

        skuService.importData();
        return new Result(true,StatusCode.OK,"导入成功");
    }

    @GetMapping
    public Map search(@RequestParam(required = false) Map<String,String> searchMap){

        Map<String, Object> map = skuService.search(searchMap);
        return map;
    }

}
