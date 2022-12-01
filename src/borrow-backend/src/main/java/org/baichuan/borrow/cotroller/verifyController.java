package org.baichuan.borrow.cotroller;


import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.domin.User;
import org.baichuan.borrow.result.Result;
import org.baichuan.borrow.service.VerifyService;
import org.baichuan.borrow.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static org.baichuan.borrow.result.CodeMsg.*;

@Controller
@RequestMapping("verify")
public class verifyController {

    @Autowired
    VerifyService verifyService;
    @RequestMapping
    public Result verify(HttpServletRequest request, @RequestBody LoginVo loginVo){
        Result myResult=verifyService.verify(request,loginVo);
        return myResult;
    }
}
