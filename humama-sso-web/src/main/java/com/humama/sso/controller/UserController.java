package com.humama.sso.controller;

import com.humama.sso.pojo.User;
import com.humama.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/10/17 .
 * @Version: 1.0 .
 */
@RequestMapping(value = "user")
@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 注册界面
     * @return
     */
    @RequestMapping(value = "register",method = RequestMethod.GET)
    public String toRegister(){
        return "register";
    }

    /**
     * 登录界面
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String toLogin() {
        return "login";
    }

    @RequestMapping(value = "check/{param}/{type}",method = RequestMethod.GET)
    public ResponseEntity<Boolean> check(@PathVariable(value = "param") String param,
                                         @PathVariable(value = "type") Integer type){

        try{
            Boolean bool = this.userService.check(param,type);
            if (bool == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(bool);
        }catch (Exception e){
            LOGGER.error("检查用户名是否可用出现异常，param={},type={},异常为{}");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @RequestMapping(value = "doRegister",method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> doRegister(@Valid User user, BindingResult bindingResult){
        Map<String,Object> result = new HashMap<>();
        if (bindingResult.hasErrors()) {
            // 校验有错误
            List<String> msgs = new ArrayList<String>();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String msg = objectError.getDefaultMessage();
                msgs.add(msg);
            }
            result.put("status", "400");
            result.put("data", StringUtils.join(msgs, '|'));
            return ResponseEntity.ok(result);
        }

        Boolean bool = this.userService.saveUser(user);
        LOGGER.info("注册用户，是否成功={}",bool);
        if (bool){
            result.put("status","200");
        }else {
            result.put("status","300");
            result.put("data","是的！");
        }
        return ResponseEntity.ok(result);
    }
}
