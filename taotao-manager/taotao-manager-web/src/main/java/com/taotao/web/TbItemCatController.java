package com.taotao.web;

import com.taotao.common.EUTreeNode;
import com.taotao.service.TbItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author hmt
 * @date 2019/7/27 20:49
 */
@Controller
@RequestMapping("/item/cat")
public class TbItemCatController {

    @Autowired
    private TbItemCatService tbItemCatService;

    @RequestMapping("/list")
    @ResponseBody
    // 第一次前台没有传递过来数据，所以需要设置默认值
    public List<EUTreeNode> getTbItemList(@RequestParam(value = "id",defaultValue = "0") long parentId ){
        List<EUTreeNode> result = tbItemCatService.getItemCategoryList(parentId);
        return result;
    }

}
