package com.humama.manage.service.item;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.humama.manage.mapper.item.ItemParamMapper;
import com.humama.manage.pojo.item.ItemParam;
import com.humama.manage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/17 .
 * @Version: 1.0 .
 */
@Service
public class ItemParamService extends BaseService<ItemParam> {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ItemParamMapper itemParamMapper;

   /* public PageInfo<ItemParam> queryPageList(Integer page,Integer rows){
        Example example = new Example(ItemParam.class);
        example.setOrderByClause("updated DESC");

        //设置分页参数
        PageHelper.startPage(page,rows);

        List<ItemParam> list = this.itemParamMapper.selectByExample(example);
        return new PageInfo<ItemParam>(list);
    }*/

    public List<Map<String,Object>> queryPageList(Integer page, Integer rows){
        Map<String,Integer> map = new HashMap<>();
        page = (page - 1) * rows;
        map.put("page",page);
        map.put("rows",rows);
        return this.itemParamMapper.queryPageList(map);
    }
}
