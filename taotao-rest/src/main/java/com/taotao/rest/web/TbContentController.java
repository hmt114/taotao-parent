package com.taotao.rest.web;

import com.taotao.common.TaotaoResult;
import com.taotao.rest.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hmt
 * @date 2019/8/2 9:45
 */
@Controller
@RequestMapping("/content")
public class TbContentController {

    @Autowired
    private TbContentService tbContentService;
    @RequestMapping("/getall")
    @ResponseBody
    public TaotaoResult test(){
        TaotaoResult result = tbContentService.getAllContent();
        return result;
    }

}
