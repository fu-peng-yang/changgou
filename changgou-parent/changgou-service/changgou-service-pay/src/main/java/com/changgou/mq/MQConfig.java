package com.changgou.mq;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.mq
 * @Author: yang
 * @CreateTime: 2021-07-04 16:15
 * @Description:
 */
@Configuration
public class MQConfig {
    /**
     * 读取配置文件中的信息的对象
     */
    @Autowired
    private Environment env;

    /**
     * 创建队列
     * @return
     */
    @Bean
    public Queue orderQueue(){
        return new Queue(env.getProperty("mq.pay.queue.order"));
    }

    /**
     * 创建交换机
     * @return
     */
    @Bean
    public Exchange orderExchange(){
        return new DirectExchange(env.getProperty("mq.pay.exchange.order"),true,false);
    }

    /**
     *队列绑定交换机
     * @return
     */
    @Bean
    public Binding orderQueueExchange(Queue orderQueue, Exchange orderExchange){
        return BindingBuilder.bind(orderQueue).to(orderExchange).with(env.getProperty("mq.pay.routing.key")).noargs();
    }



    //******************************秒杀队列创建*****************************
    /**
     * 创建队列
     * @return
     */
    @Bean
    public Queue orderSeckillQueue(){
        return new Queue(env.getProperty("mq.pay.queue.seckillorder"),true);
    }

    /**
     * 创建交换机
     * @return
     */
    @Bean
    public DirectExchange orderSeckillExchange(){
        return new DirectExchange(env.getProperty("mq.pay.exchange.seckillorder"),true,false);
    }

    /**
     *队列绑定交换机
     * @return
     */
    @Bean
    public Binding basicSeckillBinding(Queue orderSeckillQueue, Exchange orderSeckillExchange){
        return BindingBuilder.bind(orderSeckillQueue).to(orderSeckillExchange).with(env.getProperty("mq.pay.routing.seckillkey")).noargs();
    }
}
