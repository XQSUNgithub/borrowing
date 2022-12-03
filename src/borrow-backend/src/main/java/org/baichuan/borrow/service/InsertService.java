package org.baichuan.borrow.service;
import com.alibaba.fastjson.JSONObject;
import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.domin.User;
import org.baichuan.borrow.result.Result;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Date;

@Service
public interface InsertService {
    public default Result insert(HttpServletRequest request, LoginVo loginVo, UserDao userDao) throws ClassNotFoundException {
        if(loginVo.getValue().getString(loginVo.getKey())!=null)//修改,拿到的是一行数据
        {
            //User oldUser=(User)userDao.getByField("uuid",loginVo.getUuid(),"user");
            Field[] fields= Class.forName("org.baichuan.borrow.domin."+loginVo.getTableName()).getDeclaredFields();
            for(Field curField:fields){
                userDao.editUser(loginVo.getTableName(),curField.getName(),loginVo.getValue().getString(curField.getName()),loginVo.getKey(),loginVo.getValue().getString(loginVo.getKey()));
            }
            return Result.success(loginVo.getValue());
        }


        Date time1 = new Date();
        String random= String.valueOf(time1.getTime()).substring(7,13)+ ((Double) Math.random()).toString().substring(4, 8);
        //返回id
        userDao.insertUser(loginVo.getTableName(),loginVo.getKey(),random);
        Field[] fields= Class.forName("org.baichuan.borrow.domin."+loginVo.getTableName()).getDeclaredFields();
        for(Field curField:fields){
            if(curField.getName().equals(loginVo.getKey()))
                continue;
            userDao.editUser(loginVo.getTableName(),curField.getName(),loginVo.getValue().getString(curField.getName()),loginVo.getKey(),loginVo.getValue().getString(loginVo.getKey()));
        }
        return Result.success(random);
    }
}
