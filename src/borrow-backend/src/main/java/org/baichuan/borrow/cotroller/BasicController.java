package org.baichuan.borrow.cotroller;


import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.result.Result;
import org.baichuan.borrow.service.LoginService;
import org.baichuan.borrow.service.BasicImpl.UserService;
import org.baichuan.borrow.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static org.baichuan.borrow.result.CodeMsg.REQUEST_ERROR;

@Controller
@RequestMapping
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
    public Result query(HttpServletRequest request, @RequestBody LoginVo loginVo){
        //由request的Authorization拿到jwt
        //jwt拿到uuid
        Result myResult=verifyService.verify(request,loginVo);
        if(!myResult.getMsg().isEmpty()) return  myResult;
        switch (loginVo.getTableName()){
            case "user" :return Result.success(userService.query(request,loginVo));

        }
        return Result.error(REQUEST_ERROR);
    }
    @RequestMapping("insert")
    public Result insert(HttpServletRequest request, @RequestBody LoginVo loginVo){
        Result myResult=verifyService.verify(request,loginVo);
        if(!myResult.getMsg().isEmpty()) return  myResult;
        switch (loginVo.getTableName()){
            case "user" :return Result.success(userService.insert(request,loginVo));

        }
        return Result.error(REQUEST_ERROR);
    }


}
