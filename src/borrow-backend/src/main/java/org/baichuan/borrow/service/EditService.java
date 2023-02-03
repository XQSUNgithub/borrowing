package org.baichuan.borrow.service;

import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.result.Result;
import org.baichuan.borrow.util.CollectionIdGenerator;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Date;

import static org.baichuan.borrow.result.CodeMsg.REQUEST_ERROR;

public interface EditService {
    public default Result edit(HttpServletRequest request, LoginVo loginVo, UserDao userDao) throws ClassNotFoundException {
        if(loginVo.getValue().getString(loginVo.getKey())==null||loginVo.getValue().getString(loginVo.getKey()).length()==0)//修改,拿到的是一行数据
        return Result.error(REQUEST_ERROR);
        String id=loginVo.getValue().getString(loginVo.getKey());
        if(userDao.getByField(loginVo.getKey(),id,loginVo.getTableName())==null)
            return Result.error(REQUEST_ERROR);
        Field[] fields= Class.forName("org.baichuan.borrow.domin."+loginVo.getTableName()).getDeclaredFields();
        for(Field curField:fields){
            if(curField.getName().equals(loginVo.getKey()))
                    continue;
            if(loginVo.getValue().getString(curField.getName())==null)
                loginVo.getValue().put(curField.getName(),userDao.getByField(loginVo.getKey(),id,loginVo.getTableName()).getString(curField.getName()));
            userDao.editUser(loginVo.getTableName(),curField.getName(),loginVo.getValue().getString(curField.getName()),loginVo.getKey(),loginVo.getValue().getString(loginVo.getKey()));
            }
            return Result.success(loginVo.getValue());

    }
}
