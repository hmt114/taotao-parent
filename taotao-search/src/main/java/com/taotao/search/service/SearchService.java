package com.taotao.search.service;


import com.taotao.search.pojo.SearchResult;

/**
 * @author hmt
 * @date 2019/8/7 0:20
 */
public interface SearchService {
    SearchResult search(String keywords, Integer pageNum, Integer pageSize);
}
