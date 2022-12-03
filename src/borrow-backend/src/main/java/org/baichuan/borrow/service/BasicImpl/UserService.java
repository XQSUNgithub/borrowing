package org.baichuan.borrow.service.BasicImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.domin.User;
import org.baichuan.borrow.result.Result;
import org.baichuan.borrow.service.DeleteService;
import org.baichuan.borrow.service.InsertService;
import org.baichuan.borrow.service.QueryService;
import org.baichuan.borrow.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

import static org.baichuan.borrow.result.CodeMsg.USER_ERROR;

@Slf4j
@Service
public class UserService implements QueryService, InsertService,DeleteService {

    @Autowired
    VerifyService verifyService;

    @Autowired
    UserDao userDao;
    @Override
    public Result query(HttpServletRequest request, LoginVo loginVo) {
        if(loginVo.getKeyword()==null)
        { log.info(JSON.toJSONString(userDao.getallList("user")));
            return Result.success(userDao.getallList("user"));}
        List<User> res=new ArrayList<>();
        Set<String> uuidSet=new HashSet<>();
        String[] keywords=loginVo.getKeyword().split(" ");
        Field[] fields=User.class.getDeclaredFields();
        log.info("开始模糊匹配");
        log.info(JSON.toJSONString(keywords));
        log.info(JSON.toJSONString(fields));
        for(String keyword:keywords){
            if(keyword.trim().length()==0) continue;
            for(Field curfield:fields){
                List<JSONObject> cur=userDao.getList(curfield.getName(),keyword.trim(),"user");
                log.info(JSONObject.toJSONString(cur));
                if(cur!=null){
                    for(JSONObject cur00:cur){
                        User cur0=JSONObject.toJavaObject(cur00,User.class);
                        if(!uuidSet.contains(cur0.getUuid())){
                            uuidSet.add(cur0.getUuid());
                            res.add(cur0);
                        }
                    }
                }
            }
        }
        return Result.success(res);
    }


    @Override
    public Result insert(HttpServletRequest request, LoginVo loginVo) {
        if(loginVo.getUuid()!=null)//修改
        {
            //User oldUser=(User)userDao.getByField("uuid",loginVo.getUuid(),"user");
            User myUser=new User();
            myUser.setUuid(loginVo.getUuid());
            myUser.setRealname(loginVo.getRealname());
            myUser.setPassword(loginVo.getPassword());
            myUser.setState(loginVo.getState());
            userDao.editUser(myUser);
            return Result.success(myUser) ;
        }

        User myUser=new User();
        Date time1 = new Date();
        String random= String.valueOf(time1.getTime()).substring(7,13)+ ((Double) Math.random()).toString().substring(4, 8);
        myUser.setUuid(random);
        myUser.setPassword(loginVo.getPassword());
        myUser.setState(loginVo.getState());
        myUser.setRealname(loginVo.getRealname());
        //返回id
        userDao.insertUser(myUser);
        return Result.success(myUser);
    }

    @Override
    public Result delete(HttpServletRequest request, LoginVo loginVo) {
        if(loginVo.getUuid()!=null){
            log.info(JSON.toJSONString(loginVo));
            userDao.deleteLine("uuid",loginVo.getUuid(),"user");
            return Result.success(true);
        }
        return Result.error(USER_ERROR);
    }
}
