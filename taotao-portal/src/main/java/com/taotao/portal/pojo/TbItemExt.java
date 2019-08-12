package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

/**
 * @author hmt
 * @date 2019/8/7 21:14
 * 商品图片可能有多张，所以继承以前的类扩展了一个方法
 */
public class TbItemExt extends TbItem {
    //将数据库中存储的用","隔开的image,用split分隔成数组
    public String[] getImages(){
        if(this.getImage() != null && this.getImage()!= ""){
            String[] strings = this.getImage().split(",");
            return strings;
        }
        return null;
    }
}
