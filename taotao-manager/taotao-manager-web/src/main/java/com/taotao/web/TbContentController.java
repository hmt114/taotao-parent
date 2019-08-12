package com.taotao.web;

import com.taotao.common.EUTreeNode;
import com.taotao.common.EasyUIResult;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hmt
 * @date 2019/8/3 17:08
 */
@RestController
@RequestMapping("/content")
public class TbContentController {
    @Autowired
    private TbContentService tbContentService;

    @RequestMapping("/query/list")
    public EasyUIResult getTbContentList(@RequestParam("categoryId") long categoryId,@RequestParam("page") int pageNum,@RequestParam("rows") int pageSize){
        return tbContentService.getTbContentList(categoryId,pageNum,pageSize);
    }

    @RequestMapping("/category/list")
    public List<EUTreeNode> getTbContentCategoryList(){
        return tbContentService.getTbContentCategoryId();
    }

    @RequestMapping("/delete")
    public TaotaoResult deleteByIds(@RequestParam("ids") long ids){
        return tbContentService.deleteByIds(ids);
    }

    @RequestMapping("/save")
    public TaotaoResult saveTbContent(TbContent tbContent) {
        return tbContentService.addTbContent(tbContent);
    }
}
