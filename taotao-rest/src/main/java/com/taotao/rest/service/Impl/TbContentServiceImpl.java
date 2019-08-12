package com.taotao.rest.service.Impl;

import com.taotao.common.JsonUtils;
import com.taotao.common.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.rest.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hmt
 * @date 2019/8/2 10:01
 */
@Service
public class TbContentServiceImpl implements TbContentService {

    @Autowired
    private TbContentMapper tbContentMapper;
    @Override
    public TaotaoResult getAllContent() {
        List<TbContent> tbContents = tbContentMapper.selectByExample(null);
        List<Map<String,Object>> contentItems = new ArrayList<>();
        for (TbContent tbContent:tbContents){
            Map<String,Object> contentItem = new HashMap<>();
            contentItem.put("srcB",tbContent.getPic2());
            contentItem.put("src",tbContent.getPic());
            contentItem.put("href",tbContent.getUrl());
            contentItem.put("alt",tbContent.getTitle());
            contentItem.put("height",240);
            contentItem.put("width",670);
            contentItem.put("heightB",240);
            contentItem.put("widthB",550);
            contentItems.add(contentItem);
        }
        return TaotaoResult.ok(JsonUtils.objectToJson(contentItems));
    }
}
