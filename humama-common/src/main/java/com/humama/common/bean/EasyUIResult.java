package com.humama.common.bean;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/14 .
 * @Version: 1.0 .
 */
public class EasyUIResult {

    private Integer total;

    private List<?> rows;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public EasyUIResult() {
    }

    public EasyUIResult(Integer total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public EasyUIResult(Long total, List<?> rows) {
        this.total = total.intValue();
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    /**
     * 胡回来的数据转化为EasyUIResult对象
     * @param jsonData
     * @param clazz
     * @return
     */
    public static EasyUIResult formatToList(String jsonData,Class<?> clazz){
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("rows");
            List<?> list = null;
            if (data.isArray() && data.size() > 0){
                list = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return new EasyUIResult(jsonNode.get("total").intValue(),list);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public String toString() {
        return "EasyUIResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
