package org.baichuan.borrow.service;


import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.domin.User;
import org.baichuan.borrow.result.Result;
import org.baichuan.borrow.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static org.baichuan.borrow.result.CodeMsg.*;

@Service
public class VerifyService {

    @Autowired
    UserDao userDao;


    public Result verify(HttpServletRequest request, LoginVo loginVo){
        String token = request.getHeader("Authorization");
        String id= JwtUtil.getMemberIdByJwtToken(token);
        if(id==null||!JwtUtil.checkToken(token)) return Result.error(TOKEN_ERROR);
        User myUser= (User) userDao.getByField("uuid",id,"user");
        if(myUser==null) return Result.error(USER_ERROR);
        if(myUser.getState()!=0&&loginVo.getAction().equals("0")) return Result.error(USER_Forbidden);
        return Result.success(true);
    }
}
