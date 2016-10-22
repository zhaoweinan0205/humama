package com.humama.web.controller.item;


import com.humama.manage.pojo.item.ItemDesc;
import com.humama.web.pojo.item.Item;
import com.humama.web.service.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/29 .
 * @Version: 1.0 .
 */
@RequestMapping(value = "item")
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public ModelAndView showDetail(@PathVariable(value = "itemId") Long itemId){
        ModelAndView modelAndView = new ModelAndView("item");
        //基本数据
        Item item = this.itemService.queryItemById(itemId);
        modelAndView.addObject("item",item);

        //描述数据
        ItemDesc itemDesc = this.itemService.queryItemDescById(itemId);
        modelAndView.addObject("itemDesc",itemDesc);

        //规格参数数据
        String itemParam = this.itemService.queryItemParamByItemId(itemId);
        modelAndView.addObject("itemParam", itemParam);

        //添加
        return modelAndView;
    }
}
