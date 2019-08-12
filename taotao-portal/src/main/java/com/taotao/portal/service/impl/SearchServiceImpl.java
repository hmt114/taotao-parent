package com.taotao.portal.service.impl;

import com.taotao.common.HttpUtil;
import com.taotao.common.TaotaoResult;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hmt
 * @date 2019/8/7 11:26
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Value("${search.url}")
    private String searchUrl;

    @Override
    public SearchResult search(String keywords, Integer pageNum, Integer pageSize) {
        Map<String, String> params = new HashMap<>();
        params.put("q", keywords);
        params.put("pageNum", pageNum + "");
        params.put("pageSize", pageSize + "");
        String searchResultJson = HttpUtil.doPost(searchUrl + "/search", params);
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(searchResultJson, SearchResult.class);
        if (taotaoResult.getStatus().equals(200)) {
            return (SearchResult) taotaoResult.getData();
        }
        return null;
    }
}
