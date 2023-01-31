package org.baichuan.borrow.service.BasicImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.domin.User;
import org.baichuan.borrow.result.Result;
import org.baichuan.borrow.service.DeleteService;
import org.baichuan.borrow.service.EditService;
import org.baichuan.borrow.service.InsertService;
import org.baichuan.borrow.service.QueryService;
import org.baichuan.borrow.util.CollectionIdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.tags.EditorAwareTag;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CollectionService implements QueryService, InsertService, DeleteService, EditService {


    public Result preDelete(HttpServletRequest request, LoginVo loginVo, UserDao userDao) throws ClassNotFoundException {
        return this.edit(request,loginVo,userDao);
    }
    public Result queryExists(HttpServletRequest request, LoginVo loginVo, UserDao userDao) throws ClassNotFoundException {
        List<JSONObject> allData= (List<JSONObject>) this.query(request,loginVo,userDao).getData();
        List<JSONObject> res=new ArrayList<>();
        for(JSONObject cur:allData){
            if(cur.getString("destroyed")!=null&&cur.getString("destroyed").equals("no")){
                res.add(cur);
            }
        }
        return Result.success(res);
    }
    public Result queryCollected(HttpServletRequest request, LoginVo loginVo, UserDao userDao) throws ClassNotFoundException {
        List<JSONObject> allData= (List<JSONObject>) this.query(request,loginVo,userDao).getData();
        List<JSONObject> res=new ArrayList<>();
        for(JSONObject cur:allData){
            if(cur.getString("destroyed")==null||(cur.getString("destroyed")!=null&!cur.getString("destroyed").equals("no"))){
                res.add(cur);
            }
        }
        return Result.success(res);
    }
    public Result insertCollection(HttpServletRequest request, LoginVo loginVo, UserDao userDao, CollectionIdGenerator collectionIdGenerator) throws ClassNotFoundException, IllegalAccessException {
        String id=loginVo.getValue().getString("recorderid");
        User myUser= JSON.toJavaObject(userDao.getByField("uuid",id,"user"),User.class);
        log.info(JSON.toJSONString(myUser));
        JSONObject value=loginVo.getValue();
        value.put("recorder",myUser.getRealname());
        return this.insert(request,loginVo,userDao,collectionIdGenerator);
    }
}
