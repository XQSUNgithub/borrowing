package org.baichuan.borrow.service.BasicImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.domin.User;
import org.baichuan.borrow.result.Result;
import org.baichuan.borrow.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

import static org.baichuan.borrow.result.CodeMsg.USER_ERROR;

@Slf4j
@Service
public class UserService implements QueryService, InsertService,DeleteService, EditService {

}
