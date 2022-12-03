package org.baichuan.borrow.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.domin.User;
import org.baichuan.borrow.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public interface QueryService {


     public default Result query(HttpServletRequest request, LoginVo loginVo,UserDao userDao) throws ClassNotFoundException {
        if(loginVo.getKeyword()==null||loginVo.getKeyword().length()==0)
        {// log.info(JSON.toJSONString(userDao.getallList("user")));
            return Result.success(userDao.getallList(loginVo.getTableName()));}
        List<JSONObject> res=new ArrayList<>();
        Set<String> keySet=new HashSet<>();
        String[] keywords=loginVo.getKeyword().split(" ");
        Field[] fields= Class.forName("org.baichuan.borrow.domin."+loginVo.getTableName()).getDeclaredFields();
       // log.info("开始模糊匹配");
      //  log.info(JSON.toJSONString(keywords));
      //  log.info(JSON.toJSONString(fields));
        for(String keyword:keywords){
            if(keyword.trim().length()==0) continue;
            for(Field curfield:fields){
                List<JSONObject> cur=userDao.getList(curfield.getName(),keyword.trim(),loginVo.getTableName());
                //log.info(JSONObject.toJSONString(cur));
                if(cur!=null){
                    for(JSONObject cur00:cur){
                     //   User cur0= (User) JSONObject.toJavaObject(cur00,Class.forName(loginVo.getTableName()));
                        if(!keySet.contains(cur00.getString(loginVo.getKeyname(loginVo.getTableName())))){
                            keySet.add(cur00.getString(loginVo.getKeyname(loginVo.getTableName())));
                            res.add(cur00);
                        }
                    }
                }
            }
        }
        return Result.success(res);
    }
}
