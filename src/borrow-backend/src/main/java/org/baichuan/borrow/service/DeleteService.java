package org.baichuan.borrow.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.result.Result;
import org.baichuan.borrow.domin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static org.baichuan.borrow.result.CodeMsg.USER_ERROR;

@Service
public interface DeleteService {


    public default Result delete(HttpServletRequest request, LoginVo loginVo, UserDao userDao) {
        if(loginVo.getValue().getString(loginVo.getKey())!=null){
            System.out.println(JSON.toJSONString(loginVo));
            userDao.deleteLine(loginVo.getKey(),loginVo.getValue().getString(loginVo.getKey()), loginVo.getTableName());
            return Result.success(true);
        }
        return Result.error(USER_ERROR);
    }
}
