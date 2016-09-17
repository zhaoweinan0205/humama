package com.humama.manage.controller.Item;

import com.github.pagehelper.PageInfo;
import com.humama.common.bean.EasyUIResult;
import com.humama.manage.pojo.item.Item;
import com.humama.manage.pojo.item.ItemDesc;
import com.humama.manage.service.item.ItemDescService;
import com.humama.manage.service.item.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private ItemDescService itemDescService;

    /**
     * 添加商品
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item,
             @RequestParam("desc") String desc,
             @RequestParam("itemParams") String itemParams){
        try {
            if (item == null || StringUtils.isEmpty(item.getTitle())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            if (LOGGER.isInfoEnabled()){
                LOGGER.info("保存item={},描述为{},规格参数为{}",item,desc,itemParams);
            }
            this.itemService.saveItem(item,desc,itemParams);

            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            LOGGER.error("保存item发生异常，异常为{}",e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 获取商品列表
     * @param page 页数
     * @param rows 每页数据量
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryItemList(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "30") Integer rows){
        try {
            PageInfo<Item> itemPageInfo = this.itemService.queryPageList(page,rows);
            EasyUIResult easyUIResult = new EasyUIResult(itemPageInfo.getTotal(),
                    itemPageInfo.getList());
            return ResponseEntity.status(HttpStatus.OK).body(easyUIResult);
        } catch (Exception e) {
            LOGGER.error("获取商品列表出现异常，异常为{}",e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 查询商品描述
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public ResponseEntity<ItemDesc> queryItemDescByItemId(@PathVariable Long itemId){
        try {
            ItemDesc itemDesc = this.itemDescService.queryById(itemId);
            if (itemDesc == null){
                LOGGER.info("查询商品描述为空");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(itemDesc);
            }
            LOGGER.info("查询商品描述{}",itemDesc);
            return ResponseEntity.status(HttpStatus.OK).body(itemDesc);
        } catch (Exception e) {
            LOGGER.error("查询商品描述发生异常，异常为{}",e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 更新商品信息
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateItem(Item item,@RequestParam("desc") String desc,
                           @RequestParam("itemParams") String itemParams){
        try {
            LOGGER.info("修改商品,item={},desc={},itemParams={}",item,desc,itemParams);
            if (StringUtils.isEmpty(item.getTitle())){
                //参数问题返回400
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            this.itemService.updateItem(item,desc,itemParams);

            LOGGER.info("更新商品成功，itemId={}",item.getId());

            //成功返回204
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            LOGGER.error("更新商品发生异常，异常为{}",e);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
