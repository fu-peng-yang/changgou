package com.changgou.seckill.mq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.seckill.mq
 * @Author: yang
 * @CreateTime: 2021-07-17 16:09
 * @Description: 1.延时超时队列->负责数据暂时存储  Queue1
 * 2.真正监听的消息队列             Queue2
 * 3.创建交换机
 */
@Configuration
public class QueueConfig {

    /**
     * 延时超时队列->负责数据暂时存储  Queue1
     */
    @Bean
    public org.springframework.amqp.core.Queue delaySeckillQueue(){
        return QueueBuilder.durable("delaySeckillQueue")
                .withArgument("x-dead-letter-exchange","seckillExchange")      //当前队列的消息一旦过期,则进入到死信队列交换机
                .withArgument("x-dead-letter-routing-key","seckillQueue")      //将死信队列的数据路由到指定队列中
                .build();
    }

    /**
     * 真正监听的消息队列             Queue2
     */
    @Bean
    public org.springframework.amqp.core.Queue seckillQueue(){
        return new org.springframework.amqp.core.Queue("seckillQueue");
    }

    /**
     * 秒杀交换机
     * @return
     */
    @Bean
    public Exchange seckillExchange(){
        return new DirectExchange("seckillExchange");
    }

    /**
     * 队列绑定交换机
     * @param seckillQueue
     * @param seckillExchange
     * @return
     */
    @Bean
    public Binding seckillQueueBindingExchange(org.springframework.amqp.core.Queue seckillQueue, Exchange seckillExchange) {
        return BindingBuilder.bind(seckillQueue).to(seckillExchange).with("seckillQueue").noargs();
    }
}
