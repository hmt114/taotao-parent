package com.taotao.portal.web;

import com.taotao.common.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping("/update/num/{itemId}/{num}")
    public TaotaoResult modifyCart(@PathVariable Long itemId,@PathVariable Long num,
                                   HttpServletRequest request,HttpServletResponse response){
        return cartService.updateNum(itemId,num,request,response);
    }

    //删除购物车内容
    @RequestMapping(value = "/delete/{itemId}",method = RequestMethod.GET)
    public String deleteid(@PathVariable Long itemId,HttpServletResponse response,HttpServletRequest request,Model model){
        List<CartItem> cartItems= cartService.deleteid(itemId,request,response);
        model.addAttribute("cartList",cartItems);
        return "cart";
    }

    @GetMapping({"/cart","/show"})
    public String toCart(HttpServletRequest request,Model model){
        List<CartItem> cartItemList = cartService.getCartItemList(request);
        model.addAttribute("cartList", cartItemList);
        return "cart";
    }


}
