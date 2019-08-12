package com.taotao.portal.service.impl;

import com.taotao.common.HttpUtil;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbItemDesc;
import com.taotao.portal.service.TbItemDescService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author hmt
 * @date 2019/8/8 20:51
 */
@Service
public class TbItemDescServiceImpl implements TbItemDescService {

    @Value("${rest.url}")
    private String restUrl;
    @Override
    public String getTbItemDesc(long id) {
        String jsonResult = HttpUtil.doGet(restUrl+"/item/desc/"+id);
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(jsonResult,TbItemDesc.class);
        if(taotaoResult.getStatus().equals(200)){
            TbItemDesc tbItemDesc = (TbItemDesc) taotaoResult.getData();
            return tbItemDesc.getItemDesc();
        }else {
            return "<span>暂无描述/span>";
        }

    }
}
