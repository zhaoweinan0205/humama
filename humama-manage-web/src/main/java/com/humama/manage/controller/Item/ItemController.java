package com.humama.manage.controller.Item;

import com.humama.manage.pojo.item.Item;
import com.humama.manage.service.item.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/13 .
 * @Version: 1.0 .
 */
@RequestMapping(value = "item")
@Controller
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item,
             @RequestParam("desc") String desc){
        try {
            if (item == null && StringUtils.isEmpty(item.getTitle())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            item.setStatus(1);
            item.setId(null);

            this.itemService.save(item);
            LOGGER.info("保存item");
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            LOGGER.error("保存item发生异常，异常为{}",e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
