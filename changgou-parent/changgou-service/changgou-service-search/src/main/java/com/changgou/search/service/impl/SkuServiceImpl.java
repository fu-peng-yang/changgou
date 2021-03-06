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
    private ElasticsearchTemplate elasticsearchTemplate;//????????????????????????????????????[????????????]

    /**
     * ???????????????
     *
     * @param searchMap
     * @return
     */


    @Override
    public Map<String, Object> search(Map<String, String> searchMap) {
        //??????????????????
        NativeSearchQueryBuilder nativeSearchQueryBuilder = builderBasicQuery(searchMap);

        //????????????
        Map<String, Object> resultMap = searchList(nativeSearchQueryBuilder);

        /*if (searchMap==null || StringUtils.isEmpty(searchMap.get("category"))){
            //????????????????????????
            List<String> categoryList = searchCategoryList(nativeSearchQueryBuilder);
            resultMap.put("categoryList",categoryList);
        }*/


        /*if (searchMap==null || StringUtils.isEmpty(searchMap.get("brand"))) {
            //????????????????????????
            List<String> brandList = searchBrandList(nativeSearchQueryBuilder);
            resultMap.put("brandList", brandList);
        }*/
        //??????????????????
        /*Map<String, Set<String>> specMap = searchSpecList(nativeSearchQueryBuilder);
        resultMap.put("specMap",specMap);*/
        Map<String, Object> groupMap = searchGroupList(nativeSearchQueryBuilder, searchMap);
        resultMap.putAll(groupMap);

        return resultMap;

    }

    /**
     * ??????????????????
     * @param searchMap
     * @return
     */

    private NativeSearchQueryBuilder builderBasicQuery(Map<String, String> searchMap) {
        //????????????????????????,??????????????????????????????
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (searchMap!=null && searchMap.size()>0){
            //?????????????????????
            String keywords = searchMap.get("keywords");
            //????????????????????????,????????????????????????
            if (!StringUtils.isEmpty(keywords)){
                //nativeSearchQueryBuilder.withQuery(QueryBuilders.queryStringQuery(keywords).field("name"));
                boolQueryBuilder.must(QueryBuilders.queryStringQuery(keywords).field("name"));
            }
            //?????????->category
            if (!StringUtils.isEmpty(searchMap.get("category"))){
                boolQueryBuilder.must(QueryBuilders.termQuery("categoryName",searchMap.get("category")));
            }
            //?????????->brand
            if (!StringUtils.isEmpty(searchMap.get("brand"))){
                boolQueryBuilder.must(QueryBuilders.termQuery("brandName",searchMap.get("brand")));
            }
            //??????????????????:spec_??????=??????2G&spec_??????=???
            for (Map.Entry<String, String> entry : searchMap.entrySet()) {
                String key = entry.getKey();
                //??????key???spec_??????,???????????????????????????
                if (key.startsWith("spec_")){
                    //String value = entry.getValue();
                    String value = searchMap.get(key).replace("\\","");
                    //spec_??????   spec_ ??????????????????
                    boolQueryBuilder.must(QueryBuilders.termQuery("specMap."+key.substring(5)+".keyword",value));
                }
            }
            String price = searchMap.get("price");
            if (!StringUtils.isEmpty(price)){
                String replace = price.replace("???", "").replace("??????", "");
                String[] prices = replace.split("-");
                if (prices!=null && prices.length>0) {
                    boolQueryBuilder.must(QueryBuilders.rangeQuery("price").gt(Integer.parseInt(prices[0])));
                    if (prices.length == 2) {
                        boolQueryBuilder.must(QueryBuilders.rangeQuery("price").lte(Integer.parseInt(prices[1])));
                    }
                }

            }

            //????????????
            //??????????????????:????????????????????????
            //??????????????????:??????createTime????????????
            //??????????????????:???????????????????????????????????????
            //??????????????????:???????????????????????????????????????
            String sortFiled =searchMap.get("sortFiled");//??????????????????
            String sortRule = searchMap.get("sortRule");//?????????????????????
            if (!StringUtils.isEmpty(sortFiled)&&!StringUtils.isEmpty(sortRule)){
                nativeSearchQueryBuilder.withSort(new FieldSortBuilder(sortFiled)//????????????
                        .order(SortOrder.valueOf(sortRule)));//???????????????
            }



        }
        //??????
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
        //???boolQueryBuilder?????????nativeSearchQueryBuilder
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        return nativeSearchQueryBuilder;
    }


    /**
     * ????????????
     * @param nativeSearchQueryBuilder
     * @return
     */

    private Map<String, Object> searchList(NativeSearchQueryBuilder nativeSearchQueryBuilder) {
        //????????????
        HighlightBuilder.Field field = new HighlightBuilder.Field("name");//???????????????
        //?????? <em style="color:red;">
        field.preTags("<em style=\"color:red;\">");
        //?????? </em>
        field.postTags("</em>");
        //????????????  ????????????????????????
        field.fragmentSize(100);
        //????????????
        nativeSearchQueryBuilder.withHighlightFields(field);
        /**
         * ????????????,????????????
         * 1)????????????????????????
         * 2)??????????????????(????????????)?????????????????????
         * 3)AggregatedPage<SkuInfo>:????????????????????????
         */


        AggregatedPage<SkuInfo> skuInfos = elasticsearchTemplate.queryForPage(
                nativeSearchQueryBuilder.build(),//??????????????????
                SkuInfo.class,//??????????????????????????????????????????
                new SearchResultMapper() {//??????????????????????????????????????????????????????
                    @Override
                    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                        //??????????????????????????????????????????
                        List<T> list = new ArrayList<>();
                        //????????????,??????????????????->?????????[???????????????|????????????]
                        for (SearchHit hit : response.getHits()) {
                            //?????????????????????,??????????????????
                            SkuInfo skuInfo = JSON.parseObject(hit.getSourceAsString(), SkuInfo.class);
                            //?????????????????????,??????????????????->??????????????????????????????
                            HighlightField highlightField = hit.getHighlightFields().get("name");
                            if (highlightField!=null&& highlightField.getFragments()!=null){
                                //??????????????????
                                Text[] fragments = highlightField.getFragments();
                                StringBuffer buffer = new StringBuffer();
                                for (Text fragment : fragments) {
                                    buffer.append(fragment.toString());
                                }
                                //???????????????????????????????????????????????????
                                skuInfo.setName(buffer.toString());
                            }
                            //?????????????????????????????????
                            list.add((T) skuInfo);
                        }

                        //???????????????
                        /**
                         * 1)?????????????????????:(????????????)List<T> content
                         * 2)??????????????????:Pageable pageable
                         * 3)????????????????????????:long total
                         */
                        return new AggregatedPageImpl<T>(list,pageable,response.getHits().getTotalHits());
                    }
                });



        List<SkuInfo> content = skuInfos.getContent();
        //?????????
        int totalPages = skuInfos.getTotalPages();
        //????????????-????????????
        long totalElements = skuInfos.getTotalElements();
        //????????????Map??????????????????,?????????
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rows", content);
        resultMap.put("total", totalElements);
        resultMap.put("totalPages", totalPages);
        //????????????????????????
        NativeSearchQuery query = nativeSearchQueryBuilder.build();
        Pageable pageable = query.getPageable();
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        //????????????
        resultMap.put("pageNum",pageNumber);
        resultMap.put("pageSize",pageSize);
        return resultMap;
    }

    /**
     * ????????????:???????????? ????????????  ????????????
     * @param nativeSearchQueryBuilder
     * @return
     */

    private Map<String,Object> searchGroupList(NativeSearchQueryBuilder nativeSearchQueryBuilder,Map<String,String> searchMap) {

        /**
         * ????????????????????????
         * .addAggregation():????????????????????????
         * 1)?????????
         * 2)???????????????????????????????????????
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
         * ??????????????????
         * aggregatePage.getAggregations():??????????????????,?????????????????????????????????
         * .get("skuCategory"):??????????????????????????? [??????,????????????,????????????]
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
     * ????????????????????????
     * @param stringTerms
     * @return
     */
    public List<String> getGroupList(StringTerms stringTerms){
        List<String> groupList = new ArrayList<>();
        for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
            String fieldName = bucket.getKeyAsString();//????????????????????????
            groupList.add(fieldName);
        }
        return  groupList;
    }
    /**
     * ??????????????????
     * @param nativeSearchQueryBuilder
     * @return
     */

    /*private List<String> searchCategoryList(NativeSearchQueryBuilder nativeSearchQueryBuilder) {

     *//**
     * ????????????????????????
     * .addAggregation():????????????????????????
     * 1)?????????
     * 2)???????????????????????????????????????
     *//*


        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuCategory").field("categoryName"));

        AggregatedPage<SkuInfo> aggregatedPage = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class);
        *//**
     * ??????????????????
     * aggregatePage.getAggregations():??????????????????,?????????????????????????????????
     * .get("skuCategory"):??????????????????????????? [??????,????????????,????????????]
     *//*

        StringTerms stringTerms = aggregatedPage.getAggregations().get("skuCategory");
        List<String> categoryList = new ArrayList<>();
        for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
            String categoryName = bucket.getKeyAsString();//???????????????????????????
            categoryList.add(categoryName);
        }
        return categoryList;
    }*/

    /**
     * ??????????????????
     * @param nativeSearchQueryBuilder
     * @return
     */

    /*private List<String> searchBrandList(NativeSearchQueryBuilder nativeSearchQueryBuilder) {

     *//**
     * ????????????????????????
     * .addAggregation():????????????????????????
     * 1)?????????
     * 2)???????????????????????????????????????
     *//*


        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuBrand").field("brandName"));

        AggregatedPage<SkuInfo> aggregatedPage = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class);
        *//**
     * ??????????????????
     * aggregatePage.getAggregations():??????????????????,?????????????????????????????????
     * .get("skuBrand"):??????????????????????????? [??????,??????,??????]
     *//*

        StringTerms stringTerms = aggregatedPage.getAggregations().get("skuBrand");
        List<String> brandList = new ArrayList<>();
        for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
            String brandName = bucket.getKeyAsString();//???????????????????????????
            brandList.add(brandName);
        }
        return brandList;
    }*/

    /**
     * ??????????????????
     * @param nativeSearchQueryBuilder
     * @return
     */

    /*private Map<String, Set<String>> searchSpecList(NativeSearchQueryBuilder nativeSearchQueryBuilder) {
     *//**
     * ????????????????????????
     * .addAggregation():????????????????????????
     * 1)?????????
     * 2)???????????????????????????????????????
     *//*


        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuSpec").field("spec.keyword").size(10000));

        AggregatedPage<SkuInfo> aggregatedPage = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class);
         *//**
     * ??????????????????
     * aggregatePage.getAggregations():??????????????????,?????????????????????????????????
     * .get("skuSpec"):??????????????????????????? [{"??????????????????":"?????????","??????????????????":"20??????","??????":"165"},{"??????????????????":"?????????","??????????????????":"20??????","??????":"165"},{"??????????????????":"?????????","??????????????????":"20??????","??????":"165"}]
     *//*

        StringTerms stringTerms = aggregatedPage.getAggregations().get("skuSpec");
        List<String> specList = new ArrayList<String>();
        for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
            String specName = bucket.getKeyAsString();//???????????????????????????
            specList.add(specName);
        }

        //????????????
        Map<String, Set<String>> allSpec = putAllSpec(specList);
        return allSpec;
    }*/

    /**
     * ????????????
     * @param specList
     * @return
     */
    private Map<String, Set<String>> putAllSpec(List<String> specList) {
        Map<String,Set<String>> allSpec = new HashMap<String, Set<String>>();
        //1.??????specList
        for (String spec : specList) {
            //2.?????????JSON???????????????Map
            Map<String,String> specMap = JSON.parseObject(spec, Map.class);
            //3.?????????Map?????????????????????Map<String,Set<String>>
            for (Map.Entry<String, String> specEntry : specMap.entrySet()) {
                String key = specEntry.getKey();//????????????
                String value = specEntry.getValue();//???????????????
                //?????????????????????????????????????????????
                Set<String> specSet = allSpec.get(key);
                if (specSet == null){
                    specSet = new HashSet<String>();
                }
                //?????????????????????????????????
                specSet.add(value);
                //??????????????????allSpec???
                allSpec.put(key,specSet);
            }
        }
        return allSpec;
    }

    /**
     * ???????????????
     */

    @Override
    public void importData() {
        //Feigin??????,??????List<Sku>

        Result<List<Sku>> skuList = skuFeign.findAll();
        //???List<Sku>??????List<SkuInfo>
        List<SkuInfo>  skuInfoList = JSON.parseArray(JSON.toJSONString(skuList.getData()),SkuInfo.class);

        //????????????skuInfoList
        for (SkuInfo skuInfo : skuInfoList) {
            //??????spec -> Map(String) ->Map??????  {"??????????????????":"?????????","??????????????????":"20??????","??????":"164"}
            Map<String, Object> specMap = JSON.parseObject(skuInfo.getSpec(), Map.class);
            //??????????????????????????????,?????????????????????????????????Map<String,Object>???????????????,???Map<String,Object>???key??????????????????,??????????????????Map???key
            //??????Map<String,Object>??????Object?????????????????????Sku????????????(key)????????????
            skuInfo.setSpecMap(specMap);
        }
        //??????Dao????????????????????????
        skuEsMapper.saveAll(skuInfoList);
    }
}
