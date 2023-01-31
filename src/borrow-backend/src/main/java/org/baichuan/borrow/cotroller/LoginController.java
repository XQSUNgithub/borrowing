package org.baichuan.borrow.cotroller;

import com.alibaba.fastjson.JSONObject;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.result.Result;
import org.baichuan.borrow.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.baichuan.borrow.result.CodeMsg.REQUEST_ERROR;

@Slf4j
@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping(value="/register")
    @ResponseBody
    //返回类与前端关系？
    public Result<String> register(HttpServletRequest request, @RequestBody LoginVo loginVo) throws Exception {

      log.info("password:"+loginVo.getPassword()+"realname:"+loginVo.getRealname()+"invitecode:"+loginVo.getInvitecode());
       //登录
        String id=loginService.register(loginVo);
        if(id==null) return Result.error(REQUEST_ERROR);
        return Result.success(id);
    }

    @RequestMapping(value="/do_login")
    @ResponseBody
    //返回类与前端关系？
    public Result<JSONObject> doLogin(HttpServletResponse response, @RequestBody LoginVo loginVo) throws Exception {
        log.info(loginVo.getPassword()+" "+loginVo.getRealname());
        //登录
        JSONObject res=loginService.login(loginVo);
        log.info(res+"登陆后");
        if(res==null) return Result.error(REQUEST_ERROR);//response.setStatus(403);

        return Result.success(res);
    }

    @RequestMapping("/to_login")
    public String toLogin() {
        log.info("login");
        return "login";
    }
}
