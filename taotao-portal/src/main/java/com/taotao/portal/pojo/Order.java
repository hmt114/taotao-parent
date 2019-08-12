package com.taotao.portal.pojo;

import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import com.taotao.pojo.TbUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author hmt
 * @date 2019/8/12 15:17
 */
@Getter
@Setter
public class Order extends TbOrder {
    private TbOrderShipping OrderShipping;
    private TbUser tbUser;
    private List<TbOrderItem> OrderItems;
}
