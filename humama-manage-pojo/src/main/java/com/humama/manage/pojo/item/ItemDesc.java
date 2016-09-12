package com.humama.manage.pojo.item;

import com.humama.manage.pojo.BasePojo;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/13 .
 * @Version: 1.0 .
 */
@Table(name = "tb_item_desc")
public class ItemDesc extends BasePojo {

    @Id
    private Long itemId;

    private String itemDesc;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    @Override
    public String toString() {
        return "ItemDesc{" +
                "itemId=" + itemId +
                ", itemDesc='" + itemDesc + '\'' +
                '}';
    }
}
