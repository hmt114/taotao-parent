package com.taotao.order.service.impl;

import com.taotao.common.*;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.pojo.Order;
import com.taotao.order.pojo.OrderResult;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author hmt
 * @date 2019/8/12 15:30
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;
    @Override
    @Transactional
    public TaotaoResult createOrder(String data) {
        System.out.println(JsonUtils.objectToJson(data));
        if(StringUtils.isBlank(data)){
            return TaotaoResult.build(500,"创建失败，订单信息不能为空");
        }
        try {
            Order order = JsonUtils.jsonToPojo(data, Order.class);
            System.out.println(JsonUtils.objectToJson(order));
            //写tb_order表
            //写tb_order_Item表
            //写tb_order_shipping表
            OrderResult orderResult = saveOrder2DB(order);
            System.out.println(JsonUtils.objectToJson(orderResult));
            return TaotaoResult.ok(orderResult);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500,"转换信息异常", ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 生成订单信息，保存到数据库
     * @param order 返回portal需要的订单信息
     * @return
     */
    private OrderResult saveOrder2DB(Order order) {
        OrderResult orderResult = new OrderResult();
        //生成订单id
        String orderId = IDUtils.genImageNameByUUID();
        //保存订单表Tb_Order
        TbOrder tbOrder = new TbOrder();
        tbOrder.setOrderId(orderId);
        tbOrder.setUserId(order.getTbUser().getId());
        tbOrder.setPaymentType(order.getPaymentType());
        tbOrder.setPayment(order.getPayment()+"");
        tbOrder.setStatus(SystemConstants.TAOTAO_ORDER_STATUS_NOPAY);
        tbOrder.setCreateTime(new Date());
        tbOrder.setShippingName("顺丰");
        tbOrder.setShippingCode("ER5201314521");
        tbOrder.setBuyerNick(order.getTbUser().getUsername());
        tbOrder.setBuyerMessage("快点发货吧~");
        tbOrderMapper.insert(tbOrder);
        //保存订单商品表Tb_Order_item
        List<TbOrderItem> orderItemList = order.getOrderItems();
        for(TbOrderItem tbOrderItem : orderItemList){
            String orderItemId = IDUtils.genImageNameByUUID();
            tbOrderItem.setId(orderItemId);
            tbOrderItem.setOrderId(orderId);
            tbOrderItemMapper.insert(tbOrderItem);
        }
        //保存物流信息表
        TbOrderShipping orderShipping = order.getOrderShipping();
        orderShipping.setOrderId(orderId);
        tbOrderShippingMapper.insert(orderShipping);
        //设置创建订单后的返回结果
        orderResult.setOrderId(orderId);
        //两天后plusDays
        String date = new DateTime().plusDays(2).toString("yyyy-MM-dd");
        orderResult.setDate(date);
        orderResult.setTotalPayment(order.getPayment());
        return orderResult;
    }
}
