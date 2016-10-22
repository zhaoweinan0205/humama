package com.humama.manage.controller.content;

import com.humama.manage.pojo.content.ContentCategory;
import com.humama.manage.service.content.ContentCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 内容分类Controller.
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/19 .
 * @Version: 1.0 .
 */
@RequestMapping(value = "content/category")
@Controller
public class ContentCategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentCategoryController.class);

    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 根据id查询内容分类节点下面的子节点
     * @param parentId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContentCategory>> queryListByParentId(
            @RequestParam(value = "id",defaultValue = "0")Long parentId){
        try {
            ContentCategory record = new ContentCategory();
            record.setParentId(parentId);
            List<ContentCategory> list = this.contentCategoryService.queryListByWhere(record);
            if (list == null || list.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            if (LOGGER.isInfoEnabled()){
                LOGGER.info("根据节点查询内容分类节点的子节点，parentId={}，list={}",parentId,list);
            }
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            LOGGER.error("根据节点查询内容分类节点的子节点发生异常，parentId={}，异常为{}",parentId,e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 新增节点
     * @param contentCategory
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ContentCategory> saveContentCategory(ContentCategory contentCategory){
        try {
            this.contentCategoryService.saveContentCategory(contentCategory);
            LOGGER.info("新增内容种类，ContentCategory={}",contentCategory);
            return ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);
        } catch (Exception e) {
            LOGGER.error("新增内容种类发生异常，异常为{}",e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 节点重命名
     * @param contentCategory
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateContentCategory(ContentCategory contentCategory){
        try {
            this.contentCategoryService.updateSelective(contentCategory);
            if (LOGGER.isInfoEnabled()){
                LOGGER.info("内容列表节点重命名，contentCategory={}",contentCategory);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            LOGGER.error("内容列表节点重命名发生异常，异常为{}",e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 删除节点
     * @param contentCategory
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteContentCategory(ContentCategory contentCategory){
        try {
            List<Object> ids = new ArrayList<>();
            ids.add(contentCategory.getId());
            findNodes(contentCategory.getId(),ids);

            //删除所有子节点deleteContentByIds
            this.contentCategoryService.deleteContentByIds(contentCategory,ids);
            LOGGER.info("删除节点，contentCategory={}",contentCategory);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            LOGGER.error("删除节点异常，contentCategory={}，异常为{}",contentCategory,e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 查询父节点下面所有子节点,使用递归调用
     * @param parentId
     * @param ids
     */
    private void findNodes(Long parentId,List<Object> ids){
        ContentCategory record = new ContentCategory();
        record.setParentId(parentId);
        List<ContentCategory> list = this.contentCategoryService.queryListByWhere(record);

        //遍历列表
        for (ContentCategory contentCategory : list){
            ids.add(contentCategory.getId());
            if (contentCategory.getParent()){
                //如果该节点是父节点，继续遍历该节点下面的节点
                //递归调用
                findNodes(contentCategory.getId(),ids);
            }
        }
    }
}
