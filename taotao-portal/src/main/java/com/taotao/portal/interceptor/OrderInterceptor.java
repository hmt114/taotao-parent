package com.taotao.portal.interceptor;

import com.taotao.common.CookieUtils;
import com.taotao.common.HttpUtil;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 订单模块拦截器
 * 用户只有登录后才能操作订单相关功能
 * @author Rabbit
 * @date 2019/8/8 16:40
 */
@Component
public class OrderInterceptor implements HandlerInterceptor{

    //注入登录路径
    @Value("${taotao.sso.login_url}")
    private String ssoLoginUrl;

    //注入token路径
    @Value("${taotao.sso.token_url}")
    private String ssoTokenUrl;

    //请求拦截器
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取cookie中的token
        String token = CookieUtils.getCookieValue(request,"TT_TOKEN");
        //获取用户原本想去的页面
        String redirectUrl = HttpUtil.getFullUrl(request);
        //如果用户没有token，就重定向到登录页，并且附加原始url
        if(StringUtils.isBlank(token)){
            response.sendRedirect(ssoLoginUrl+"?redirect="+redirectUrl);
            return false;
        }
        //拿cookie中获取的token信息，请求sso服务获取真正的用户信息
        String jsonData = HttpUtil.doGet(ssoTokenUrl+"/"+token);
        try {
            //用户token正常，将token对应的用户信息保存到request作用域中
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(jsonData, TbUser.class);
            if(taotaoResult.getStatus().equals(200)){
                TbUser tbUser = (TbUser) taotaoResult.getData();
                request.setAttribute("user",tbUser);
                return true;
            }
            //用户会话过期后的处理
            response.getWriter().print("<script>alert('登录已失效，请重新登录！');window.location.href='"+ssoLoginUrl+"'</script>");
            return false;
        } catch (Exception e) {
            System.out.println("无法获取用户信息！");
            return false;
        }
    }
}
