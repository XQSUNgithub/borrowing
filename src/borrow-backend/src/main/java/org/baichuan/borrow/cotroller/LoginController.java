package org.baichuan.borrow.cotroller;

import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.result.Result;
import org.baichuan.borrow.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

import static org.baichuan.borrow.result.CodeMsg.REQUEST_ERROR;

@Slf4j
@Controller
@RequestMapping("/serve/login")
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping(value="/register")
    @ResponseBody
    //返回类与前端关系？
    public Result<String> register(HttpServletResponse response, LoginVo loginVo) throws Exception {
        log.info("password:"+loginVo.getPassword()+"realname:"+loginVo.getRealname()+"invitecode:"+loginVo.getInvitecode());
        //登录
        String id=userService.register(loginVo);
        if(id==null) return Result.error(REQUEST_ERROR);
        return Result.success(id);
    }

    @RequestMapping(value="/do_login")
    @ResponseBody
    //返回类与前端关系？
    public Result<String> doLogin(HttpServletResponse response, LoginVo loginVo) throws Exception {
        log.info(loginVo.getPassword()+" "+loginVo.getRealname());
        //登录
        String token=userService.login(loginVo);
        if(token==null) return Result.error(REQUEST_ERROR);
        return Result.success(token);
    }

    @RequestMapping("/to_login")
    public String toLogin() {
        log.info("login");
        return "login";
    }
}
