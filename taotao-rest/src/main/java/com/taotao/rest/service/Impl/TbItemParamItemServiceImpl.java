package com.taotao.rest.service.Impl;

import com.taotao.common.TaotaoResult;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.rest.service.TbItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hmt
 * @date 2019/8/9 0:59
 */
@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {

    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;
    @Override
    public TaotaoResult getTbItemParam(long id) {

        TbItemParamItem tbItemParamItem = null;
        try {
            TbItemParamItemExample condition = new TbItemParamItemExample();
            condition.createCriteria().andItemIdEqualTo(id);
            List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExampleWithBLOBs(condition);
            if (tbItemParamItems == null || tbItemParamItems.size() < 0) {
                System.out.println("没有查询到id为" + id + "的商品信息");
                return TaotaoResult.ok("没有查询到商品信息");
            }
            tbItemParamItem = tbItemParamItems.get(0);
            return TaotaoResult.ok(tbItemParamItem);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.ok("没有查询到商品信息");
        }
    }
}