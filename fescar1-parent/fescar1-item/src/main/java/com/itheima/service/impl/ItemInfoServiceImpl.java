package com.itheima.service.impl;

import com.itheima.dao.ItemInfoMapper;
import com.itheima.pojo.ItemInfo;
import com.itheima.service.ItemInfoService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemInfoServiceImpl implements ItemInfoService {

    @Autowired
    private ItemInfoMapper itemInfoMapper;
    /**
     * 库存递减
     * @param id
     * @param count
     */
    @Override
    public void decrCount(int id, int count) {
        //查询商品信息
        ItemInfo itemInfo = itemInfoMapper.selectByPrimaryKey(id);
        itemInfo.setCount(itemInfo.getCount()-count);
        int dcount = itemInfoMapper.updateByPrimaryKeySelective(itemInfo);
        System.out.println("库存递减受影响行数:" + dcount);
        //异常准备回滚
        //int q = 10/0;
    }
}
