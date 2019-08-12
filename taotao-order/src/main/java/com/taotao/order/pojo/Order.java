package com.taotao.order.pojo;

import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import com.taotao.pojo.TbUser;

import java.util.List;

/**
 * @author hmt
 * @date 2019/8/12 15:17
 */

public class Order extends TbOrder {
    private TbOrderShipping OrderShipping;
    private TbUser tbUser;
    private List<TbOrderItem> OrderItems;

    public TbOrderShipping getOrderShipping() {
        return OrderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        OrderShipping = orderShipping;
    }

    public TbUser getTbUser() {
        return tbUser;
    }

    public void setTbUser(TbUser tbUser) {
        this.tbUser = tbUser;
    }

    public List<TbOrderItem> getOrderItems() {
        return OrderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        OrderItems = orderItems;
    }
}
