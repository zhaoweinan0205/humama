package com.humama.manage.service.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humama.common.bean.ItemCatData;
import com.humama.common.bean.ItemCatResult;
import com.humama.common.service.RedisService;
import com.humama.manage.service.BaseService;
import com.humama.manage.mapper.item.ItemCatMapper;
import com.humama.manage.pojo.item.ItemCat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/12 .
 * @Version: 1.0 .
 */
@Service
public class ItemCatService extends BaseService<ItemCat>{

    private static final String REDIS_KEY = "HUMAMA_MANAGE_ITEM_CAT_ALL";// 最佳实践，项目名_模块名_业务名

    private static final Integer REDIS_TIME = 60 * 60 * 24 * 30 * 3;

    private final static ObjectMapper MAPPER = new ObjectMapper();

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private RedisService redisService;

    public List<ItemCat> queryItemCatListByParentId(Long parentId){
        ItemCat record = new ItemCat();
        record.setParentId(parentId);
        return this.queryListByWhere(record);
    }

    /**
     * 查询商品类目树
     * @return
     */
    public ItemCatResult queryAllTree(){
        ItemCatResult result = new ItemCatResult();

        //先从缓存查询
        try {
            String cacheData = this.redisService.get(REDIS_KEY);
            if (StringUtils.isNotEmpty(cacheData)){
                //命中
                return MAPPER.readValue(cacheData,ItemCatResult.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //先查出所有的商品类目
        List<ItemCat> itemCats = this.queryAll();

        // 转为map存储，key为父节点ID，value为数据集合
        Map<Long,List<ItemCat>> itemCatMap = new HashMap<>();


        //把类目列表添加到map中，商品类目的父id作为key,节点队列为value
        for (ItemCat itemCat : itemCats){
            if (!itemCatMap.containsKey(itemCat.getParentId())){
                itemCatMap.put(itemCat.getParentId(),new ArrayList<ItemCat>());
            }
            itemCatMap.get(itemCat.getParentId()).add(itemCat);
        }

        //封装一级结构
        List<ItemCat> itemCatList1 = itemCatMap.get(0L);

        for (ItemCat itemCat : itemCatList1){
            ItemCatData itemCatData = new ItemCatData();
            itemCatData.setUrl("/products/" + itemCat.getId() + ".html");
            itemCatData.setName("<a href='" + itemCatData.getUrl() + "'>" + itemCat.getName() + "</a>");

            result.getItemCats().add(itemCatData);

            //判断是否为父节点，不是父节点就continue
            if (!itemCat.getIsParent()){
                continue;
            }
            //是父节点，分装二级对象
            //获取该节点下面的子节点
            List<ItemCat> itemCatList2 = itemCatMap.get(itemCat.getId());
            List<ItemCatData> itemCatData2 = new ArrayList<>();
            itemCatData.setItems(itemCatData2);
            for (ItemCat itemCat2 : itemCatList2){
                ItemCatData id2 = new ItemCatData();
                id2.setName(itemCat2.getName());
                id2.setUrl("/products/" + itemCat2.getId() + ".html");
                itemCatData2.add(id2);

                if (itemCat2.getIsParent()){
                    // 封装三级对象
                    List<ItemCat> itemCatList3 = itemCatMap.get(itemCat2.getId());
                    List<String> itemCatData3 = new ArrayList<>();
                    id2.setItems(itemCatData3);
                    for (ItemCat itemCat3 : itemCatList3) {
                        itemCatData3.add("/products/" + itemCat3.getId() + ".html|" + itemCat3.getName());
                    }
                }
            }
            if (result.getItemCats().size() >= 14) {
                break;
            }
        }

        //存到缓存中去
        try {
            this.redisService.set(REDIS_KEY,MAPPER.writeValueAsString(result),REDIS_TIME);
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
