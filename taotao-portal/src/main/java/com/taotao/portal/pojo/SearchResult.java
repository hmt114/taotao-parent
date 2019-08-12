package com.taotao.portal.pojo;

import com.taotao.pojo.SearchItem;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author hmt
 * @date 2019/8/7 1:29
 */
@Getter
@Setter
public class SearchResult implements Serializable {
    private long pageNum;
    private long totalPages;
    private List<SearchItem> itemList;
}
