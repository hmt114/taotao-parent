package com.taotao.portal.web;

import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author hmt
 * @date 2019/8/11 18:33
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @RequestMapping("/add/{itemId}")
    public String addItem(@PathVariable Long itemId, HttpServletRequest request,
                          HttpServletResponse response, Model model) {
        List<CartItem> cartItemList = cartService.addItem(itemId,request,response);
        //把购物车中的商品传递给页面
        model.addAttribute("cartList", cartItemList);
        return "cart";
    }
    @GetMapping({"/cart","/show"})
    public String toCart(HttpServletRequest request,Model model){
        List<CartItem> cartItemList = cartService.getCartItemList(request);
        model.addAttribute("cartList", cartItemList);
        return "cart";
    }


}
