package org.baichuan.borrow.cotroller;


import org.baichuan.borrow.domin.User;
import org.baichuan.borrow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("serve/user")
public class UserController {
    //人员列表
    //录入人员
    //修改人员账号 密码 姓名 权限
    @Autowired
    UserService userService;
    @RequestMapping("/list")
    public List<User> userList(HttpServletRequest request){
        //由request的Authorization拿到jwt
        //jwt拿到uuid
        String uuid="123";
        //uuid查出user 查出
       // User myUser=
        List<User> userList=userService.list();
        return userList;
    }


}
