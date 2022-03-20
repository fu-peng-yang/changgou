package com.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.search.dao.SkuEsMapper;
import com.changgou.search.pojo.SkuInfo;
import com.changgou.search.service.SkuService;
import com.mysql.cj.xdevapi.JsonArray;
import entity.Result;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import springfox.documentation.spring.web.json.Json;

import java.util.*;

/**
 *
 *@ClassName: SkuServiceImpl
 *@Description
 *@Author yang
 *@Date 2020/9/30
 *@Time 9:59*/


@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private SkuEsMapper skuEsMapper;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;//可以实现索引库的增删改查[高级搜索]

    /**
     * 多条件搜索
     *
     * @param searchMap
     * @return
     */


    @Override
    public Map<String, Object> search(Map<String, String> searchMap) {
        //搜索条件封装
        NativeSearchQueryBuilder nativeSearchQueryBuilder = builderBasicQuery(searchMap);

        //集合搜索
        Map<String, Object> resultMap = searchList(nativeSearchQueryBuilder);

        /*if (searchMap==null || StringUtils.isEmpty(searchMap.get("category"))){
            //分类分组查询实现
            List<String> categoryList = searchCategoryList(nativeSearchQueryBuilder);
            resultMap.put("categoryList",categoryList);
        }*/


        /*if (searchMap==null || StringUtils.isEmpty(searchMap.get("brand"))) {
            //品牌分组查询实现
            List<String> brandList = searchBrandList(nativeSearchQueryBuilder);
            resultMap.put("brandList", brandList);
        }*/
        //规格分组查询
        /*Map<String, Set<String>> specMap = searchSpecList(nativeSearchQueryBuilder);
        resultMap.put("specMap",specMap);*/
        Map<String, Object> groupMap = searchGroupList(nativeSearchQueryBuilder, searchMap);
        resultMap.putAll(groupMap);

        return resultMap;

    }

    /**
     * 搜索条件封装
     * @param searchMap
     * @return
     */

    private NativeSearchQueryBuilder builderBasicQuery(Map<String, String> searchMap) {
        //搜索条件构建对象,用于封装各种搜索条件
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (searchMap!=null && searchMap.size()>0){
            //根据关键词搜索
            String keywords = searchMap.get("keywords");
            //如果关键词不为空,则搜索关键词数据
            if (!StringUtils.isEmpty(keywords)){
                //nativeSearchQueryBuilder.withQuery(QueryBuilders.queryStringQuery(keywords).field("name"));
                boolQueryBuilder.must(QueryBuilders.queryStringQuery(keywords).field("name"));
            }
            //输入了->category
            if (!StringUtils.isEmpty(searchMap.get("category"))){
                boolQueryBuilder.must(QueryBuilders.termQuery("categoryName",searchMap.get("category")));
            }
            //输入了->brand
            if (!StringUtils.isEmpty(searchMap.get("brand"))){
                boolQueryBuilder.must(QueryBuilders.termQuery("brandName",searchMap.get("brand")));
            }
            //规格过滤实现:spec_网络=联通2G&spec_颜色=红
            for (Map.Entry<String, String> entry : searchMap.entrySet()) {
                String key = entry.getKey();
                //如果key以spec_开头,则表示规格筛选查询
                if (key.startsWith("spec_")){
                    //String value = entry.getValue();
                    String value = searchMap.get(key).replace("\\","");
                    //spec_网络   spec_ 前五个要去掉
                    boolQueryBuilder.must(QueryBuilders.termQuery("specMap."+key.substring(5)+".keyword",value));
                }
            }
            String price = searchMap.get("price");
            if (!StringUtils.isEmpty(price)){
                String replace = price.replace("元", "").replace("以上", "");
                String[] prices = replace.split("-");
                if (prices!=null && prices.length>0) {
                    boolQueryBuilder.must(QueryBuilders.rangeQuery("price").gt(Integer.parseInt(prices[0])));
                    if (prices.length == 2) {
                        boolQueryBuilder.must(QueryBuilders.rangeQuery("price").lte(Integer.parseInt(prices[1])));
                    }
                }

            }

            //排序规则
            //销量排序规则:月销量或季度销量
            //新品排序规则:根据createTime降序即可
            //评价排序规则:好中差评价累加总和进行排序
            //综合排序规则:根据用户搜索默认的排序规则
            String sortFiled =searchMap.get("sortFiled");//指定排序的域
            String sortRule = searchMap.get("sortRule");//指定排序的规则
            if (!StringUtils.isEmpty(sortFiled)&&!StringUtils.isEmpty(sortRule)){
                nativeSearchQueryBuilder.withSort(new FieldSortBuilder(sortFiled)//排序的域
                        .order(SortOrder.valueOf(sortRule)));//排序的规则
            }



        }
        //分页
        Integer pageNum = 1;
        if (!StringUtils.isEmpty(searchMap.get("pageNum"))) {
            try {
                pageNum = Integer.valueOf(searchMap.get("pageNum"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                pageNum=1;
            }
        }
        Integer pageSize = 30;
        nativeSearchQueryBuilder.withPageable(PageRequest.of(pageNum-1,pageSize));
        //将boolQueryBuilder填充到nativeSearchQueryBuilder
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        return nativeSearchQueryBuilder;
    }


    /**
     * 集合搜索
     * @param nativeSearchQueryBuilder
     * @return
     */

    private Map<String, Object> searchList(NativeSearchQueryBuilder nativeSearchQueryBuilder) {
        //高亮配置
        HighlightBuilder.Field field = new HighlightBuilder.Field("name");//指定高亮域
        //前缀 <em style="color:red;">
        field.preTags("<em style=\"color:red;\">");
        //后缀 </em>
        field.postTags("</em>");
        //碎片长度  关键词数据的长度
        field.fragmentSize(100);
        //添加高亮
        nativeSearchQueryBuilder.withHighlightFields(field);
        /**
         * 执行搜索,响应结果
         * 1)搜索条件封装对象
         * 2)搜索的结果集(集合数据)需要转换的类型
         * 3)AggregatedPage<SkuInfo>:搜索结果集的封装
         */


        AggregatedPage<SkuInfo> skuInfos = elasticsearchTemplate.queryForPage(
                nativeSearchQueryBuilder.build(),//搜索条件封装
                SkuInfo.class,//数据结果集要转换的类型字节码
                new SearchResultMapper() {//执行搜索后将数据结果集封装到该对象中
                    @Override
                    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                        //存储所有转换后的高亮数据对象
                        List<T> list = new ArrayList<>();
                        //执行查询,获取所有数据->结果集[非高亮数据|高亮数据]
                        for (SearchHit hit : response.getHits()) {
                            //分析结果集数据,获取高亮数据
                            SkuInfo skuInfo = JSON.parseObject(hit.getSourceAsString(), SkuInfo.class);
                            //分析结果集数据,获取高亮数据->只有某个域的高亮数据
                            HighlightField highlightField = hit.getHighlightFields().get("name");
                            if (highlightField!=null&& highlightField.getFragments()!=null){
                                //高亮数据读取
                                Text[] fragments = highlightField.getFragments();
                                StringBuffer buffer = new StringBuffer();
                                for (Text fragment : fragments) {
                                    buffer.append(fragment.toString());
                                }
                                //非高亮数据中指定的域替换成高亮数据
                                skuInfo.setName(buffer.toString());
                            }
                            //将高亮数据添加到集合中
                            list.add((T) skuInfo);
                        }

                        //将数据返回
                        /**
                         * 1)搜索的集合数据:(携带高亮)List<T> content
                         * 2)分页对象信息:Pageable pageable
                         * 3)搜索记录的总条数:long total
                         */
                        return new AggregatedPageImpl<T>(list,pageable,response.getHits().getTotalHits());
                    }
                });



        List<SkuInfo> content = skuInfos.getContent();
        //总页数
        int totalPages = skuInfos.getTotalPages();
        //分页参数-总记录数
        long totalElements = skuInfos.getTotalElements();
        //封装一个Map存储所有数据,并返回
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rows", content);
        resultMap.put("total", totalElements);
        resultMap.put("totalPages", totalPages);
        //获取搜索封装信息
        NativeSearchQuery query = nativeSearchQueryBuilder.build();
        Pageable pageable = query.getPageable();
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        //分页数据
        resultMap.put("pageNum",pageNumber);
        resultMap.put("pageSize",pageSize);
        return resultMap;
    }

    /**
     * 分组查询:分类分组 品牌分组  规格分组
     * @param nativeSearchQueryBuilder
     * @return
     */

    private Map<String,Object> searchGroupList(NativeSearchQueryBuilder nativeSearchQueryBuilder,Map<String,String> searchMap) {

        /**
         * 分组查询分类集合
         * .addAggregation():添加一个聚合操作
         * 1)取别名
         * 2)表示根据哪个域进行分组查询
         */


        if (searchMap==null || StringUtils.isEmpty(searchMap.get("category"))) {
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuCategory").field("categoryName"));
        }
        if (searchMap==null || StringUtils.isEmpty(searchMap.get("brand"))) {
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuBrand").field("brandName"));
        }
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuSpec").field("spec.keyword"));
        AggregatedPage<SkuInfo> aggregatedPage = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class);
        /**
         * 获取分组数据
         * aggregatePage.getAggregations():获取的是集合,可以根据多个域进行分组
         * .get("skuCategory"):获取指定域的集合数 [手机,家用电器,手机配件]
         */

        Map<String,Object> groupMapResult = new HashMap<>();
        if (searchMap==null || StringUtils.isEmpty(searchMap.get("category"))) {
            StringTerms cagegoryTerms = aggregatedPage.getAggregations().get("skuCategory");
            List<String> categoryList = getGroupList(cagegoryTerms);
            groupMapResult.put("categoryList",categoryList);
        }
        if (searchMap==null || StringUtils.isEmpty(searchMap.get("brand"))) {
            StringTerms brandTerms = aggregatedPage.getAggregations().get("skuBrand");
            List<String> brandList = getGroupList(brandTerms);
            groupMapResult.put("brandList",brandList);
        }
        StringTerms specTerms = aggregatedPage.getAggregations().get("skuSpec");
        List<String> specList = getGroupList(specTerms);
        Map<String, Set<String>> specMap = putAllSpec(specList);
        groupMapResult.put("specList",specMap);
        return groupMapResult;
    }

    /**
     * 获取分组集合数据
     * @param stringTerms
     * @return
     */
    public List<String> getGroupList(StringTerms stringTerms){
        List<String> groupList = new ArrayList<>();
        for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
            String fieldName = bucket.getKeyAsString();//其中的一个域名字
            groupList.add(fieldName);
        }
        return  groupList;
    }
    /**
     * 分类分组查询
     * @param nativeSearchQueryBuilder
     * @return
     */

    /*private List<String> searchCategoryList(NativeSearchQueryBuilder nativeSearchQueryBuilder) {

     *//**
     * 分组查询分类集合
     * .addAggregation():添加一个聚合操作
     * 1)取别名
     * 2)表示根据哪个域进行分组查询
     *//*


        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuCategory").field("categoryName"));

        AggregatedPage<SkuInfo> aggregatedPage = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class);
        *//**
     * 获取分组数据
     * aggregatePage.getAggregations():获取的是集合,可以根据多个域进行分组
     * .get("skuCategory"):获取指定域的集合数 [手机,家用电器,手机配件]
     *//*

        StringTerms stringTerms = aggregatedPage.getAggregations().get("skuCategory");
        List<String> categoryList = new ArrayList<>();
        for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
            String categoryName = bucket.getKeyAsString();//其中的一个分类名字
            categoryList.add(categoryName);
        }
        return categoryList;
    }*/

    /**
     * 品牌分组查询
     * @param nativeSearchQueryBuilder
     * @return
     */

    /*private List<String> searchBrandList(NativeSearchQueryBuilder nativeSearchQueryBuilder) {

     *//**
     * 品牌查询分类集合
     * .addAggregation():添加一个聚合操作
     * 1)取别名
     * 2)表示根据哪个域进行分组查询
     *//*


        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuBrand").field("brandName"));

        AggregatedPage<SkuInfo> aggregatedPage = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class);
        *//**
     * 获取分组数据
     * aggregatePage.getAggregations():获取的是集合,可以根据多个域进行分组
     * .get("skuBrand"):获取指定域的集合数 [华为,小米,中兴]
     *//*

        StringTerms stringTerms = aggregatedPage.getAggregations().get("skuBrand");
        List<String> brandList = new ArrayList<>();
        for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
            String brandName = bucket.getKeyAsString();//其中的一个品牌名字
            brandList.add(brandName);
        }
        return brandList;
    }*/

    /**
     * 规格分组查询
     * @param nativeSearchQueryBuilder
     * @return
     */

    /*private Map<String, Set<String>> searchSpecList(NativeSearchQueryBuilder nativeSearchQueryBuilder) {
     *//**
     * 规格查询分类集合
     * .addAggregation():添加一个聚合操作
     * 1)取别名
     * 2)表示根据哪个域进行分组查询
     *//*


        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuSpec").field("spec.keyword").size(10000));

        AggregatedPage<SkuInfo> aggregatedPage = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class);
         *//**
     * 获取分组数据
     * aggregatePage.getAggregations():获取的是集合,可以根据多个域进行分组
     * .get("skuSpec"):获取指定域的集合数 [{"电视音响效果":"小影院","电视屏幕尺寸":"20英寸","尺码":"165"},{"电视音响效果":"小影院","电视屏幕尺寸":"20英寸","尺码":"165"},{"电视音响效果":"小影院","电视屏幕尺寸":"20英寸","尺码":"165"}]
     *//*

        StringTerms stringTerms = aggregatedPage.getAggregations().get("skuSpec");
        List<String> specList = new ArrayList<String>();
        for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
            String specName = bucket.getKeyAsString();//其中的一个规格名字
            specList.add(specName);
        }

        //规格汇总
        Map<String, Set<String>> allSpec = putAllSpec(specList);
        return allSpec;
    }*/

    /**
     * 规格汇总
     * @param specList
     * @return
     */
    private Map<String, Set<String>> putAllSpec(List<String> specList) {
        Map<String,Set<String>> allSpec = new HashMap<String, Set<String>>();
        //1.循环specList
        for (String spec : specList) {
            //2.将每个JSON字符串转成Map
            Map<String,String> specMap = JSON.parseObject(spec, Map.class);
            //3.将每个Map对象合并成一个Map<String,Set<String>>
            for (Map.Entry<String, String> specEntry : specMap.entrySet()) {
                String key = specEntry.getKey();//规格名字
                String value = specEntry.getValue();//规格选项值
                //获取当前规格名字对应的规格数据
                Set<String> specSet = allSpec.get(key);
                if (specSet == null){
                    specSet = new HashSet<String>();
                }
                //将当前规格加入到集合中
                specSet.add(value);
                //将数据存入到allSpec中
                allSpec.put(key,specSet);
            }
        }
        return allSpec;
    }

    /**
     * 导入索引库
     */

    @Override
    public void importData() {
        //Feigin调用,查询List<Sku>

        Result<List<Sku>> skuList = skuFeign.findAll();
        //将List<Sku>转成List<SkuInfo>
        List<SkuInfo>  skuInfoList = JSON.parseArray(JSON.toJSONString(skuList.getData()),SkuInfo.class);

        //循环当前skuInfoList
        for (SkuInfo skuInfo : skuInfoList) {
            //获取spec -> Map(String) ->Map类型  {"电视音响效果":"小影院","电视屏幕尺寸":"20英寸","尺码":"164"}
            Map<String, Object> specMap = JSON.parseObject(skuInfo.getSpec(), Map.class);
            //如果需要生成动态的域,只需要将该域存入到一个Map<String,Object>对象中即可,该Map<String,Object>的key会生成一个域,域的名字为该Map的key
            //当前Map<String,Object>后面Object的值会作为当前Sku对象该域(key)对应的值
            skuInfo.setSpecMap(specMap);
        }
        //调用Dao实现数据批量导入
        skuEsMapper.saveAll(skuInfoList);
    }
}
