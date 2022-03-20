package com.itheima.feign;
import com.github.pagehelper.PageInfo;
import com.itheima.pojo.OrderInfo;
import com.itheima.pojo.Result;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:admin
 * @Description:
 * @Date 2019/6/18 13:58
 *****/
@FeignClient(name="business")
@RequestMapping("/orderInfo")
public interface OrderInfoFeign {

    @PostMapping(value = "/add")
    String add(@RequestParam(value = "name") String username, @RequestParam(value = "id") int id, @RequestParam(value = "count") int count);
}