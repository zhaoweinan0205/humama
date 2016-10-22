package com.humama.manage.service.content;

import com.humama.manage.mapper.content.ContentCategoryMapper;
import com.humama.manage.pojo.content.Content;
import com.humama.manage.pojo.content.ContentCategory;
import com.humama.manage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/19 .
 * @Version: 1.0 .
 */
@Service
public class ContentCategoryService extends BaseService<ContentCategory>{

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    @Autowired
    private ContentService contentService;

    public void deleteContentByIds(ContentCategory contentCategory,List<Object> ids){
        //先删除内容种类节点
        this.deleteByIds(ContentCategory.class,"id",ids);
        //再删除节点下面的内容
        this.contentService.deleteByIds(Content.class,"categoryId",ids);

        //查看删除节点的父节点是否还有其他子节点
        ContentCategory record = new ContentCategory();
        record.setParentId(contentCategory.getParentId());
        List<ContentCategory> list = this.queryListByWhere(record);
        if (list == null || list.isEmpty()){
            //没有子节点了把他设置为子节点
            ContentCategory parent = new ContentCategory();
            parent.setId(contentCategory.getParentId());
            parent.setParent(false);
            this.updateSelective(parent);
        }
    }

    public ContentCategory saveContentCategory(ContentCategory contentCategory){
        contentCategory.setId(null);
        contentCategory.setParent(false);
        contentCategory.setSortOrder(1);
        contentCategory.setStatus(1);

        this.save(contentCategory);

        //查询上层节点是不是父节点，不是 需要修改
        ContentCategory parent = this.queryById(contentCategory.getParentId());
        if (!parent.getParent()){
            parent.setParent(true);
            this.updateSelective(parent);
        }
        return contentCategory;
    }
}
