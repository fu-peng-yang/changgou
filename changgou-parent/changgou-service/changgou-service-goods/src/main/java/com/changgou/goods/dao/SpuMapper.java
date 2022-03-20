package com.changgou.goods.dao;

import com.changgou.goods.pojo.Spu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/****
 * @Author:admin
 * @Description:Spu的Dao
 * @Date 2019/6/14 0:12
 *****/
public interface SpuMapper extends Mapper<Spu> {
    /**
     * 查询spu商品列表
     * @return
     */
    @Select("SELECT s.*,b.`name` as sname FROM tb_spu s,tb_brand b where s.brand_id = b.id")
    List<Spu> findSpuList();

    @Select({"<script> SELECT tb_spu.name as `name`,tb_spu.sn as `sn`,tb_brand.name as `sname` FROM tb_spu " +
            "LEFT JOIN tb_brand  on tb_spu.brand_id = tb_brand.id   " +
            "<where> " +
            "<if test='name != null'> " +
            "and tb_spu.`name` LIKE #{name} " +
            "</if> " +
            "<if test=' sn != null'> " +
            "and tb_spu.`sn` = #{sn}" +
            "</if> " +
            "</where>" +
            "LIMIT #{page},#{size}" +
            "</script>"})
    List<Spu> findByExample(@Param("page")Integer page,@Param("size")Integer size,@Param("name") String name, @Param("sn") String sn);

    /**
     * 统计已上架数量
     * @return
     */
    @Select("SELECT COUNT(is_marketable) FROM tb_spu WHERE is_marketable ='1'")
    int countByIsMarketable();

    /**
     * 统计未上架数量
     * @return
     */
    @Select("SELECT COUNT(is_marketable) FROM tb_spu WHERE is_marketable ='0'")
    int countByIsNotMarketable();

    /**
     * 统计待审核商品数量
     * @return
     */
    @Select("SELECT COUNT(status) FROM tb_spu WHERE `status` ='0'")
    int countByWaitStatus();

    /**
     * 统计未通过商品数量
     * @return
     */
    @Select("SELECT COUNT(status) FROM tb_spu WHERE `status` ='2'")
    int countByNotStatus();
}
