package com.humama.manage.mapper.item;

import com.github.abel533.mapper.Mapper;
import com.humama.manage.pojo.item.ItemParam;

import java.util.List;
import java.util.Map;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/17 .
 * @Version: 1.0 .
 */
public interface ItemParamMapper extends Mapper<ItemParam> {

    public List<Map<String,Object>> queryPageList(Map<String,Integer> map);
}
