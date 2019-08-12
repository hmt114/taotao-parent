package com.taotao.search.service.impl;

import com.taotao.pojo.SearchItem;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author hmt
 * @date 2019/8/7 0:23
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SolrClient solrClient;
    @Override
    public SearchResult search(String keywords, Integer pageNum, Integer pageSize) {
        SolrQuery query = new SolrQuery();
        //设置查询关键字
        query.setQuery(keywords);
        //设置默认查询字段
        query.set("df","item_keywords");
        //设置分页参数
        //设置从第几个数据开始分
        query.setStart((pageNum-1)*pageSize);
        query.setRows(pageSize);
        // 设置关键词高亮显示
        query.setHighlight(true);
        query.setHighlightSimplePre("<em style='color:red;'>");
        query.setHighlightSimplePost("</em>");

        try {
            QueryResponse resp = solrClient.query(query);
            SolrDocumentList resultList = resp.getResults();
            SearchResult searchResult = new SearchResult();
            //获取搜索结果
            List<SearchItem> searchItemList = parseSearchDocument(resultList);
            searchResult.setItemList(searchItemList);

            // 获取搜索到的总记录数
            long totalCount = resultList.getNumFound();
            // 计算总页数
            long totalPages = totalCount / pageSize;
            if(totalCount % pageSize != 0) {
                totalPages ++;
            }
            searchResult.setPageNum(pageNum.longValue());
            searchResult.setTotalPages(totalPages);
            return searchResult;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将solr查询的结果封装成SearchItem对象
     * @param resultList
     * @return
     */
    private List<SearchItem> parseSearchDocument(SolrDocumentList resultList) {
        List<SearchItem> searchItemList = new ArrayList<>();
        for(Iterator<SolrDocument> it = resultList.iterator(); it.hasNext();) {
            SolrDocument doc = it.next();
            String title = (String) doc.getFieldValue("item_title");
            String image = (String) doc.getFieldValue("item_image");
            long id = Long.parseLong((String) doc.getFieldValue("id"));
            long price = Long.parseLong((String) doc.getFieldValue("item_price"));
            String sellPoint = (String) doc.getFieldValue("item_sell_point");
            String categoryName = (String) doc.getFieldValue("item_category_name");
            String desc = (String) doc.getFieldValue("item_desc");
            SearchItem searchItem = new SearchItem();
            searchItem.setId(id);
            if(image == null || image == ""){
                searchItem.setImage(image);
            }else {
                searchItem.setImage(image.split(",")[0]);
            }
            searchItem.setTitle(title);
            searchItem.setPrice(price);
            searchItem.setCategoryName(categoryName);
            searchItem.setItemDesc(desc);
            searchItem.setSellPoint(sellPoint);
            searchItemList.add(searchItem);
        }
        return searchItemList;
    }

}
