package com.humama.manage.controller.Item;

import com.humama.manage.pojo.item.ItemDesc;
import com.humama.manage.service.item.ItemDescService;
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
 * @CreatedTime: 2016/10/16 .
 * @Version: 1.0 .
 */
@RequestMapping(value = "item/desc")
@Controller
public class ItemDescController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemDescController.class);

    @Autowired
    private ItemDescService itemDescService;

    /**
     * 查询商品描述
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public ResponseEntity<ItemDesc> showDetail(@PathVariable(value = "itemId") Long itemId){
        try{
            ItemDesc itemDesc = this.itemDescService.queryById(itemId);
            if (itemDesc == null){
                LOGGER.info("查询商品描述为空");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            LOGGER.info("查询商品描述{}",itemDesc);
            return ResponseEntity.status(HttpStatus.OK).body(itemDesc);
        }catch (Exception e){
            LOGGER.error("查询商品描述发生异常，异常为{}",e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
