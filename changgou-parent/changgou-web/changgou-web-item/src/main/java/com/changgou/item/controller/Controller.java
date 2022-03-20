package com.changgou.item.controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.item.controller
 * @Author: yang
 * @CreateTime: 2020-12-01 18:21
 * @Description:
 */
@org.springframework.stereotype.Controller
public class Controller {
    @RequestMapping("view")
    public String show(){
        return "/items/1148472451710152704";
    }
}
