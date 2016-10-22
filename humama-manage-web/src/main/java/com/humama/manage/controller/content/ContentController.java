package com.humama.manage.controller.content;

import com.humama.common.bean.EasyUIResult;
import com.humama.manage.pojo.content.Content;
import com.humama.manage.service.content.ContentService;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/19 .
 * @Version: 1.0 .
 */
@RequestMapping(value = "content")
@Controller
public class ContentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    private ContentService contentService;

    /**
     * 根据内容分类id查询内容列表
     * @param categoryId 内容分类id
     * @param page 页数
     * @param rows 每页数据量
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryListByCategoryId(
            @RequestParam(value = "categoryId")Long categoryId,
            @RequestParam(value = "page",defaultValue = "0")Integer page,
            @RequestParam(value = "rows",defaultValue = "30")Integer rows){
        try {
            EasyUIResult easyUIResult = this.contentService.queryListByCategoryId(categoryId,page,rows);
            if (LOGGER.isInfoEnabled()){
                LOGGER.info("根据内容分类id查询内容列表，categoryId={}，page={}，rows={}",
                        categoryId,page,rows);
            }
            return ResponseEntity.status(HttpStatus.OK).body(easyUIResult);
        } catch (Exception e) {
            LOGGER.error("根据内容分类id查询内容列表，categoryId={}，异常为{}",categoryId,e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 新增内容
     * @param content
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveContent(Content content){
        try {
            if (StringUtils.isEmpty(content.getTitle())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            content.setId(null);
            this.contentService.save(content);
            LOGGER.info("新增内容，content={}",content);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            LOGGER.error("新增内容发生异常为{}",e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 更新内容
     * @param content
     * @return
     */
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public ResponseEntity<Void> updateContent(Content content){
        try{
            if (StringUtils.isEmpty(content.getTitle())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            this.contentService.update(content);
            LOGGER.info("更新内容，content={}",content);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            LOGGER.error("更新内容发生异常为{}",e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public ResponseEntity<Void> deleteContent(@RequestParam("ids") String ids){
        try {
            if (StringUtils.isEmpty(ids)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            List<Object> values = new ArrayList<>();
            String[] args = StringUtils.split(ids,",");
            for (String s : args){
                values.add(s);
            }
            this.contentService.deleteByIds(Content.class,"id",values);
            LOGGER.info("删除内容，ids={}",ids);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            LOGGER.error("删除内容出现异常，ids={}，异常为{}",ids,e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
