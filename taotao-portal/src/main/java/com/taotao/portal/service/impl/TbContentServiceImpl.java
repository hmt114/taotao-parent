package com.taotao.portal.service.impl;

import com.taotao.common.HttpUtil;
import com.taotao.common.JsonUtils;
import com.taotao.common.TaotaoResult;
import com.taotao.portal.service.TbContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author hmt
 * @date 2019/8/2 9:31
 */
@Service
public class TbContentServiceImpl implements TbContentService {

    @Value("${rest.url}")
    private String restUrl;

    @Override
    public String getContent() {

        //发送请求到rest服务，接收数据，轮播图
        String content = HttpUtil.doGet(restUrl+"/content/getall");
        TaotaoResult taotaoResult = JsonUtils.jsonToPojo(content,TaotaoResult.class);
        if(taotaoResult.getStatus().equals(200)){
            return (String) taotaoResult.getData();
        }
        return "加载数据异常...";
    }
}
