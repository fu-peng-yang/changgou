package com.changgou.seckill.task;

import com.alibaba.fastjson.JSON;
import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.dao.SeckillOrderMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.pojo.SeckillOrder;
import entity.IdWorker;
import entity.SeckillStatus;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.seckill.task
 * @Author: yang
 * @CreateTime: 2021-07-11 17:56
 * @Description:
 */
@Component
public class MultiThreadingCreateOrder {
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * 异步执行
     * @Async:该方法会异步执行(底层多线程方式)
     */
    @Async
    public void createOrder() {
        try {
            System.out.println("准备睡会再下单!");
            Thread.sleep(10000);
            //从Redis队列中获取用户排队信息
            SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundListOps("SeckillOrderQueue").rightPop();
            if (seckillStatus==null){
                return;
            }

            //定义要购买的商品的ID和时区以及用户名
            String time = seckillStatus.getTime();
            Long id = seckillStatus.getGoodsId();
            String username = seckillStatus.getUsername();

            //先到SeckilGoodsCountList_ID队列中获取该商品的一个信息,如果能获取,则可以下单
            Object sgoods = redisTemplate.boundListOps("SeckillGoodsCountList_" + seckillStatus.getGoodsId()).rightPop();
            //如果不能获取该商品的队列信息,则表示没有库存,清理排队信息
            if (sgoods==null){
                //则表示没有库存,清理排队信息
                clearUserQueue(username);
                return;
            }


            //查询秒杀商品
            String namespace = "SeckillGoods_" + time;
            SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps(namespace).get(id);

            //判断有无库存
            if (seckillGoods==null || seckillGoods.getStockCount()<=0){
                //异常  程序终止
                throw  new RuntimeException("已售罄");
            }
            //创建订单对象
            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setId(idWorker.nextId());
            seckillOrder.setSeckillId(id); //商品ID
            seckillOrder.setMoney(seckillGoods.getCostPrice()); //支付金额
            seckillOrder.setUserId(username); //用户名
            seckillOrder.setCreateTime(new Date());  //创建时间
            seckillOrder.setStatus("0"); //未支付
            /**将订单对象存储起来
             * 1.一个用户只允许有一个未支付秒杀订单
             * 2.订单存入到Redis
             *     Hash
             *        namespace->SeckillOrder
             *                       username:SeckillOrder
             */
            redisTemplate.boundHashOps("SeckillOrder").put(username,seckillOrder);
            /**
             * 库存递减
             *    Redis.stockCount
             *    商品有可能是最后一个,如果是最后一个,则将Redis中商品信息删除,并且将Redis中该商品的数据同步到Mysql
             */
            seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
            Thread.sleep(10000);
            System.out.println(Thread.currentThread().getId() + "操作后剩余库存=" + seckillGoods.getStockCount());
            //获取该商品对应的队列数量
            Long size = redisTemplate.boundListOps("SeckillGoodsCountList_" + seckillStatus.getGoodsId()).size();

            //if (seckillGoods.getStockCount()<=0){
              if(size<=0){
                  //同步数量
                  seckillGoods.setStockCount(size.intValue());
                //同步数据到MySQL
                seckillGoodsMapper.updateByPrimaryKeySelective(seckillGoods);
                //删除Redis中的商品数据
                redisTemplate.boundHashOps(namespace).delete(id);
            }else {
                //同步数据到Redis
                redisTemplate.boundHashOps(namespace).put(id,seckillGoods);
            }
            //更新下单状态

            seckillStatus.setOrderId(seckillOrder.getId()); //id
            seckillStatus.setMoney(Float.valueOf(seckillGoods.getCostPrice())); //支付金额
            seckillStatus.setStatus(2); //待付款
            redisTemplate.boundHashOps("UserQueueStatus").put(username,seckillStatus);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            System.out.println("下单时间:"+simpleDateFormat.format(new Date()));
            //发送消息给延迟队列
            rabbitTemplate.convertAndSend("delaySeckillQueue", (Object) JSON.toJSONString(seckillStatus), new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setExpiration("10000");
                    return message;
                }
            });

            System.out.println("下单完成!");
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 清理用户排队抢单信息
     * @param username
     */
    public void clearUserQueue(String username){
        //排队标识清理
        redisTemplate.boundHashOps("UserQueueCount").delete(username);
        //排队信息清理掉
        redisTemplate.boundHashOps("UserQueueStatus").delete(username);
    }
}
