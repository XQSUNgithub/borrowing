package org.baichuan.borrow.service;

import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.result.Result;

import javax.servlet.http.HttpServletRequest;

public interface InsertService {
    public Result insert(HttpServletRequest request, LoginVo loginVo);
}
