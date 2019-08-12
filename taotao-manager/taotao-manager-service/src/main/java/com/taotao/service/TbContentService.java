package com.taotao.service;

import com.taotao.common.EUTreeNode;
import com.taotao.common.EasyUIResult;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * @author hmt
 * @date 2019/8/3 16:37
 */
public interface TbContentService {
    EasyUIResult getTbContentList(long categoryId,int pageNum,int pageSize);
    List<EUTreeNode> getTbContentCategoryId();
    TaotaoResult deleteByIds(long ids);

    TaotaoResult addTbContent(TbContent tbContent);
}
