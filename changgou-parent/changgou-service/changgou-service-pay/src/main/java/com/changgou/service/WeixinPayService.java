package com.changgou.service;

import java.util.Map;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.service
 * @Author: Administrator
 * @CreateTime: 2021-07-04 07:07
 * @Description:
 */
public interface WeixinPayService {
    /**
     * 创建二维码操作
     *
     * @param parameterMap
     * @return
     */
    Map createnative(Map<String, String> parameterMap);

    /**
     * 查询微信支付状态
     * @param outtradeno
     * @return
     */
    Map queryStatus(String outtradeno);
}
