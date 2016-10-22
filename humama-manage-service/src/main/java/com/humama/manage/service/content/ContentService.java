package com.humama.manage.service.content;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.humama.common.bean.EasyUIResult;
import com.humama.manage.mapper.content.ContentMapper;
import com.humama.manage.pojo.content.Content;
import com.humama.manage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/19 .
 * @Version: 1.0 .
 */
@Service
public class ContentService extends BaseService<Content>{

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ContentMapper contentMapper;

    public EasyUIResult queryListByCategoryId(Long categoryId,Integer page,Integer rows){
        Example example = new Example(Content.class);
        example.setOrderByClause("updated DESC");
        example.createCriteria().andEqualTo("categoryId",categoryId);
        PageHelper.startPage(page,rows);
        PageInfo<Content> pageInfo = new PageInfo<Content>(
                this.contentMapper.selectByExample(example));
        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
    }
}
