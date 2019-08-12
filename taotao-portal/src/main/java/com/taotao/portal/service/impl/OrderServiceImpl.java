package com.taotao.portal.service.impl;

import com.taotao.common.CookieUtils;
import com.taotao.common.HttpUtil;
import com.taotao.common.JsonUtils;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.pojo.OrderResult;
import com.taotao.portal.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hmt
 * @date 2019/8/12 11:58
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Value("${order.url}")
    private String orderBaseUrl;
    @Value("${taotao.sso.token_url}")
    private String ssoTokenUrl;

    @Override
    public OrderResult createOrder(Order order, HttpServletRequest request) {
        //获取登录用户信息
        TbUser tbUser = getUserInfo(request);
        order.setTbUser(tbUser);
        Map<String,String> params = new HashMap<>();
        params.put("data", JsonUtils.objectToJson(order));
        try {
            String json = HttpUtil.doPost(orderBaseUrl+"/create",params);
            System.out.println(json);
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json,OrderResult.class);
            //如果成功，返回OrderResult
            if(taotaoResult.getStatus().equals(200)){
                OrderResult orderResult = (OrderResult) taotaoResult.getData();
                return orderResult;
            }
        } catch (Exception e) {
            System.out.println("获取数据异常");
            e.printStackTrace();
        }
        return new OrderResult();
    }

    private TbUser getUserInfo(HttpServletRequest request) {
        // 获取Cookie中的token
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");


        // 拿从Cookie中获取的token信息请求SSO服务，获取真正的用户信息
        String jsonData = HttpUtil.doGet(ssoTokenUrl + "/" +token);
        try {
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(jsonData, TbUser.class);
            if(taotaoResult.getStatus().equals(200)) {
                TbUser tbUser = (TbUser) taotaoResult.getData();
                return tbUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
