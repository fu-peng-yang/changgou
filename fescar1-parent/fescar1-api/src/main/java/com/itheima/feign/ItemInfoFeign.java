package com.itheima.feign;
import com.github.pagehelper.PageInfo;
import com.itheima.pojo.ItemInfo;
import com.itheima.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:admin
 * @Description:
 * @Date 2019/6/18 13:58
 *****/
@FeignClient(name="fescar-order")
@RequestMapping("/itemInfo")
public interface ItemInfoFeign {

    @PostMapping(value = "/decrCount")
    String decrCount(@RequestParam(value = "id")int id,@RequestParam(value = "count")int count);

}