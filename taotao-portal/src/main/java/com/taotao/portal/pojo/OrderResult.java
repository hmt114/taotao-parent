package com.taotao.portal.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hmt
 * @date 2019/8/12 11:59
 * 存储订单创建成功后需要展示的数据
 */
@Getter
@Setter
public class OrderResult {
    private String orderId;
    private String totalPayment;
    private String date;
}
