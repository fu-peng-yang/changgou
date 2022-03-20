package com.changgou.seckill.mq;

import com.alibaba.fastjson.JSON;
import com.changgou.seckill.service.SeckillOrderService;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.seckill.mq
 * @Author: yang
 * @CreateTime: 2021-07-17 10:33
 * @Description: 监听
 */
@Component
@RabbitListener(queues = "${mq.pay.queue.seckillorder}")
public class SeckillMessageListener {

    @Autowired
    private SeckillOrderService seckillOrderService;
    /**
     * 消息监听
     * @param message
     */
    @RabbitHandler
    public void getMessage(String message){

        try {
            //将支付信息转成Map
            Map<String, String> resultMap = JSON.parseObject(message,Map.class);
            //return_code ->通信标识-SUCCESS
            String return_code = resultMap.get("return_code");
            //out_trade_no->订单号
            String outtradeno = resultMap.get("out_trade_no");
            //自定义数据
            String attach = resultMap.get("attach");
            Map<String,String> attachMap = JSON.parseObject(attach, Map.class);
            if (return_code.equals("SUCCESS")) {
                //result_code->业务结果-SUCCESS->改订单状态
                String result_code = resultMap.get("result_code");

                if (result_code.equals("SUCCESS")){
                    //改订单状态
                    seckillOrderService.updatePayStatus(attachMap.get("username"),resultMap.get("transaction_id"),resultMap.get("time_end"));
                }else {
                    //FAIL->删除订单[真实工作中存入到MYSQL]->回滚库存
                    seckillOrderService.deleteOrder(attachMap.get("username"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
