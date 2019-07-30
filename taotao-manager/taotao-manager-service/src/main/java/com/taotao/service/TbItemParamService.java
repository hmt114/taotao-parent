package com.taotao.service;

import com.taotao.common.EasyUIResult;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import org.springframework.stereotype.Service;

/**
 * @author hmt
 * @date 2019/7/30 11:33
 */
@Service
public interface TbItemParamService {

    EasyUIResult getTbItemParamList(int pageNum,int pageSize);

    TaotaoResult getItemParamByItemCatId(long id);

    TaotaoResult deleteByIds(long ids);

    TaotaoResult addTbItemParam(String paramData,long catId);

}
