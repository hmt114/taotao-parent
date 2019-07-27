package com.taotao.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.taotao.common.EasyUIResult;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hmt
 * @date 2019/7/27 17:17
 */
@Service
public class TbItemServiceImpl implements TbItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public EasyUIResult getTbItemList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<TbItem> tbItemList = (Page<TbItem>) tbItemMapper.selectByExample(new TbItemExample());
        long total = tbItemList.getTotal();
        return new EasyUIResult(total,tbItemList);
    }
}
