package com.taotao.service;

import com.taotao.common.EasyUIResult;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbItem;
import org.springframework.stereotype.Service;

/**
 * @author hmt
 * @date 2019/7/27 17:09
 */
@Service
public interface TbItemService {

    EasyUIResult getTbItemList(int pageNum, int pageSize);

    TaotaoResult addTbItem(TbItem tbItem, String desc, String tbItemParams);

    TaotaoResult deleteTbItem(long ids);

    TaotaoResult instockTbItem(long ids);

    TaotaoResult reshelfTbItem(long ids);

    TaotaoResult showDesc(long ids);

    TaotaoResult showParamItem(long ids);

    TaotaoResult updateTbItem(TbItem tbItem, String desc, String itemParams);

}
