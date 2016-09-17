package com.humama.manage.pojo.item;

import com.humama.manage.pojo.BasePojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/17 .
 * @Version: 1.0 .
 */
@Table(name = "tb_item_param")
public class ItemParam extends BasePojo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long itemCatId;

    private String paramData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemCatId() {
        return itemCatId;
    }

    public void setItemCatId(Long itemCatId) {
        this.itemCatId = itemCatId;
    }

    public String getParamData() {
        return paramData;
    }

    public void setParamData(String paramData) {
        this.paramData = paramData;
    }

    @Override
    public String toString() {
        return "ItemParam{" +
                "id=" + id +
                ", itemCatId=" + itemCatId +
                ", paramData='" + paramData + '\'' +
                '}';
    }
}
