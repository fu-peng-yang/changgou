package com.changgou.backstage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.backstage.controller
 * @Author: yang
 * @CreateTime: 2020-11-01 19:34
 * @Description: 首页
 */
@Controller
@RequestMapping("/homepage")
public class HomePageController {
    /**
     * 系统首页
     * @return
     */
    @GetMapping("/sys_home_page")
    public String showSystemHomepage(){
        return "system_home_page";
    }

}
