package com.itheima.order.service.impl;

import com.itheima.feign.ItemInfoFeign;
import com.itheima.order.dao.OrderInfoMapper;
import com.itheima.order.feign.UserFeignClient;
import com.itheima.order.service.OrderService;
import com.itheima.pojo.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private ItemInfoFeign itemInfoFeign;
    //@Autowired
    private UserFeignClient userFeignClient;

    @Override
    public void create(String userId, String commodityCode, Integer count) {
        int orderMoney = count * 100;//订单金额
        //创建订单
        jdbcTemplate.update("insert order_tbl(user_id,commodity_code,count,money) values(?,?,?,?)",
                new Object[]{userId, commodityCode, count, orderMoney});
        //扣减余额
        /*userFeignClient.reduce(userId, orderMoney);*/
    }

    @Override
    public  void  add(String username, int id, int count){

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setMessage("生成订单");
        orderInfo.setMoney(10);
        int icoutn = orderInfoMapper.insertSelective(orderInfo);
        System.out.println("添加订单受影响行数:"+icoutn);

        itemInfoFeign.decrCount(id,count);

    }
}
