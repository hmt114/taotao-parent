package com.taotao.web;

import com.taotao.common.EasyUIResult;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.TbItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hmt
 * @date 2019/7/30 11:23
 */
@Controller
@RequestMapping("/item/param")
public class TbItemParamController {

    @Autowired
    private TbItemParamService tbItemParamService;


    @RequestMapping("/list")
    @ResponseBody
    public EasyUIResult getTbItemParamList(@RequestParam("page") int pageNum,@RequestParam("rows") int pageSize){
        EasyUIResult easyUIResult = tbItemParamService.getTbItemParamList(pageNum,pageSize);
        return easyUIResult;
    }

    @RequestMapping("/query/itemcatid/{id}")
    @ResponseBody
    public TaotaoResult getTbItemParamByCatId(@PathVariable long id){
        System.out.println(id);
        TaotaoResult taotaoResult = tbItemParamService.getItemParamByItemCatId(id);
        return taotaoResult;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteByIds(long ids){
        return tbItemParamService.deleteByIds(ids);
    }

    @RequestMapping("/save/{itemCatId}")
    @ResponseBody
    public TaotaoResult addTbItemParam( String paramData,@PathVariable("itemCatId") long catId){
        return tbItemParamService.addTbItemParam(paramData,catId);
    }
}
