package com.changgou.item.service;

public interface PageService {
    /**
     * 根据商品Id生成静态页
     * @param spuId
     */
    void createPageHtml(Long spuId);
}
