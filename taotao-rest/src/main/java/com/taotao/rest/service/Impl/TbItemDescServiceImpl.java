package com.taotao.rest.service.Impl;

import com.taotao.common.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import com.taotao.rest.service.TbItemDescService;
import com.taotao.rest.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hmt
 * @date 2019/8/8 21:44
 */
@Service
public class TbItemDescServiceImpl implements TbItemDescService {

    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Override
    public TaotaoResult getTbItemDesc(long id) {
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);
        return TaotaoResult.ok(tbItemDesc);
    }
}
