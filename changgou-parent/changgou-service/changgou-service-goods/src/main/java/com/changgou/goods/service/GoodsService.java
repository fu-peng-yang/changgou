package com.changgou.goods.service;

import com.changgou.goods.pojo.Spu;
import com.github.pagehelper.PageInfo;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.goods.service
 * @Author: yang
 * @CreateTime: 2020-11-17 20:14
 * @Description: 商品服务层
 */
public interface GoodsService {
    /**
     * 分页显示商品列表
     * @return
     */
    PageInfo<Spu> findPage(int page,int size);

    /**
     * 根据商品名称/货号分页显示商品列表
     * @param name
     * @param sname
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spu> findPageByExample(Integer page,Integer size,String name,String sn);
}
