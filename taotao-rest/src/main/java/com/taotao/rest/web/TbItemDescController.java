package com.taotao.rest.web;

import com.taotao.common.TaotaoResult;
import com.taotao.rest.service.TbItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hmt
 * @date 2019/8/8 21:19
 */
@RestController
public class TbItemDescController {

    @Autowired
    private TbItemDescService tbItemDescService;

    @RequestMapping("/item/desc/{id}")
    public TaotaoResult getTbItemDesc(@PathVariable long id){
        return tbItemDescService.getTbItemDesc(id);
    }
}
