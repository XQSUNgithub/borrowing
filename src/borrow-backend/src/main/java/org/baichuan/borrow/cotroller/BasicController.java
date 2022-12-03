package org.baichuan.borrow.cotroller;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.result.Result;
import org.baichuan.borrow.service.LoginService;
import org.baichuan.borrow.service.BasicImpl.UserService;
import org.baichuan.borrow.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static org.baichuan.borrow.result.CodeMsg.REQUEST_ERROR;

@Slf4j
@Controller
@RequestMapping("/")
public class BasicController {
    //人员列表
    //录入人员
    //修改人员账号 密码 姓名 权限
    @Autowired
    UserDao userDao;

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;
    @Autowired
    VerifyService verifyService;
    @RequestMapping("query")
    @ResponseBody
    public Result query(HttpServletRequest request, @RequestBody JSONObject loginVo0){
        log.info("query");
        log.info(loginVo0.toJSONString());
        LoginVo loginVo=JSONObject.toJavaObject(loginVo0,LoginVo.class);
        log.info(JSONObject.toJSONString(loginVo));
        Result myResult=verifyService.verify(request,loginVo);
        if(myResult.getMsg()!=null) return  myResult;
        log.info("鉴权成功");
        switch (loginVo.getTableName()){
            case "user" :return userService.query(request,loginVo);

        }
        return Result.error(REQUEST_ERROR);
    }
    @RequestMapping("insert")
    @ResponseBody
    public Result insert(HttpServletRequest request, @RequestBody JSONObject loginVo0){
        log.info("insert");
        log.info(loginVo0.toJSONString());
        LoginVo loginVo=JSONObject.toJavaObject(loginVo0,LoginVo.class);
        log.info(JSONObject.toJSONString(loginVo));
        Result myResult=verifyService.verify(request,loginVo);
        if(myResult.getMsg()!=null) return  myResult;
        log.info("鉴权成功");
        switch (loginVo.getTableName()){
            case "user" :return userService.insert(request,loginVo);

        }
        return Result.error(REQUEST_ERROR);
    }

    @RequestMapping("delete")
    @ResponseBody
    public Result delete(HttpServletRequest request,@RequestBody JSONObject loginVo0){
        log.info("delete");
        log.info(loginVo0.toJSONString());
        LoginVo loginVo=JSONObject.toJavaObject(loginVo0,LoginVo.class);
        log.info(JSONObject.toJSONString(loginVo));
        Result myResult=verifyService.verify(request,loginVo);
        if(myResult.getMsg()!=null) return  myResult;
        log.info("鉴权成功");
        switch (loginVo.getTableName()){
            case "user" :return userService.delete(request,loginVo);

        }
        return Result.error(REQUEST_ERROR);
    }

}
