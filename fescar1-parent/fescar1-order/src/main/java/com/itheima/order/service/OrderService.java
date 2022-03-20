package com.itheima.order.service;

import com.itheima.feign.ItemInfoFeign;
import com.itheima.order.dao.OrderInfoMapper;
import com.itheima.order.feign.UserFeignClient;
import com.itheima.pojo.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.itheima.order.service *
 * @since 1.0
 */

public interface OrderService {
    public  void  add(String username,int id,int count);
    public void create(String userId, String commodityCode, Integer count);
}
