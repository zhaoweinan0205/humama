package com.humama.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.humama.common.bean.EasyUIResult;
import com.humama.common.service.ApiService;
import com.humama.manage.pojo.content.Content;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/19 .
 * @Version: 1.0 .
 */
@Service
public class IndexService {

    @Autowired
    private ApiService apiService;

    @Value("${HUMAMA_MANAGE_URL}")
    private String HUMAMA_MANAGE_URL;

    @Value("${INDEX_AD1_URL}")
    private String INDEX_AD1_URL;

    @Value("${INDEX_AD2_URL}")
    private String INDEX_AD2_URL;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public String queryIndexAD1() {
        try {
            String url = HUMAMA_MANAGE_URL + INDEX_AD1_URL;
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isEmpty(jsonData)){
                return null;
            }
            // 解析json，生成前端所需要的json数据
            EasyUIResult easyUIResult = EasyUIResult.formatToList(jsonData, Content.class);

            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

            for (Content content : (List<Content>)easyUIResult.getRows()){
                if (content != null){
                    Map<String, Object> map = new LinkedHashMap<String, Object>();
                    map.put("srcB", content.getPic());
                    map.put("height", 240);
                    map.put("alt", content.getTitle());
                    map.put("width", 670);
                    map.put("src", content.getPic());
                    map.put("widthB", 550);
                    map.put("href", content.getUrl());
                    map.put("heightB", 240);
                    result.add(map);
                }
            }
            return MAPPER.writeValueAsString(result);
        }catch (Exception e){
           e.printStackTrace();
        }
        return null;
    }

    public String queryIndexAD2() {
        try {
            String url = HUMAMA_MANAGE_URL + INDEX_AD2_URL;
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isEmpty(jsonData)) {
                return null;
            }
            // 解析json，生成前端所需要的json数据
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            ArrayNode rows = (ArrayNode) jsonNode.get("rows");

            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

            for (JsonNode row : rows) {
                Map<String, Object> map = new LinkedHashMap<String, Object>();
                map.put("width", 310);
                map.put("height", 70);
                map.put("src", row.get("pic").asText());
                map.put("href", row.get("url").asText());
                map.put("alt", row.get("title").asText());
                map.put("widthB", 210);
                map.put("heightB", 70);
                map.put("srcB", row.get("pic").asText());
                result.add(map);
            }
            return MAPPER.writeValueAsString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
