package com.changgou.search.service;

import org.springframework.stereotype.Repository;

import java.util.Map;
/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.search.service *
 * @since 1.0
 */
public interface SkuService {

    /**
     * 多条件搜索
     * @param searchMap
     * @return
     */
    Map<String,Object> search(Map<String,String> searchMap);
    /**
     * 导入索引库
     */
    void importData();
}
