package com.changgou.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.service.WeixinPayService;
import com.github.wxpay.sdk.WXPayUtil;
import entity.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.service.impl
 * @Author: yang
 * @CreateTime: 2021-07-04 07:06
 * @Description:
 */
@Service
public class WeixinPayServiceImpl implements WeixinPayService {
    //用户ID
    @Value("${weixin.appid}")
    private String appid;
    //商户ID
    @Value("${weixin.partner}")
    private String partner;
    //密钥
    @Value("${weixin.partnerkey}")
    private String partnerkey;
    //回调地址
    @Value("${weixin.notifyurl}")
    private String notifyurl;
    /**
     * 获取二维码操作
     * @param parameterMap
     * @return
     */
    @Override
    public Map createnative(Map<String, String> parameterMap) {
        try {
            //参数
            Map<String,String> paramMap = new HashMap<String, String>();
            paramMap.put("appid",appid);
            paramMap.put("mch_id",partner);
            //随机字符串
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            paramMap.put("body", "商品描述:商品不错");
            //订单号
            paramMap.put("out_trade_no", parameterMap.get("outtradeno"));
            //交易金额,单位:分
            paramMap.put("total_fee",parameterMap.get("totalfee"));
            //终端ID  机器号
            paramMap.put("spbill_create_ip","127.0.0.1");
            //交易结果回调通知地址
            paramMap.put("notify_url",notifyurl);
            //交易类型
            paramMap.put("trade_type","NATIVE");

            //获取自定义数据
            String exchange = parameterMap.get("exchange");
            String routingkey = parameterMap.get("routingkey");
            Map<String,String> attachMap = new HashMap<String, String>();
            attachMap.put("exchange",exchange);
            attachMap.put("routingkey", routingkey);
            //如果是秒杀订单,需要传username
            String username = parameterMap.get("username");
            if (!StringUtils.isEmpty(username)){
                attachMap.put("username",username);
            }
            String attach = JSON.toJSONString(attachMap);
            parameterMap.put("attach",attach);
            //Map转成XML字符串,可以携带签名
            String xmlparameters = WXPayUtil.generateSignedXml(paramMap, partnerkey);
            //URL地址
            String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
            HttpClient httpClient = new HttpClient(url);
            //提交方式
            httpClient.setHttps(true);
            //提交参数
            httpClient.setXmlParam(xmlparameters);
            //执行请求
            httpClient.post();
            //获取返回的数据
            String result = httpClient.getContent();
            //返回数据转成Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 查询微信支付状态
     * @param outtradeno
     * @return
     */
    @Override
    public Map queryStatus(String outtradeno) {
        try {
            //参数
            Map<String,String> paramMap = new HashMap<String, String>();
            paramMap.put("appid",appid);
            paramMap.put("mch_id",partner);
            //随机字符串
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            //订单号
            paramMap.put("out_trade_no", outtradeno);
            //Map转成XML字符串,可以携带签名
            String xmlparameters = WXPayUtil.generateSignedXml(paramMap, partnerkey);
            //URL地址
            String url = "https://api.mch.weixin.qq.com/pay/orderquery";
            HttpClient httpClient = new HttpClient(url);
            //提交方式
            httpClient.setHttps(true);
            //提交参数
            httpClient.setXmlParam(xmlparameters);
            //执行请求
            httpClient.post();
            //获取返回的数据
            String result = httpClient.getContent();
            //返回数据转成Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
