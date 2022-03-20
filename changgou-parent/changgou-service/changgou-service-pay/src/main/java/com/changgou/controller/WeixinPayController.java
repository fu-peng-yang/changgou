package com.changgou.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.service.WeixinPayService;
import com.github.wxpay.sdk.WXPayUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.controller
 * @Author: yang
 * @CreateTime: 2021-07-04 07:07
 * @Description:
 */
@RestController
@RequestMapping(value = "/weixin/pay")
public class WeixinPayController {
    @Autowired
    private WeixinPayService weixinPayService;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * 二维码创建
     * 普通订单:
     *     exchange:exchange.order
     *     routingkey:queue.order
     * 秒杀订单:
     *     exchange:exchange.seckillorder
     *     routingkey:queue.seckillorder
     *
     *   exchange:routingkey->包装成JSON->打包到attach
     * @param parameterMap
     * @return
     */
    @RequestMapping(value = "/create/native")
    public Result createNative(@RequestParam Map<String,String> parameterMap){

        Map<String,String> resultMap = weixinPayService.createnative(parameterMap);
        return new Result(true, StatusCode.OK,"创建二维码预付订单成功",resultMap);
    }

    /**
     * 微信支付状态查询
     * @param outtradeno
     * @return
     */
    @GetMapping(value = "/status/query")
    public Result queryStatus(String outtradeno){
        Map result = weixinPayService.queryStatus(outtradeno);
        return new Result(true,StatusCode.OK,"查询支付状态成功",result);
    }

    /**
     * 支付回调
     * @param request
     * @return
     */
    @RequestMapping(value = "/notify/url")
    public String notifyUrl(HttpServletRequest request){
        InputStream inputStream ;
        try {
            //读取支付回调数据
            inputStream = request.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer))!=-1){
                outputStream.write(buffer,0,len);
            }
            outputStream.close();
            inputStream.close();
            //将支付回调数据转换成xml字符串
            String result = new String(outputStream.toByteArray(),"UTF-8");
            //将xml字符串转换成Map结构
            Map<String, String> map = WXPayUtil.xmlToMap(result);
            //String respMap = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
            //获取自定义参数
            String attach = map.get("attach");
            Map<String, String> attachMap = JSON.parseObject(attach, Map.class);
            //发送支付结果给MQ
            rabbitTemplate.convertAndSend(attachMap.get("exchange"),attachMap.get("routingkey"), JSON.toJSONString(map));
            //rabbitTemplate.convertAndSend("exchange.order", "queue.order", JSON.toJSONString(map));
            //响应数据设置
            Map respMap = new HashMap();
            respMap.put("return_code","SUCCESS");
            respMap.put("return_msg","OK");
            return WXPayUtil.mapToXml(respMap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
