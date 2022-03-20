package com.changgou.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.oauth.controller
 * @Author: yang
 * @CreateTime: 2021-06-14 19:35
 * @Description:
 */
@Controller
@RequestMapping(value = "/oauth")
public class LoginRedirect {
    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(@RequestParam(value = "FROM",required = false,defaultValue = "")String from, Model model) {

        //存储from
        model.addAttribute("from",from);//获取FROM参数,并将参数的值存储到Model中
        return "login";
    }
}
