package com.humama.web.controller;

import com.humama.web.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 首页Controller.
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/18 .
 * @Version: 1.0 .
 */
@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    @RequestMapping(value = "index",method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");
        //大广告位数据
        String indexAd1 = this.indexService.queryIndexAD1();
        modelAndView.addObject("indexAd1",indexAd1);

        //小广告
        String indexAd2 = this.indexService.queryIndexAD2();
        modelAndView.addObject("indexAd2",indexAd2);

        return modelAndView;
    }
}
