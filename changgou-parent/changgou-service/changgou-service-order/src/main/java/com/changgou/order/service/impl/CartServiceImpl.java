package com.changgou.order.service.impl;

import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartServcie;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: CartServiceImpl
 * @Description
 * @Author yang
 * @Date 2020/10/7
 * @Time 16:40
 * 购物车
 */
@Service
public class  CartServiceImpl implements CartServcie {
    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private SpuFeign spuFeign;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void add(Long id,Integer num,  String username) {

        //当添加购物车数量<=0的时候,需要移除该商品信息
        if(num<=0){
            //移除购物车该商品
            redisTemplate.boundHashOps("Cart_" + username).delete(id);
            //如果此时购物车数量为空,则连购物车一起移除
            Long size = redisTemplate.boundHashOps("Cart_" + username).size();
            if (size==null || size<=0){
                redisTemplate.delete("Cart_"+username);
            }
            return;
        }

        //1.根据商品的SKU的ID 获取sku的数据
        Result<Sku> skuResult = skuFeign.findById(id);

        Sku data = skuResult.getData();

        if (data != null) {

            //2.根据sku的数据对象 获取 该SKU对应的SPU的数据
            Long spuId = data.getSpuId();

            Result<Spu> spuResult = spuFeign.findById(spuId);
            Spu spu = spuResult.getData();

            //3.将数据存储到 购物车对象(order_item)中
            OrderItem orderItem = createOrderItem(id, num, data, spu);
            //4.数据添加到redis中  key:用户名 field:sku的ID  value:购物车数据(order_item)

            redisTemplate.boundHashOps("Cart_" + username).put(id, orderItem);// hset key field value   hget key field
        }


    }

    /**
     * 创建一个OrderItem对象
     * @param id
     * @param num
     * @param data
     * @param spu
     * @return
     */
    private OrderItem createOrderItem(Long id, Integer num, Sku data, Spu spu) {
        OrderItem orderItem = new OrderItem();

        orderItem.setCategoryId1(spu.getCategory1Id());
        orderItem.setCategoryId2(spu.getCategory2Id());
        orderItem.setCategoryId3(spu.getCategory3Id());
        orderItem.setSpuId(spu.getId());
        orderItem.setSkuId(id);
        orderItem.setName(data.getName());//商品的名称  sku的名称
        orderItem.setPrice(data.getPrice());//sku的单价
        orderItem.setNum(num);//购买的数量
        orderItem.setMoney(num * orderItem.getPrice());//单价* 数量
        //orderItem.setPayMoney(orderItem.getNum() * orderItem.getPrice());//单价* 数量
        orderItem.setImage(spu.getImage());//商品的图片地址
        return orderItem;
    }

    /**
     * 购物车列表
     * @param username
     * @return
     */
    @Override
    public List<OrderItem> list(String username) {
        //redisTemplate.boundHashOps("order_"+username).values() 获取指定命名空间I下的所有数据
        return redisTemplate.boundHashOps("Cart_"+username).values();
    }


}
