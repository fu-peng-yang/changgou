package com.changgou.backstage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.backstage.controller
 * @Author: yang
 * @CreateTime: 2020-10-27 14:55
 * @Description:
 */
@Controller
public class BackstageController {

    @RequestMapping("view")
    public String showView(){
        return "后台首页";
    }


}
