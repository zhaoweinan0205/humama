package com.humama.common.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 商品类目列表使用.
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/18 .
 * @Version: 1.0 .
 */
public class ItemCatResult {

    //指定序列化json后的名称
    @JsonProperty("data")
    private List<ItemCatData> itemCats = new ArrayList<ItemCatData>();

    public List<ItemCatData> getItemCats() {
        return itemCats;
    }

    public void setItemCats(List<ItemCatData> itemCats) {
        this.itemCats = itemCats;
    }

    @Override
    public String toString() {
        return "ItemCatResult{" +
                "itemCats=" + itemCats +
                '}';
    }
}
