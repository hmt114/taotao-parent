package com.taotao.search.web;

import com.taotao.common.TaotaoResult;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author hmt
 * @date 2019/8/7 1:40
 */
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;
    @RequestMapping("/search")
    public TaotaoResult search(@RequestParam("q") String keywords, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "30") Integer pageSize) {
        SearchResult result = searchService.search(keywords, pageNum, pageSize);
        if(result == null) {
            return TaotaoResult.build(400, "搜索异常");
        }
        return TaotaoResult.ok(result);
    }
}
