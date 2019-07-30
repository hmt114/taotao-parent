package com.taotao.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.taotao.common.EasyUIResult;
import com.taotao.common.IDUtils;
import com.taotao.common.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author hmt
 * @date 2019/7/27 17:17
 */
@Service
public class TbItemServiceImpl implements TbItemService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Override
    public EasyUIResult getTbItemList(int pageNum, int pageSize) {
        // 获取pageNum页，pageSize条内容
        PageHelper.startPage(pageNum,pageSize);
        // 紧跟着的第一个select方法会被分页
        Page<TbItem> tbItemList = (Page<TbItem>) tbItemMapper.selectByExample(new TbItemExample());
        long total = tbItemList.getTotal();
        return new EasyUIResult(total,tbItemList);
    }

    @Override
    public TaotaoResult addTbItem(TbItem tbItem, String desc,String itemParams) {
        long itemId = IDUtils.genItemId();
        tbItem.setId(itemId);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        tbItem.setStatus((byte)1);
        tbItemMapper.insert(tbItem);

        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setCreated(tbItem.getCreated());
        tbItemDesc.setUpdated(tbItem.getUpdated());
        tbItemDesc.setItemDesc(desc);
        tbItemDescMapper.insert(tbItemDesc);

        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParams);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        tbItemParamItemMapper.insert(itemParamItem);

        TaotaoResult taotaoResult = TaotaoResult.ok();
        return taotaoResult;
    }

    @Override
    public TaotaoResult deleteTbItem(long ids) {
        TbItem tbItem = new TbItem();
        tbItem.setId(ids);
        tbItem.setStatus((byte) 3);
        tbItemMapper.updateByPrimaryKeySelective(tbItem);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult instockTbItem(long ids) {
        TbItem tbItem = new TbItem();
        tbItem.setId(ids);
        tbItem.setStatus((byte) 2);
        tbItemMapper.updateByPrimaryKeySelective(tbItem);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult reshelfTbItem(long ids) {
        TbItem tbItem = new TbItem();
        tbItem.setId(ids);
        tbItem.setStatus((byte) 1);
        tbItemMapper.updateByPrimaryKeySelective(tbItem);
        return TaotaoResult.ok();
    }


}
