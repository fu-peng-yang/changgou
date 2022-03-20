package com.changgou.canal;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.changgou.content.feign.ContentFeign;
import com.changgou.content.pojo.Content;
import com.changgou.item.feign.PageFeign;
import com.xpand.starter.canal.annotation.*;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * @ClassName: CanalDataEventListener
 * @Description
 * @Author yang
 * @Date 2020/9/24
 * @Time 19:48
 * 实现mysql数据监听
 */

@CanalEventListener
public class CanalDataEventListener {
    @Autowired
    private ContentFeign contentFeign;
    @Autowired
    private PageFeign pageFeign;
    //字符串
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * @InsertListenPoint:增加监听 只有增加后的数据
     * rowData.getBeforeColumnList() 删除 修改
     * rowData.getAfterColumnList() 增加 修改
     * @param entryType :当前操作的类型 增加数据
     * @param rowData :发生变更的一行数据
     */
    @InsertListenPoint
    public void onEventInsert(CanalEntry.EntryType entryType, CanalEntry.RowData rowData){

        for (CanalEntry.Column column: rowData.getAfterColumnsList()) {

            System.out.println("列名"+column.getName()+"-------变更的数据"+column.getValue());
        }
    }

    /**
     * 修改监听
     * @param entryType
     * @param rowData
     */
    @UpdateListenPoint
    public void onEventUpdate(CanalEntry.EntryType entryType,CanalEntry.RowData rowData){

        for (CanalEntry.Column column: rowData.getBeforeColumnsList()) {

            System.out.println("修改前列名"+column.getName()+"-------变更的数据"+column.getValue());
        }
        for (CanalEntry.Column column: rowData.getAfterColumnsList()) {

            System.out.println("修改后列名"+column.getName()+"-------变更的数据"+column.getValue());
        }
    }

    /**
     * 删除监听
     * @param entryType
     * @param rowData
     */
    @DeleteListenPoint
    public void onEventDelete(CanalEntry.EntryType entryType,CanalEntry.RowData rowData){

        for (CanalEntry.Column column: rowData.getBeforeColumnsList()) {

            System.out.println("删除前列名"+column.getName()+"-------变更的数据"+column.getValue());
        }
    }

    /**
     * 自定义监听
     * rowData.getBeforeColumnList() 删除 修改
     * rowData.getAfterColumnList() 增加 修改
     * @param entryType :当前操作的类型 增加数据
     * @param rowData :发生变更的一行数据
     */
    @ListenPoint(
            eventType={CanalEntry.EventType.DELETE,CanalEntry.EventType.UPDATE}, //监听类型
            schema={"changgou_content"}, //监听的数据
            table={"tb_content"},    //监听的表
            destination = "example" //监听实例的地址 指定某一个目的地 一定要和配置文件中的目录保持一致
    )
    public void onEventCustomUpdate(CanalEntry.EntryType entryType,CanalEntry.RowData rowData){

        for (CanalEntry.Column column: rowData.getBeforeColumnsList()) {

            System.out.println("操作前列名"+column.getName()+"-------变更的数据"+column.getValue());
        }
        for (CanalEntry.Column column: rowData.getAfterColumnsList()) {

            System.out.println("操作后列名"+column.getName()+"-------变更的数据"+column.getValue());
        }
    }

    /**
     * 自定义数据库的操作来监听
     * @param eventType
     * @param rowData
     */
    @ListenPoint(destination = "example",//地址
                 schema = {"changgou_content"},//数据
                 table = {"tb_content","tb_content_category"},//表
                 eventType = {CanalEntry.EventType.DELETE,
                         CanalEntry.EventType.UPDATE,
                         CanalEntry.EventType.INSERT})//类型
    public void onEventCustomUpdateContent(CanalEntry.EventType eventType,
                                    CanalEntry.RowData rowData){

        //获取列名为category_id的值
        String categoryId = getColumnValue(eventType,rowData);
        //调用fegin获取该分类下的所有的广告集合
        Result<List<Content>> categoryResult = contentFeign.findByCategory(Long.valueOf(categoryId));
        List<Content> data = categoryResult.getData();
        //使用redisTemplate存储到redis中
        stringRedisTemplate.boundValueOps("content_"+categoryId).set(JSON.toJSONString(data));
    }

    private String getColumnValue(CanalEntry.EventType eventType,CanalEntry.RowData rowData){
        String categoryId = "";
        //判断 如果是删除  则获取beforlist
        if (eventType == CanalEntry.EventType.DELETE){
            for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
                if (column.getName().equalsIgnoreCase("category_id")){
                    categoryId = column.getValue();
                    return  categoryId;
                }
            }
        }else {
            for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                if (column.getName().equalsIgnoreCase("category_id")){
                    categoryId = column.getValue();
                    return  categoryId;
                }
            }
        }
        return categoryId;
    }

    /**
     * cannal监听数据变化生成静态页
     * @param eventType
     * @param rowData
     */
    @ListenPoint(destination = "example",schema = "changgou_goods",table = {"tb_spu"},
    eventType = {CanalEntry.EventType.UPDATE,CanalEntry.EventType.INSERT,CanalEntry.EventType.DELETE})
    public void onEventCustomSpu(CanalEntry.EventType eventType,CanalEntry.RowData rowData){

        //判断操作类型
        if (eventType == CanalEntry.EventType.DELETE){
            String spuId = "";
            List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
            for (CanalEntry.Column column:beforeColumnsList){
                if (column.getName().equals("id")){
                    spuId = column.getValue();//spuid
                    break;
                }
            }
            //todo删除静态页
        }else {
            //新增或者更新
            List<CanalEntry.Column> afterColumnLst = rowData.getAfterColumnsList();
                    String spuId = "";
                    for (CanalEntry.Column column:afterColumnLst){
                        if (column.getName().equals("id")){
                            spuId = column.getValue();
                            break;
                        }
                    }
                    //更新  生成静态页
            pageFeign.createHtml(Long.valueOf(spuId));
        }
    }

}
