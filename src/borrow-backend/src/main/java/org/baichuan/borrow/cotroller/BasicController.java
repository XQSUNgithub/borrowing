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
import javax.servlet.http.HttpServletResponse;

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
    public Result query(HttpServletResponse response,HttpServletRequest request, @RequestBody LoginVo loginVo) throws ClassNotFoundException {
        log.info("query");
        /*log.info(loginVo0.toJSONString());
        LoginVo loginVo=new LoginVo();
        loginVo.setAction(loginVo0.getString("action"));
        loginVo.setTableName(loginVo0.getString("tablename"));
        log.info(loginVo0.getString("value"));
        loginVo.setValue(JSONObject.parseObject(loginVo0.getString("value")));*/
       // LoginVo loginVo=JSONObject.toJavaObject(loginVo0,LoginVo.class);
        log.info(JSONObject.toJSONString(loginVo));
        Result myResult=verifyService.verify(response,request,loginVo);
        if(myResult.getMsg()!=null) return  myResult;
        log.info("鉴权成功");
        switch (loginVo.getTableName()){
            case "User" :return userService.query(request,loginVo,userDao);

        }
        return Result.error(REQUEST_ERROR);
    }
    @RequestMapping("insert")
    @ResponseBody
    public Result insert(HttpServletResponse response,HttpServletRequest request, @RequestBody LoginVo loginVo) throws ClassNotFoundException {
        log.info("insert");
       // LoginVo loginVo=JSONObject.toJavaObject(loginVo0,LoginVo.class);
        log.info(JSONObject.toJSONString(loginVo));
        Result myResult=verifyService.verify(response,request,loginVo);
        if(myResult.getMsg()!=null) return  myResult;
        log.info("鉴权成功");
        switch (loginVo.getTableName()){
            case "User" :return userService.insert(request,loginVo,userDao);
        }
        return Result.error(REQUEST_ERROR);
    }

    @RequestMapping("delete")
    @ResponseBody
    public Result delete(HttpServletResponse response,HttpServletRequest request,@RequestBody LoginVo loginVo){
        log.info("delete");
       // LoginVo loginVo=JSONObject.toJavaObject(loginVo0,LoginVo.class);
        log.info(JSONObject.toJSONString(loginVo));
        Result myResult=verifyService.verify(response,request,loginVo);
        if(myResult.getMsg()!=null) return  myResult;
        log.info("鉴权成功");
        switch (loginVo.getTableName()){
            case "User" :return userService.delete(request,loginVo,userDao);
        }
        return Result.error(REQUEST_ERROR);
    }

}
