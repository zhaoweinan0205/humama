package com.humama.manage.controller.Item;

import com.humama.manage.pojo.item.ItemParamItem;
import com.humama.manage.service.item.ItemParamItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/17 .
 * @Version: 1.0 .
 */
@RequestMapping(value = "item/param/item")
@Controller
public class ItemParamItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemParamItemController.class);

    @Autowired
    private ItemParamItemService itemParamItemService;

    /**
     * 根据商品id查询商品规格参数
     * @param itemId 商品id
     * @return
     */
    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public ResponseEntity<ItemParamItem> queryByItemId(
            @PathVariable("itemId") Long itemId){
        try {
            ItemParamItem record = new ItemParamItem();
            record.setItemId(itemId);
            ItemParamItem itemParamItem = this.itemParamItemService.queryOne(record);
            if (itemParamItem == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            LOGGER.info("根据商品id查询商品规格参数,itemParamItem={}",itemParamItem);
            return ResponseEntity.status(HttpStatus.OK).body(itemParamItem);
        } catch (Exception e) {
            LOGGER.error("根据商品id查询商品规格参数发生异常,异常为{}",e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
