package com.taotao.order.pojo;

/**
 * @author hmt
 * @date 2019/8/12 11:59
 * 存储订单创建成功后需要展示的数据
 */

public class OrderResult {
    private String orderId;
    private String  totalPayment;
    private String date;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
