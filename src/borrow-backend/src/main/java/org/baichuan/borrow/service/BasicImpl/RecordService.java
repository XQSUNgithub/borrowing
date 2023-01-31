package org.baichuan.borrow.service.BasicImpl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.domin.Record;
import org.baichuan.borrow.result.Result;
import org.baichuan.borrow.service.DeleteService;
import org.baichuan.borrow.service.EditService;
import org.baichuan.borrow.service.InsertService;
import org.baichuan.borrow.service.QueryService;
import org.baichuan.borrow.util.CollectionIdGenerator;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.baichuan.borrow.result.CodeMsg.REQUEST_ERROR;

@Slf4j
@Service
public class RecordService implements QueryService, InsertService, DeleteService, EditService {
    //审批
    //点击租借 插入状态为0的记录
    //1 查出0，2状态的记录
    public Result queryApproval(HttpServletRequest request, LoginVo loginVo, UserDao userDao) throws ClassNotFoundException {
        log.info("queryApproval");
        List<JSONObject> allData= (List<JSONObject>) this.query(request,loginVo,userDao).getData();
        List<JSONObject> res=new ArrayList<>();
        for(JSONObject cur:allData){
            if(cur.getString("type")!=null&&(cur.getString("type").equals("借阅")||cur.getString("type").equals("归还"))){
                res.add(cur);
            }
        }
        return Result.success(res);
    }
    /*public Result editApproval(HttpServletRequest request,LoginVo loginVo,UserDao userDao){
        loginVo.getValue().getString("time1")

    }*/
    //2 更改0为1，2为3
    //删除
    //删除某条记录 代表这个人的状态被还原到上一状态或删除
    public Result deleteApproval(HttpServletRequest request, LoginVo loginVo, UserDao userDao) throws ClassNotFoundException {
        if(loginVo.getValue().getString("type")!=null&&loginVo.getValue().getString("type").equals("借阅")){
            this.delete(request,loginVo,userDao);
            return Result.success(true);
        }
        if(loginVo.getValue().getString("type")!=null&&loginVo.getValue().getString("type").equals("归还")){
            Record lastRecord=new Record();
            lastRecord.setId(loginVo.getValue().getString("id"));
            lastRecord.setType("已借");
            loginVo.setValue((JSONObject) JSONObject.toJSON(lastRecord));
            this.edit(request,loginVo,userDao);
            return Result.success(loginVo.getValue());
        }
        return Result.error(REQUEST_ERROR);
    }

    //记录
    //1 查出1，3状态的记录
    public Result queryRecord(HttpServletRequest request, LoginVo loginVo, UserDao userDao) throws ClassNotFoundException {
        List<JSONObject> allData= (List<JSONObject>) this.query(request,loginVo,userDao).getData();
        List<JSONObject> res=new ArrayList<>();
        for(JSONObject cur:allData){
            if(cur.getString("type")!=null&&(cur.getString("type").equals("已借")||cur.getString("type").equals("已还"))){
                res.add(cur);
            }
        }
        return Result.success(res);
    }
    //该记录是否可删除？ 3号状态可以删除
    public Result deleteRecord(HttpServletRequest request, LoginVo loginVo, UserDao userDao) throws ClassNotFoundException {
        if(loginVo.getValue().getString("type")!=null&&loginVo.getValue().getString("type").equals("已还")){
            this.delete(request,loginVo,userDao);
            return Result.success(true);
        }
        return Result.error(REQUEST_ERROR);
    }

    public Result editWithTime(HttpServletRequest request, LoginVo loginVo, UserDao userDao, CollectionIdGenerator collectionIdGenerator) throws ClassNotFoundException, IllegalAccessException {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

        String now=formatter.format(date);
        switch(loginVo.getValue().getString("type")){
            case "借阅":{
                //赋值对应的time 然后正常插入
                JSONObject value=loginVo.getValue();
                value.put("time0",now);
                return this.insert(request,loginVo,userDao,collectionIdGenerator);
            }
            case "已借":{
                JSONObject value=loginVo.getValue();
                value.put("time1",now);
                return  this.edit(request,loginVo,userDao);
            }
            case "归还":{
                JSONObject value=loginVo.getValue();
                value.put("time2",now);
                return  this.edit(request,loginVo,userDao);
            }
            case"已还":{
                JSONObject value=loginVo.getValue();
                value.put("time3",now);
                return  this.edit(request,loginVo,userDao);
            }
            default:return Result.error(REQUEST_ERROR);
        }
    }
}
