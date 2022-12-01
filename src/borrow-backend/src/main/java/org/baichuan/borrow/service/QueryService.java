package org.baichuan.borrow.service;

import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.result.Result;

import javax.servlet.http.HttpServletRequest;

public interface QueryService {
    public  Result query(HttpServletRequest request, LoginVo loginVo);
}
