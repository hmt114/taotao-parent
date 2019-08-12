package com.taotao.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.taotao.common.EUTreeNode;
import com.taotao.common.EasyUIResult;
import com.taotao.common.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hmt
 * @date 2019/8/3 16:42
 */
@Service
public class TbContentServiceImpl implements TbContentService {

    @Autowired
    private TbContentMapper tbContentMapper;
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Override
    public EasyUIResult getTbContentList(long categoryId,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        TbContentExample condition = new TbContentExample();
        condition.createCriteria().andCategoryIdEqualTo(categoryId);
        Page<TbContent> tbContents = (Page<TbContent>) tbContentMapper.selectByExampleWithBLOBs(condition);
        return new EasyUIResult(tbContents.getTotal(),tbContents.getResult());
    }

    @Override
    public List<EUTreeNode> getTbContentCategoryId() {
        List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(new TbContentCategoryExample());
        List<EUTreeNode> euTreeNodes = new ArrayList<>();
        for (TbContentCategory tbContentCategory:tbContentCategories){
            EUTreeNode euTreeNode = new EUTreeNode();
            euTreeNode.setId(tbContentCategory.getId());
            euTreeNode.setState(tbContentCategory.getStatus().toString());
            //euTreeNode.setState(tbContentCategory.getIsParent() ? "closed":"open");
            euTreeNode.setText(tbContentCategory.getName());
            euTreeNodes.add(euTreeNode);
        }
        return euTreeNodes;
    }

    @Override
    public TaotaoResult deleteByIds(long ids) {
        TbContentExample example = new TbContentExample();
        example.createCriteria().andIdEqualTo(ids);
        tbContentMapper.deleteByExample(example);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult addTbContent(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        tbContentMapper.insert(tbContent);
        return TaotaoResult.ok();
    }
}
