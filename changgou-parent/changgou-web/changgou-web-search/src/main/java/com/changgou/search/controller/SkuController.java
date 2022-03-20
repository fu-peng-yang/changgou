package com.changgou.search.controller;


import com.changgou.search.feign.SkuFeign;
import com.changgou.search.pojo.SkuInfo;
import entity.Page;
import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
*
*@ClassName: SkuController
*@Description
*@Author yang
*@Date 2020/9/30
*@Time 8:40
*/
@Controller
@RequestMapping(value = "/search")
public class SkuController {


    @Autowired
    private SkuFeign skuFeign;

    @RequestMapping("view")
    public String show(){
        return "ssss";
    }
    /**
     * 搜索
     *
     * @param searchMap
     * @param model
     * @return
     */
    @GetMapping(value = "/list")
    public String search(@RequestParam(required = false) Map<String, String> searchMap, Model model) {
       //替换特殊字符
        handlerSearchMap(searchMap);

        //调用搜索微服务
        Map<String, Object> resultMap = skuFeign.search(searchMap);
        model.addAttribute("result", resultMap);
        //计算分页
        Page<SkuInfo> pageInfo = new Page<SkuInfo>(
                Long.parseLong(resultMap.get("total").toString()),
                Integer.parseInt(resultMap.get("pageNum").toString())+1,
                Integer.parseInt(resultMap.get("pageSize").toString())
        );
        model.addAttribute("pageInfo",pageInfo);
        //将条件存储,用于页面回显数据
        model.addAttribute("searchMap", searchMap);
        //获取上次请求地址
        //2个url  url:不带排序参数   url:带排序参数
        String[] urls = url(searchMap);
        model.addAttribute("url", urls[0]);
        model.addAttribute("sorturl", urls[1]);
        return "search";
    }

    /**
     * 拼接组装用户请求的URL地址
     * 获取用户每次请求的地址
     * 页面需要在这次请求的地址上面添加额外的搜索条件
     * http://localhost:18086/search/list
     * http://localhost:18086/search/list?keywords=华为
     * http://localhost:18086/search/list?keywords=华为&brand=华为
     * http://localhost:18086/search/list?keywords=华为&brand=华为&catogory=语言文字
     * @return
     */
    private String[] url(Map<String,String > searchMap){

        String url = "/search/list";  //初始化地址
        String sorturl = "/search/list";          //排序地址
        if (searchMap != null && searchMap.size()>0){
            url += "?";
            sorturl += "?";
            for (Map.Entry<String, String> entry : searchMap.entrySet()) {
                //key是搜索的条件对象
                String key = entry.getKey();
                //跳过分页参数
                if (key.equalsIgnoreCase("pageNum")){
                    continue;
                }
                //value搜索的值
                String value = entry.getValue();
                url += key+"="+value+"&";

                //排序参数,跳过
                if (key.equalsIgnoreCase("sortFiled") || key.equalsIgnoreCase("sortRule")){
                    continue;
                }
                sorturl += key+"="+value+"&";
            }
            //去掉最后一个&
            url = url.substring(0,url.length()-1);
            sorturl = sorturl.substring(0,sorturl.length()-1);
        }
        return new String[]{url,sorturl};
    }

    /**
     * 替换特殊字符
     * @param searchMap
     */
    public void handlerSearchMap(Map<String,String> searchMap){
        if (searchMap!=null){
            for(Map.Entry<String,String> entry:searchMap.entrySet()){
                if (entry.getKey().startsWith("spec_")){
                    entry.setValue(entry.getValue().replace("+","%2B"));
                }
            }
        }
    }
















}
