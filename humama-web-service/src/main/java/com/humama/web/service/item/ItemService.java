package com.humama.web.service.item;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.humama.common.service.ApiService;
import com.humama.common.service.RedisService;
import com.humama.manage.pojo.item.ItemDesc;
import com.humama.web.pojo.item.Item;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/29 .
 * @Version: 1.0 .
 */
@Service
public class ItemService {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String REDIS_ITEM_KEY = "HUMAMA_WEB_ITEM_DETAIL_";

    private static final String REDIS_ITEM_DESC_KEY = "HUMAMA_WEB_ITEM_DESC_";

    private static final String REDIS_ITEM_PARAM_KEY = "HUMAMA_WEB_ITEM_PARAM_";

    private static final Integer REDIS_TIME = 60 * 60 * 24;

    @Value("${HUMAMA_MANAGE_URL}")
    private String HUMAMA_MANAGE_URL;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ApiService apiService;

    /**
     * 获取商品基本信息
     * @param id
     * @return
     */
    public Item queryItemById(Long id){
        //先去缓存命中
        String key = REDIS_ITEM_KEY + id;
        try{
            String cacheData = this.redisService.get(key);
            //命中
            if (StringUtils.isNotEmpty(cacheData)){
                return MAPPER.readValue(cacheData,Item.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //没有命中 从后台接口拿数据
        try{
            String url = HUMAMA_MANAGE_URL + "/rest/item/" + id;
            String jsonData = this.apiService.doGet(url);

            if (StringUtils.isEmpty(jsonData)){
                return null;
            }

            //存到缓存中去
            try {
                this.redisService.set(key, jsonData, REDIS_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return MAPPER.readValue(jsonData,Item.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取描述信息
     * @param id
     * @return
     */
    public ItemDesc queryItemDescById(Long id){
        //先从缓存命中
        String key = REDIS_ITEM_DESC_KEY + id;
        try{
            String cacheData = this.redisService.get(key);
            if (StringUtils.isNotEmpty(cacheData)){
                //命中了
                return MAPPER.readValue(cacheData,ItemDesc.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //没有命中，去后台取数据
        try{
            String url = HUMAMA_MANAGE_URL + "/rest/item/desc/" + id;
            String jsonData = this.apiService.doGet(url);

            if (StringUtils.isEmpty(jsonData)){
                return null;
            }

            //保存到缓存中
            try {
                this.redisService.set(key,jsonData,REDIS_TIME);
            }catch (Exception e){
                e.printStackTrace();
            }

            return MAPPER.readValue(jsonData,ItemDesc.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询商品规格参数
     * @param itemId
     * @return
     */
    public String queryItemParamByItemId(Long itemId) {
        //先从缓存
        String key = REDIS_ITEM_PARAM_KEY + itemId;
        try{
            String cacheData = this.redisService.get(key);
            if (StringUtils.isNotEmpty(cacheData)){
                //命中
                return cacheData;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //没有命中
        try{
            String url = HUMAMA_MANAGE_URL + "/rest/item/param/item/" + itemId;
            String jsonData = this.apiService.doGet(url);
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            ArrayNode paramData = (ArrayNode) MAPPER.readTree(jsonNode.get("paramData").asText());
            StringBuilder sb = new StringBuilder();
            sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\"><tbody>");
            for (JsonNode param : paramData) {
                sb.append("<tr><th class=\"tdTitle\" colspan=\"2\">" + param.get("group").asText()
                        + "</th></tr>");
                ArrayNode params = (ArrayNode) param.get("params");
                for (JsonNode p : params) {
                    sb.append("<tr><td class=\"tdTitle\">" + p.get("k").asText() + "</td><td>"
                            + p.get("v").asText() + "</td></tr>");
                }
            }
            sb.append("</tbody></table>");

            //存到缓存中
            try{
                this.redisService.set(key,sb.toString(),REDIS_TIME);
            }catch (Exception e){
                e.printStackTrace();
            }

            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
