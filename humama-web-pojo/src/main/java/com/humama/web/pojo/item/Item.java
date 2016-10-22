package com.humama.web.pojo.item;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/29 .
 * @Version: 1.0 .
 */
public class Item extends com.humama.manage.pojo.item.Item{

    public String[] getImages() {
        return StringUtils.split(super.getImage(), ',');
    }
}
