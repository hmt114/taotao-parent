
package com.taotao.search.service;

import com.taotao.common.JsonUtils;
import com.taotao.search.pojo.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/*
 * @author hmt
 * @date 2019/8/7 11:52
 */


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSearchService {

    @Autowired
    private SearchService searchService;

    @Test
    public void search(){
        SearchResult result = searchService.search("手机",1,20);
        System.out.println(JsonUtils.objectToJson(result));
    }

}

