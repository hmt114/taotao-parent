package com.taotao.portal.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hmt
 * @date 2019/8/9 10:17
 */
@Getter
@Setter
public class CartItem {
    //商品id
    private Long id;
    //商品单价
    private Long price;
    //商品购买数量
    private Integer num;
    //商品图片地址
    private String images[];
    //商品标题
    private String title;

}
