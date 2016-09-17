package com.humama.manage.service.item;

import com.github.abel533.entity.Example;
import com.humama.manage.mapper.item.ItemParamItemMapper;
import com.humama.manage.pojo.item.ItemParamItem;
import com.humama.manage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/17 .
 * @Version: 1.0 .
 */
@Service
public class ItemParamItemService extends BaseService<ItemParamItem> {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ItemParamItemMapper itemParamItemMapper;

    public void updateItemParamItem(Long itemId,String itemParams){

        ItemParamItem record = new ItemParamItem();
        record.setParamData(itemParams);
        record.setUpdated(new Date());

        Example example = new Example(ItemParamItem.class);
        example.createCriteria().andEqualTo("itemId",itemId);
        this.itemParamItemMapper.updateByExampleSelective(record,example);
    }
}
