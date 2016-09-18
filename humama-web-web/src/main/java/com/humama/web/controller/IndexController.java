package com.humama.web.controller;

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

    @RequestMapping(value = "index",method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");

        return modelAndView;
    }
}
