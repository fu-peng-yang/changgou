package com.changgou.order.service;

import com.changgou.order.pojo.OrderItem;

import java.awt.*;
import java.awt.print.PrinterGraphics;
import java.util.List;

/**
*
*@ClassName: CartServcie
*@Description
*@Author yang
*@Date 2020/10/7
*@Time 16:38
*购物车
*/
public interface CartServcie {

    /**
     * 添加购物车
     *
     * @param num
     * @param id
     * @param username
     */
    void add(Long id,Integer num,  String username);

    /**
     * 购物车列表
     * @param username
     * @return
     */
    List<OrderItem> list(String username);
}
