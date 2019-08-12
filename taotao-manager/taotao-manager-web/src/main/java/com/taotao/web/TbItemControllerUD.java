package com.taotao.web;

import com.taotao.common.EasyUIResult;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hmt
 * @date 2019/7/30 20:37
 */
@Controller
@RequestMapping("/rest/item")
public class TbItemControllerUD {
    @Autowired
    private TbItemService tbItemService;

    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteTbItem(long ids){
        return tbItemService.deleteTbItem(ids);
    }

    @RequestMapping("/instock")
    @ResponseBody
    public TaotaoResult instockTbItem(long ids){
        return tbItemService.instockTbItem(ids);
    }


    @RequestMapping("/reshelf")
    @ResponseBody
    public TaotaoResult reshelfTbItem(long ids){
        return tbItemService.reshelfTbItem(ids);
    }

    //回显商品描述
    @RequestMapping("/query/item/desc/{id}")
    @ResponseBody
    public TaotaoResult showDesc(@PathVariable long id){
        return tbItemService.showDesc(id);
    }
    //回显商品规格
    @RequestMapping("/param/item/query/{id}")
    @ResponseBody
    public TaotaoResult showParam(@PathVariable long id){
        return tbItemService.showParamItem(id);
    }
    //更新商品
    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateTbItem(TbItem tbItem, String desc, String itemParams){
        return tbItemService.updateTbItem(tbItem,desc,itemParams);
    }


}
