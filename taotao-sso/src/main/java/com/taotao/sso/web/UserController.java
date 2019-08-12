package com.taotao.sso.web;

import com.taotao.common.JsonUtils;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hmt
 * @date 2019/8/10 16:41
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/check/{param}/{type}")
    public TaotaoResult checkDate(@PathVariable String param,@PathVariable Integer type){
        TaotaoResult taotaoResult = userService.checkData(param,type);
        return taotaoResult;
    }

    @RequestMapping(value = "/register" , method = RequestMethod.POST)
    public TaotaoResult registerUser(TbUser tbUser){
        return userService.register(tbUser);
    }

    @RequestMapping(value = "/login" , method = RequestMethod.POST)
    public TaotaoResult login(String username, String password, HttpServletRequest req, HttpServletResponse resp){
        return userService.login(username,password,req,resp);
    }
    @CrossOrigin(origins = {"localhost","127.0.0.1","taotao.com"})
    @RequestMapping(value = "/logout/{token}" ,method = RequestMethod.GET)
    public TaotaoResult deleteLogin(@PathVariable String token,HttpServletRequest request,HttpServletResponse response){
        return userService.deleteUserByToken(token,request,response);
    }
//token换用户信息
    @CrossOrigin(origins = {"localhost", "127.0.0.1", "www.taotao.com"})
    @RequestMapping(value = "/token/{token}", method = RequestMethod.GET)
    public Object getUserByToken(@PathVariable String token, @RequestParam(required = false) String callback) {
        TaotaoResult taotaoResult = userService.getUserByToken(token);
        if(!StringUtils.isBlank(callback)) {
            return callback+"(" + JsonUtils.objectToJson(taotaoResult) +");";
        }
        return taotaoResult;
    }
}
