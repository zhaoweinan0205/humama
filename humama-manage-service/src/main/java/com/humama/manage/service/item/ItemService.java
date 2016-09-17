package com.humama.manage.service.item;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.humama.manage.mapper.item.ItemMapper;
import com.humama.manage.pojo.item.Item;
import com.humama.manage.pojo.item.ItemDesc;
import com.humama.manage.pojo.item.ItemParam;
import com.humama.manage.pojo.item.ItemParamItem;
import com.humama.manage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/13 .
 * @Version: 1.0 .
 */
@Service
public class ItemService extends BaseService<Item> {

    @Autowired
    private ItemDescService itemDescService;

    @Autowired
    private ItemParamItemService itemParamItemService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ItemMapper itemMapper;

    /**
     * 保存商品
     * @param item
     * @param desc
     * @param itemParams
     */
    public void saveItem(Item item,String desc,String itemParams){
        item.setId(null);
        item.setStatus(1);
        super.save(item);

        //保存商品描述
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        this.itemDescService.save(itemDesc);

        //保存规格参数
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setItemId(item.getId());
        itemParamItem.setParamData(itemParams);
        this.itemParamItemService.save(itemParamItem);
    }

    public PageInfo<Item> queryPageList(Integer page,Integer rows){
        Example example = new Example(Item.class);
        example.setOrderByClause("updated DESC");

        // 设置分页参数
        PageHelper.startPage(page, rows);

        List<Item> list = this.itemMapper.selectByExample(example);
        return new PageInfo<Item>(list);
    }


    public void updateItem(Item item,String desc,String itemParams){
        // 强制设置不能修改的字段为null
        item.setStatus(null);
        item.setCreated(null);
        this.updateSelective(item);

        // 修改商品描述数据
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        this.itemDescService.updateSelective(itemDesc);

        //修改规格参数
        this.itemParamItemService.updateItemParamItem(item.getId(),itemParams);
    }

}
