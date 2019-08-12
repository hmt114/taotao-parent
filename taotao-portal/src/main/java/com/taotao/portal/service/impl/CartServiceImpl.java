package com.taotao.portal.service.impl;

import com.taotao.common.CookieUtils;
import com.taotao.common.HttpUtil;
import com.taotao.common.JsonUtils;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hmt
 * @date 2019/8/11 18:36
 */
@Service
public class CartServiceImpl implements CartService {
    //服务url
    @Value("${rest.url}")
    private String restBaseUrl;

    @Override
    public List<CartItem> addItem(Long itemId, HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> cartItemList = new ArrayList<>();
        if(itemId == null){
            return cartItemList;
        }
        //先从cookie中取购物车商品列表
        LoadItemListFromCart(cartItemList,request);
        //将用户选择的商品添加到购物车
        add2Cart(cartItemList,itemId);
        //将新的购物车信息写入cookie,商品标题中有中文，但cookie不能存中文，所以编码转换
        CookieUtils.setCookie(request,response,"TT_CART", JsonUtils.objectToJson(cartItemList),true);
        return cartItemList;
    }

    @Override
    public List<CartItem> getCartItemList(HttpServletRequest request) {
        List<CartItem> cartItemList = new ArrayList<>();
        LoadItemListFromCart(cartItemList,request);
        return cartItemList;
    }

    private void LoadItemListFromCart(List<CartItem> cartItemList,HttpServletRequest request){
        try {
            String json = CookieUtils.getCookieValue(request,"TT_CART",true);
            if(!StringUtils.isBlank(json)){
                List<CartItem> cookieCartItemList = JsonUtils.jsonToList(json,CartItem.class);
                cartItemList.addAll(cookieCartItemList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("从cookie中获取数据出错");
        }

    }

    private void add2Cart(List<CartItem> cartItemList,Long itemId){
        String json = HttpUtil.doGet(restBaseUrl+"/item/get_item_detail/"+itemId.longValue());
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItem.class);
        if (taotaoResult != null && taotaoResult.getStatus().equals(200)){
            TbItem tbItem = (TbItem) taotaoResult.getData();
            //如果购物车中有商品，则将此商品购买数量+1
            boolean isExits = false;
            for (CartItem cartItem:cartItemList){
                if(tbItem.getId().equals(cartItem.getId())){
                    cartItem.setNum(cartItem.getNum()+1);
                    isExits = true;
                }
            }
            //如果没有，那将此商品添加进去，设置数量为1
            if(!isExits){
                CartItem cartItem = new CartItem();
                cartItem.setId(tbItem.getId());
                cartItem.setNum(1);
                cartItem.setTitle(tbItem.getTitle());
                cartItem.setImages(tbItem.getImage() == null ? new String[0] : tbItem.getImage().split(","));
                cartItem.setPrice(tbItem.getPrice());
                cartItemList.add(cartItem);
            }
        }
    }
}
