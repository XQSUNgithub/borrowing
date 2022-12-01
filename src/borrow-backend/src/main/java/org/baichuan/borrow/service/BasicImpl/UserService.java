package org.baichuan.borrow.service.BasicImpl;

import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.domin.User;
import org.baichuan.borrow.result.Result;
import org.baichuan.borrow.service.InsertService;
import org.baichuan.borrow.service.QueryService;
import org.baichuan.borrow.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class UserService implements QueryService, InsertService {

    @Autowired
    VerifyService verifyService;

    @Autowired
    UserDao userDao;
    @Override
    public Result query(HttpServletRequest request, LoginVo loginVo) {
        //2 看是什么表
        //3 看查什么
        if(loginVo.getKeytype().isEmpty())
            return Result.success(userDao.getallList("uuid",loginVo.getKeyword(),"user"));
        if(loginVo.getKeytype().equals("uuid"))
        return Result.success(userDao.getByField(loginVo.getKeytype(), loginVo.getKeyword(), "user"));
        return Result.success(userDao.getList(loginVo.getKeytype(), loginVo.getKeyword(), "user"));
    }


    @Override
    public Result insert(HttpServletRequest request, LoginVo loginVo) {
        if(loginVo.getUuid()!=null)//修改
        {
            User oldUser=(User)userDao.getByField("uuid",loginVo.getUuid(),"user");
            User myUser=new User();
            myUser.setUuid(loginVo.getUuid());
            myUser.setRealname(loginVo.getRealname()==null?oldUser.getRealname():loginVo.getRealname());
            myUser.setPassword(loginVo.getPassword()==null? oldUser.getPassword():loginVo.getPassword());
            myUser.setState(loginVo.getState()==-1? oldUser.getState() : loginVo.getState());
            return Result.success(userDao.editUser(myUser)) ;
        }

        User myUser=new User();
        Date time1 = new Date();
        String random= String.valueOf(time1.getTime()).substring(7,13)+ ((Double) Math.random()).toString().substring(4, 8);
        myUser.setUuid(random);
        myUser.setPassword(loginVo.getPassword());
        myUser.setState(loginVo.getState());
        myUser.setRealname(loginVo.getRealname());
        return Result.success(userDao.insertUser(myUser));
    }
}
