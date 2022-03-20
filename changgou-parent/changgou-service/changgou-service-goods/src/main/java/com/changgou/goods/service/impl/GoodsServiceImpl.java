package com.changgou.goods.service.impl;

import com.changgou.goods.dao.SpuMapper;
import com.changgou.goods.pojo.Spu;
import com.changgou.goods.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.goods.service.impl
 * @Author: yang
 * @CreateTime: 2020-11-17 20:16
 * @Description:
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SpuMapper spuMapper;


    /**
     * 分页显示商品列表
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Spu> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        List<Spu> spuList = spuMapper.findSpuList();
        return new PageInfo<Spu>(spuList);
    }

    /**
     * 根据商品名称/货号 分页显示商品列表
     * @param name
     * @param sname
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Spu> findPageByExample(Integer page, Integer size,String name, String sn) {
        /*PageHelper.startPage(page,size);*/
        List<Spu> spusByExample = spuMapper.findByExample(page, size, name, sn);
        return new PageInfo<>(spusByExample);
    }
}
