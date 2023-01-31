package org.baichuan.borrow.service.BasicImpl;

import com.alibaba.fastjson.JSONObject;
import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.result.Result;
import org.baichuan.borrow.service.DeleteService;
import org.baichuan.borrow.service.EditService;
import org.baichuan.borrow.service.InsertService;
import org.baichuan.borrow.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommonUserService implements QueryService, InsertService, EditService, DeleteService {

    @Autowired
    RecordService recordService;

    public Result commonApprovalQuery(HttpServletRequest request, LoginVo loginVo, UserDao userDao) throws ClassNotFoundException {
        List<JSONObject> allData= (List<JSONObject>) this.query(request,loginVo,userDao).getData();
        List<JSONObject> res=new ArrayList<>();//处于待审批状态的     //处于审批后状态的
        for(JSONObject cur:allData){
            if(cur.getString("uuid")!=null&&cur.getString("uuid").equals(loginVo.getValue().getString("uuid"))){
                if(cur.getString("type")!=null&&(cur.getString("type").equals("借阅")||cur.getString("type").equals("归还")))
                res.add(cur);
            }
        }
        return Result.success(res);
    }

    public Result commonRecordQuery(HttpServletRequest request, LoginVo loginVo, UserDao userDao) throws ClassNotFoundException {
        List<JSONObject> allData= (List<JSONObject>) this.query(request,loginVo,userDao).getData();
        List<JSONObject> res=new ArrayList<>();//处于待审批状态的     //处于审批后状态的
        for(JSONObject cur:allData){
            if(cur.getString("uuid")!=null&&cur.getString("uuid").equals(loginVo.getValue().getString("uuid"))){
                if(cur.getString("type")!=null&&(cur.getString("type").equals("已借")||cur.getString("type").equals("已还")))
                    res.add(cur);
            }
        }
        return Result.success(res);
    }



    public Result commonDelete (HttpServletRequest request, LoginVo loginVo, UserDao userDao) throws ClassNotFoundException {
       return recordService.deleteApproval(request,loginVo,userDao);
    }
}
