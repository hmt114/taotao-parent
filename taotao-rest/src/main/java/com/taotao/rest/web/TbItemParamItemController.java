package com.taotao.rest.web;

import com.taotao.common.TaotaoResult;
import com.taotao.rest.service.TbItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hmt
 * @date 2019/8/9 0:53
 */
@Controller
public class TbItemParamItemController {

    @Autowired
    private TbItemParamItemService tbItemParamItemService;
    @RequestMapping("/item/param/{id}")
    @ResponseBody
    public TaotaoResult getTbItemParam(@PathVariable long id){
        TaotaoResult taotaoResult = tbItemParamItemService.getTbItemParam(id);
        return taotaoResult;
    }
}
