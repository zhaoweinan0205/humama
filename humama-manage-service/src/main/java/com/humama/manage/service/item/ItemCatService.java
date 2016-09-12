package com.humama.manage.service.item;

import com.humama.manage.service.BaseService;
import com.humama.manage.mapper.item.ItemCatMapper;
import com.humama.manage.pojo.item.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/12 .
 * @Version: 1.0 .
 */
@Service
public class ItemCatService extends BaseService<ItemCat>{

    public List<ItemCat> queryItemCatListByParentId(Long parentId){
        ItemCat record = new ItemCat();
        record.setParentId(parentId);
        return this.queryListByWhere(record);
    }

}
