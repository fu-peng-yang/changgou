package com.changgou.order.controller;

import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartServcie;
import entity.Result;
import entity.StatusCode;
import entity.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: CartController
 * @Description
 * @Author yang
 * @Date 2020/10/7
 * @Time 17:11
 * 购物车
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartServcie cartServcie;
    @GetMapping(value = "/add")
    public Result addCart(Long id,Integer num){

        Map<String, String> userInfo = TokenDecode.getUserInfo();
        String username = userInfo.get("username");
        cartServcie.add(id,num,username);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 购物车列表
     * @param username
     * @return
     */
    @GetMapping("/list")
    public Result<List<OrderItem>> list(){
        //用户的令牌信息->解析令牌->username
        //OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        //String tokenValue = details.getTokenValue();
        //String username = "szitheima";
        Map<String, String> userInfo = TokenDecode.getUserInfo();
        System.out.println(userInfo);
        String username = userInfo.get("username");
        //查询购物车列表
        List<OrderItem> orderItemList = cartServcie.list(username);
        return new Result<List<OrderItem>>(true,StatusCode.OK,"获取购物车列表成功",orderItemList);
    }
}
