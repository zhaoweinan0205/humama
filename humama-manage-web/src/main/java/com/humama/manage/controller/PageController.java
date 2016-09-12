package com.humama.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description: 界面跳转.
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/12 .
 * @Version: 1.0 .
 */
@RequestMapping(value = "page")
@Controller
public class PageController {

    @RequestMapping(value = "{page}",method = RequestMethod.GET)
    public String toPage(@PathVariable("page") String page){
        return page;
    }

}
