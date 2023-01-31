package org.baichuan.borrow.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.baichuan.borrow.domin.LoginVo;


@Slf4j
public class AnalysisUtil {
    public static LoginVo analysis(LoginVo loginVo){
        //正则 url encode
       // URLEncoder.
        String[] urls=loginVo.getTableName().split(" ");
        if(urls.length>1){
            String[] params=urls[1].split(":");
            loginVo.setModule(params[1]);
        }
        loginVo.setTableName(urls[0]);
        log.info(JSONObject.toJSONString(loginVo));
        return loginVo;
    }
}
