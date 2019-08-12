package com.taotao.portal.web;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.taotao.pojo.TbOrderShipping;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.pojo.OrderResult;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author hmt
 * @date 2019/8/12 11:23
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @RequestMapping("/order-cart")
    public String toOrderCart(HttpServletRequest request, Model model){
        List<CartItem> cartItemList = cartService.getCartItemList(request);
        model.addAttribute("cartList",cartItemList);
        return "order-cart";
    }

    @RequestMapping("/create")
    public String toCreateOrder(Order order, Model model,HttpServletRequest request){
        OrderResult orderResult = orderService.createOrder(order,request);
        model.addAttribute("orderId",orderResult.getOrderId());
        model.addAttribute("payment",orderResult.getTotalPayment());
        model.addAttribute("date",orderResult.getDate());
        return "success";
    }
}
