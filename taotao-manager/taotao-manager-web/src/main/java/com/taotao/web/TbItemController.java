package com.taotao.web;

import com.taotao.common.EasyUIResult;
import com.taotao.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hmt
 * @date 2019/7/27 15:47
 */
@Controller
@RequestMapping("/item")
public class TbItemController {

    @Autowired
    private TbItemService tbItemService;

    @RequestMapping("/list")
    @ResponseBody
    public EasyUIResult getTbItemList(@RequestParam int page,@RequestParam int rows) {
        EasyUIResult result = tbItemService.getTbItemList(page, rows);
        return result;
    }
}
