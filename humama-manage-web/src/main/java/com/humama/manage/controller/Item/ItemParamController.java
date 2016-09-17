package com.humama.manage.controller.Item;

import com.humama.common.bean.EasyUIResult;
import com.humama.manage.pojo.item.ItemParam;
import com.humama.manage.service.item.ItemParamService;
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

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/17 .
 * @Version: 1.0 .
 */
@RequestMapping(value = "item/param")
@Controller
public class ItemParamController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemParamController.class);

    @Autowired
    private ItemParamService itemParamService;

    /**
     * 根据商品类目id查找规格参数模板
     * @param itemCatId 商品类目id
     * @return
     */
    @RequestMapping(value = "{itemCatId}",method = RequestMethod.GET)
    public ResponseEntity<ItemParam> queryItemParamByItemCatId(
            @PathVariable("itemCatId") Long itemCatId){
        try {
            ItemParam record = new ItemParam();
            record.setItemCatId(itemCatId);
            ItemParam itemParam = this.itemParamService.queryOne(record);
            if (itemParam == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            LOGGER.info("根据商品类目id查找规格参数模板，itemCatId={},itemParam={}",itemCatId,itemParam);
            return ResponseEntity.status(HttpStatus.OK).body(itemParam);
        } catch (Exception e) {
            LOGGER.error("根据商品类目id查找规格参数模板出现异常，异常为{}",e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 新增规格参数模板
     * @param itemCatId
     * @param paramData
     * @return
     */
    @RequestMapping(value = "{itemCatId}",method = RequestMethod.POST)
    public ResponseEntity<Void> saveItemParam(@PathVariable("itemCatId") Long itemCatId,
              @RequestParam("paramData") String paramData){
        try{
            ItemParam itemParam = new ItemParam();
            itemParam.setId(null);
            itemParam.setItemCatId(itemCatId);
            itemParam.setParamData(paramData);
            itemParam.setCreated(new Date());
            itemParam.setUpdated(itemParam.getCreated());
            this.itemParamService.save(itemParam);
            LOGGER.info("新增规格参数模板,itemParam={}",itemParam);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            LOGGER.error("新增规格参数模板出现异常，异常为{}",e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 分页查询商品规格列表
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryItemParamByItemList(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "30") Integer rows){
        try {
           /* PageInfo<ItemParam> itemParamPageInfo = this.itemParamService.queryPageList(page,rows);
            EasyUIResult easyUIResult = new EasyUIResult(itemParamPageInfo.getTotal(),
                    itemParamPageInfo.getList());*/
            List<Map<String,Object>> list = this.itemParamService.queryPageList(page,rows);
            EasyUIResult easyUIResult = new EasyUIResult(list.size(),list);
            LOGGER.info("分页查询规格参数列表,list={}",list);
            return ResponseEntity.status(HttpStatus.OK).body(easyUIResult);
        } catch (Exception e) {
            LOGGER.error("分页查询规格参数列表发生异常，异常为{}",e) ;
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
