package com.changgou.goods.pojo;

import java.io.Serializable;
import java.util.List;

/**
* 商品
*@ClassName: Goods
*@Description
*@Author yang
*@Date 2020/9/23
*@Time 19:27
*/
public class Goods implements Serializable {

    private Spu spu;//Spu信息
    private List<Sku> skuList;//Sku集合信息
    private Brand brand;
    private  Category category;

    public Spu getSpu() {
        return spu;
    }

    public void setSpu(Spu spu) {
        this.spu = spu;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "spu=" + spu +
                ", skuList=" + skuList +
                ", brand=" + brand +
                ", category=" + category +
                '}';
    }
}
