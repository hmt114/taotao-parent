package com.taotao.rest.service.Impl;

import com.taotao.common.TaotaoResult;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.rest.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hmt
 * @date 2019/8/8 9:14
 */
@Service
public class TbItemServiceImpl implements TbItemService {
    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public TaotaoResult getItemDetails(long id) {
        try {
            TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
            return TaotaoResult.ok(tbItem);
        } catch (Exception e) {
            return TaotaoResult.ok("失败");
        }
    }
}
