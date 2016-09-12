package com.humama.manage.controller.Item;

import com.humama.manage.service.item.ItemCatService;
import com.humama.manage.pojo.item.ItemCat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/12 .
 * @Version: 1.0 .
 */
@RequestMapping(value = "item/cat")
@Controller
public class ItemCatController {

    private static Logger LOGGER = LoggerFactory.getLogger(ItemCatController.class);

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ItemCat>> queryItemCatListByParentId
            (@RequestParam(value = "id",defaultValue = "0") Long parentId){
        try {
            List<ItemCat> list = this.itemCatService.queryItemCatListByParentId(parentId);
            if (list == null && list.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            LOGGER.info("查询商品类目，parentId={}，列表长度为{}",parentId,list.size());
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            LOGGER.error("查询商品类目异常，parentId={}，异常为{}",parentId,e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
