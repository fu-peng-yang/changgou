package com.itheima.feign;

import com.github.pagehelper.PageInfo;
import com.itheima.pojo.Result;
import com.itheima.pojo.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:admin
 * @Description:
 * @Date 2019/6/18 13:58
 *****/
@FeignClient(name="business")
@RequestMapping("/userInfo")
public interface UserInfoFeign {

    @PostMapping(value = "/add")
    String decrMoney(@RequestParam(value = "username")String username,
                            @RequestParam(value = "money")int money);

}