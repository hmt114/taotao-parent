package com.taotao.order.web;

import com.taotao.common.TaotaoResult;
import com.taotao.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hmt
 * @date 2019/8/12 15:25
 */
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @RequestMapping("/create")
    public TaotaoResult createOrder(@RequestParam("data") String data){
        TaotaoResult taotaoResult = orderService.createOrder(data);
        return taotaoResult;
    }

}
