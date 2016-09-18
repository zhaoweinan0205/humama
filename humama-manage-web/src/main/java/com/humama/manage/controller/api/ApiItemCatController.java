package com.humama.manage.controller.api;

import com.humama.common.bean.ItemCatResult;
import com.humama.manage.service.item.ItemCatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description: 为前台系统提供商品类目接口.
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/18 .
 * @Version: 1.0 .
 */
@RequestMapping(value = "api/item/cat")
@Controller
public class ApiItemCatController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ApiItemCatController.class);

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 对外提供商品类目树
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ItemCatResult> queryItemTree(){
        try {
            ItemCatResult itemCatResult = this.itemCatService.queryAllTree();
            LOGGER.info("前台调用获取商品类目树,itemCatResult={}",itemCatResult);
            return ResponseEntity.status(HttpStatus.OK).body(itemCatResult);
        } catch (Exception e) {
            LOGGER.error("前台调用获取商品类目树发生异常,异常为{}",e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
