package org.baichuan.borrow.service;
import com.alibaba.fastjson.JSONObject;
import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.domin.User;
import org.baichuan.borrow.result.Result;
import org.baichuan.borrow.util.CollectionIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Date;

import static org.baichuan.borrow.result.CodeMsg.REQUEST_ERROR;

@Service
public interface InsertService {

    public default Result insert(HttpServletRequest request, LoginVo loginVo, UserDao userDao,CollectionIdGenerator collectionIdGenerator) throws ClassNotFoundException, IllegalAccessException {
        Date time1 = new Date();
        String random=null;
        switch (loginVo.getTableName()){
            case "User":
            case "Record":
                random= String.valueOf(time1.getTime()).substring(7,13)+ ((Double) Math.random()).toString().substring(4, 8);
            break;
            case "Collection":random=collectionIdGenerator.generateId(loginVo.getValue().getString("idInfo"));
            break;
            default:System.out.println("未知表");
            break;
        }

        if(random==null)
            return Result.error(REQUEST_ERROR);

        userDao.insertUser(loginVo.getTableName(),loginVo.getKey(),random);
        Field[] fields= Class.forName("org.baichuan.borrow.domin."+loginVo.getTableName()).getDeclaredFields();

        for(Field curField:fields){
            if(curField.getName().equals(loginVo.getKey()))
                continue;
            userDao.editUser(loginVo.getTableName(),curField.getName(),loginVo.getValue().getString(curField.getName()),loginVo.getKey(),random);
        }
        return Result.success(random);
    }
}
