package com.humama.common.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @Description: 商品类目，用于前台网站左侧使用.
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/18 .
 * @Version: 1.0 .
 */
public class ItemCatData {

    // 序列化成json数据时为 u
    @JsonProperty("u")
    private String url;

    @JsonProperty("n")
    private String name;

    @JsonProperty("i")
    private List<?> items;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ItemCatData{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", items=" + items +
                '}';
    }
}
