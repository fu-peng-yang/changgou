package com.changgou.order.mq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.order.mq.listener
 * @Author: yang
 * @CreateTime: 2021-07-04 20:28
 * @Description:
 */
@Component
@RabbitListener(queues = "orderListenerQueue")
public class DelayMessageListener {
    /**
     * 延时队列监听
     * @param message
     */
    @RabbitHandler
    public void getDelayMessage(String message) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.print("监听消息的时间:" + simpleDateFormat.format(new Date()));
        System.out.println("监听到的消息:" + message);
    }
}
