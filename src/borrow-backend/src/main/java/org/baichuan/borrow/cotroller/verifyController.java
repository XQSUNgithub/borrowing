package org.baichuan.borrow.cotroller;


import lombok.extern.slf4j.Slf4j;
import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.domin.User;
import org.baichuan.borrow.result.Result;
import org.baichuan.borrow.service.VerifyService;
import org.baichuan.borrow.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static org.baichuan.borrow.result.CodeMsg.*;

@Slf4j
@Controller
@RequestMapping("verify")
public class verifyController {

    @Autowired
    VerifyService verifyService;

    @ResponseBody
    @RequestMapping
    public Result verify(HttpServletRequest request, @RequestBody LoginVo loginVo){
        log.info("verify");
        Result myResult=verifyService.verify(request,loginVo);
        return myResult;
    }
}
