package com.taotao.portal.service;

import com.taotao.portal.pojo.Order;
import com.taotao.portal.pojo.OrderResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hmt
 * @date 2019/8/12 11:57
 */
public interface OrderService {
    OrderResult createOrder(Order order, HttpServletRequest request);
}
