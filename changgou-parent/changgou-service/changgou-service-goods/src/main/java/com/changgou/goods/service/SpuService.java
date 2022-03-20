package com.changgou.goods.service;

import com.changgou.goods.pojo.Goods;
import com.changgou.goods.pojo.Spu;
import com.github.pagehelper.PageInfo;

import java.util.List;

/****
 * @Author:admin
 * @Description:Spu业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface SpuService {

    /**
     * 逻辑删除
     * @param id
     */
     void logicDeleteSpu(Long id);

    /**
     * 找回商品
     * @param id
     */
     void restoreSpu(Long id);
     /**
     * 批量上架
     * @param ids[]  要上架的所有商品ID(spuid)
     */
    int putMany(Long[] ids);

    /**
     * 商品上架
     * @param spuId
     */
    void put(Long spuId);
    /**
     * 商品下架
     * @param spuId
     */
    void pull(Long spuId);
    /**
     * 商品审核
     * @param spuId
     */
    void audit(Long spuId);
    /**
     * 根据Id查询Goods数据
     * @param id
     * @return
     */
    public Goods findGoodsById(Long id);

    /**
     * 添加商品信息
     * @param goods
     */
    public void saveOrUpdateGoods(Goods goods);
    /***
     * Spu多条件分页查询
     * @param spu
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spu> findPage(Spu spu, int page, int size);

    /***
     * Spu分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spu> findPage(int page, int size);

    /***
     * Spu多条件搜索方法
     * @param spu
     * @return
     */
    List<Spu> findList(Spu spu);

    /***
     * 删除Spu
     * @param id
     */
    void delete(Long id);

    /***
     * 修改Spu数据
     * @param spu
     */
    void update(Spu spu);

    /***
     * 新增Spu
     * @param spu
     */
    void add(Spu spu);

    /**
     * 根据ID查询Spu
     * @param id
     * @return
     */
     Spu findById(Long id);

    /***
     * 查询所有Spu
     * @return
     */
    List<Spu> findAll();


    /**
     * 统计商品数量
     * @param spu
     * @return
     */
    int spuCount(Spu spu);

    /**
     * 统计已上架商品数量
     * @return
     */
    int spuCountIsMarketable();

    /**
     * 统计未上架商品数量
     * @return
     */
    int spuCountIsNotMarketable();

    /**
     * 统计待审核商品数量
     * @return
     */
    int selectCountByWaitStatus();

    /**
     * 统计未通过商品数量
     * @return
     */
    int selectCountByNotStatus();
}
