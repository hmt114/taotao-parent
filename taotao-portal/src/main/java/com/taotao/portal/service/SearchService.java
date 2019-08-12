package com.taotao.portal.service;


import com.taotao.portal.pojo.SearchResult;

/**
 * @author hmt
 * @date 2019/8/7 1:45
 */
public interface SearchService {
    SearchResult search(String keywords, Integer pageNum, Integer pageSize);
}
