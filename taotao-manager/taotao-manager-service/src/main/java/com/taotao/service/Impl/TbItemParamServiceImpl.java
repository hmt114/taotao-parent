package com.taotao.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.taotao.common.EasyUIResult;
import com.taotao.common.TaotaoResult;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.TbItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author hmt
 * @date 2019/7/30 11:45
 */
@Service
public class TbItemParamServiceImpl implements TbItemParamService {

    @Autowired
    private TbItemParamMapper tbItemParamMapper;
    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public EasyUIResult getTbItemParamList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbItemParam> paramPage = (Page<TbItemParam>) tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
        List<TbItemCat> itemCatPage = tbItemCatMapper.selectByExample(new TbItemCatExample());

        // 连表查询功能
        for (TbItemParam param : paramPage) {
            for (TbItemCat cat : itemCatPage) {
                if (param.getItemCatId().equals(cat.getId())){
                    param.setItemCatName(cat.getName());
                }
            }
        }

        // 设置返回的数据
        long total = paramPage.getTotal();
        return new EasyUIResult(total, paramPage.getResult());
    }

    @Override
    public TaotaoResult getItemParamByItemCatId(long id) {
        TbItemParamExample tbItemParamExample = new TbItemParamExample();
        tbItemParamExample.createCriteria().andItemCatIdEqualTo(id);
        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(tbItemParamExample);
        if(tbItemParams.size() > 0){
            return TaotaoResult.ok(tbItemParams.get(0));
        }
        return TaotaoResult.build(400, "未查询到数据");
    }

    @Override
    public TaotaoResult deleteByIds(long ids) {
        TbItemParamExample example = new TbItemParamExample();
        example.createCriteria().andIdEqualTo(ids);
        tbItemParamMapper.deleteByExample(example);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult addTbItemParam(String paramData,long  catId) {
        TbItemParam tbItemParam = new TbItemParam();
        tbItemParam.setItemCatId(catId);
        tbItemParam.setCreated(new Date());
        tbItemParam.setUpdated(new Date());
        tbItemParam.setParamData(paramData);
        tbItemParamMapper.insert(tbItemParam);
        return TaotaoResult.ok();
    }
}
