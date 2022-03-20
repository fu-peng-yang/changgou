package com.changgou.reception.controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.reception.controller
 * @Author: yang
 * @CreateTime: 2020-10-29 20:01
 * @Description:
 */
@org.springframework.stereotype.Controller
public class Controller {
    /**
     * 首页
     * @return
     */
    @RequestMapping("/page")
    public String showPage(){
        return  "index";
    }

    /**
     * 登录
     * @return
     */
    @RequestMapping("views")
    public String showLogin(){
        return "login";
    }

    /**
     * 重定向
     * @return
     */
    @RequestMapping("/login")
    public String redirectLogin()
    {
        return "login";
    }

    /**
     * 注册
     * @return
     */
    @RequestMapping("/regirect")
    public String showRegister(){
        return "redirect";
    }


}
