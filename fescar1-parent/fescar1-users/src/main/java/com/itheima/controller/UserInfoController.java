package com.itheima.controller;

import com.itheima.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userInfo")
@CrossOrigin
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping(value = "/add")
    public String decrMoney(@RequestParam(value = "username")String username,
                            @RequestParam(value = "money")int money){
        userInfoService.decrMoney(username,money);
        return "success";
    }
}
