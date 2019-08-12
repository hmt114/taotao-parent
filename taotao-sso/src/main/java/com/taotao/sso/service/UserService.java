package com.taotao.sso.service;

import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hmt
 * @date 2019/8/10 16:54
 */
public interface UserService {
    TaotaoResult checkData(String param, int type);
    TaotaoResult register(TbUser tbUser);
    TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response);
    TaotaoResult getUserByToken(String token);
    TaotaoResult deleteUserByToken(String token, HttpServletRequest request, HttpServletResponse response);
}
