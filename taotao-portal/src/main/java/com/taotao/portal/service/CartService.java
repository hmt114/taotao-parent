package com.taotao.portal.service;

import com.taotao.common.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author hmt
 * @date 2019/8/11 18:36
 */
public interface CartService {
    List<CartItem> addItem(Long itemId, HttpServletRequest request, HttpServletResponse response);

    List<CartItem> getCartItemList(HttpServletRequest request);

    TaotaoResult updateNum(Long itemId, Long num, HttpServletRequest request, HttpServletResponse response);

    List<CartItem> deleteid(Long itemId, HttpServletRequest request, HttpServletResponse response);
}
