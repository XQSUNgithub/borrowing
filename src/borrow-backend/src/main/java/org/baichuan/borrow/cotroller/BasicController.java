package org.baichuan.borrow.cotroller;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.result.Result;
import org.baichuan.borrow.service.BasicImpl.CollectionService;
import org.baichuan.borrow.service.BasicImpl.CommonUserService;
import org.baichuan.borrow.service.BasicImpl.RecordService;
import org.baichuan.borrow.service.LoginService;
import org.baichuan.borrow.service.BasicImpl.UserService;
import org.baichuan.borrow.service.VerifyService;
import org.baichuan.borrow.util.AnalysisUtil;
import org.baichuan.borrow.util.CollectionIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.baichuan.borrow.result.CodeMsg.REQUEST_ERROR;

@Slf4j
@Controller
@RequestMapping("/")
public class BasicController {
    //人员列表
    //录入人员
    //修改人员账号 密码 姓名 权限
    @Autowired
    UserDao userDao;

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;
    @Autowired
    VerifyService verifyService;
    @Autowired
    CollectionService collectionService;

    @Autowired
    RecordService recordService;

    @Autowired
    CommonUserService commonUserService;

    @Autowired
    CollectionIdGenerator collectionIdGenerator;

    @RequestMapping("query")
    @ResponseBody
    public Result query(HttpServletResponse response,HttpServletRequest request, @RequestBody LoginVo loginVo) throws Exception {
        log.info("query");
        log.info(JSONObject.toJSONString(loginVo));
        Result myResult=verifyService.verify(response,request,loginVo);
        if(myResult.getMsg()!=null) return  myResult;
        log.info("鉴权成功");
        loginVo=AnalysisUtil.analysis(loginVo);
        switch (loginVo.getTableName()){
            case "User" :return userService.query(request,loginVo,userDao);
            case "Collection":{
                if(loginVo.getModule()!=null&&loginVo.getModule().equals("collected"))
                    return collectionService.queryCollected(request,loginVo,userDao);
                return collectionService.queryExists(request,loginVo,userDao);
            }
            case "Record": {
                if(loginVo.getModule()!=null&&loginVo.getModule().equals("approval"))
                    return recordService.queryApproval(request,loginVo,userDao);
                if(loginVo.getModule()!=null&&loginVo.getModule().equals("record"))
                    return recordService.queryRecord(request,loginVo,userDao);
                if(loginVo.getModule()!=null&&loginVo.getModule().equals("commonApproval"))
                    return commonUserService.commonApprovalQuery(request,loginVo,userDao);
                if(loginVo.getModule()!=null&&loginVo.getModule().equals("commonRecord"))
                    return commonUserService.commonRecordQuery(request,loginVo,userDao);
                return Result.error(REQUEST_ERROR);
            }
        }
        return Result.error(REQUEST_ERROR);
    }
    @RequestMapping("insert")
    @ResponseBody
    public Result insert(HttpServletResponse response,HttpServletRequest request, @RequestBody LoginVo loginVo) throws Exception {
        log.info("insert");
       // LoginVo loginVo=JSONObject.toJavaObject(loginVo0,LoginVo.class);
        log.info(JSONObject.toJSONString(loginVo));
        Result myResult=verifyService.verify(response,request,loginVo);
        if(myResult.getMsg()!=null) return  myResult;
        log.info("鉴权成功");
        loginVo=AnalysisUtil.analysis(loginVo);
        switch (loginVo.getTableName()){
            case "User" :return userService.insert(request,loginVo,userDao,collectionIdGenerator);
            case "Collection":return collectionService.insertCollection(request,loginVo,userDao,collectionIdGenerator);
        }
        return Result.error(REQUEST_ERROR);
    }

     //用户借阅时插入记录
    @RequestMapping("borrow")
    @ResponseBody
    public Result borrow(HttpServletResponse response,HttpServletRequest request, @RequestBody LoginVo loginVo) throws Exception {
        log.info("borrow");
        // LoginVo loginVo=JSONObject.toJavaObject(loginVo0,LoginVo.class);
        log.info(JSONObject.toJSONString(loginVo));
        Result myResult=verifyService.verify(response,request,loginVo);
        if(myResult.getMsg()!=null) return  myResult;
        log.info("鉴权成功");
        loginVo=AnalysisUtil.analysis(loginVo);
        switch (loginVo.getTableName()){
            case "Record": return recordService.editWithTime(request,loginVo,userDao,collectionIdGenerator);
        }
        return Result.error(REQUEST_ERROR);
    }

    @RequestMapping("edit")
    @ResponseBody
    public Result edit(HttpServletResponse response,HttpServletRequest request, @RequestBody LoginVo loginVo) throws Exception {
        log.info("edit");
        // LoginVo loginVo=JSONObject.toJavaObject(loginVo0,LoginVo.class);
        log.info(JSONObject.toJSONString(loginVo));
        Result myResult=verifyService.verify(response,request,loginVo);
        if(myResult.getMsg()!=null) return  myResult;
        log.info("鉴权成功");
        loginVo=AnalysisUtil.analysis(loginVo);
        switch (loginVo.getTableName()){
            case "User" :return userService.edit(request,loginVo,userDao);
            case "Collection":return collectionService.edit(request,loginVo,userDao);
            case "Record": return recordService.editWithTime(request,loginVo,userDao,collectionIdGenerator);
        }
        return Result.error(REQUEST_ERROR);
    }

    @RequestMapping("delete")
    @ResponseBody
    public Result delete(HttpServletResponse response,HttpServletRequest request,@RequestBody LoginVo loginVo) throws Exception {
        log.info("delete");
       // LoginVo loginVo=JSONObject.toJavaObject(loginVo0,LoginVo.class);
        log.info(JSONObject.toJSONString(loginVo));
        Result myResult=verifyService.verify(response,request,loginVo);
        if(myResult.getMsg()!=null) return  myResult;
        log.info("鉴权成功");
        loginVo=AnalysisUtil.analysis(loginVo);
        switch (loginVo.getTableName()){
            case "User" :return userService.delete(request,loginVo,userDao);
            case "Collection":{
                if(loginVo.getModule()!=null&&loginVo.getModule().equals("realDelete"))
                return collectionService.delete(request,loginVo,userDao);
                return collectionService.preDelete(request,loginVo,userDao);
            }
            case "Record": {
                if(loginVo.getModule()!=null&&loginVo.getModule().equals("approval"))
                    return recordService.deleteApproval(request,loginVo,userDao);
                if(loginVo.getModule()!=null&&loginVo.getModule().equals("record"))
                    return recordService.deleteRecord(request,loginVo,userDao);
                if(loginVo.getModule()!=null&&loginVo.getModule().equals("common"))
                    return commonUserService.commonDelete(request,loginVo,userDao);
                return Result.error(REQUEST_ERROR);
            }
        }
        return Result.error(REQUEST_ERROR);
    }

}
