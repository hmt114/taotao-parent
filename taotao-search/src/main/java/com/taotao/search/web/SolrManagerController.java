package com.taotao.search.web;

import com.taotao.common.TaotaoResult;
import com.taotao.search.service.TbItemSolrManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hmt
 * @date 2019/8/6 20:45
 */
@Controller
@RequestMapping("/solr/manager")
public class SolrManagerController {

    @Autowired
    private TbItemSolrManagerService solrManagerService;

    @RequestMapping("/import_item")
    @ResponseBody
    public TaotaoResult importTbItem2Solr(){
        TaotaoResult result = solrManagerService.importAllTbItem2Solr();
        return result;
    }
}


