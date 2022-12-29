package org.baichuan.borrow.service;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
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
import javax.servlet.http.HttpServletResponse;

import static org.baichuan.borrow.result.CodeMsg.*;

@Slf4j
@Service
public class VerifyService {

    @Autowired
    UserDao userDao;


    public Result verify(HttpServletResponse response,HttpServletRequest request, LoginVo loginVo)throws Exception{
        String token = request.getHeader("Authorization");
        if(JwtUtil.checkToken(token)) {
            response.setStatus(403);
            return Result.error(TOKEN_ERROR);
        }
        String id= JwtUtil.getMemberIdByJwtToken(token);
        if(id==null){
            response.setStatus(403);
            return Result.error(TOKEN_ERROR);
        }
        log.info(id);
        User myUser= JSON.toJavaObject(userDao.getByField("uuid",id,"user"),User.class);
        if(myUser==null) {
            response.setStatus(403);
            return Result.error(USER_ERROR);
        }
        //检验action为null的情况
       /* if(myUser.getState()&&loginVo.getAction()!=null&&loginVo.getAction().length()!=0&&loginVo.getAction().equals("1")) {
            response.setStatus(403);
            return Result.error(USER_Forbidden);
        }*/
        return Result.success(myUser);
    }
}
