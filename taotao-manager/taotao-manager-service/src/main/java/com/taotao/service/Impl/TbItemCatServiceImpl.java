package com.taotao.service.Impl;

import com.taotao.common.EUTreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.service.TbItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hmt
 * @date 2019/7/27 17:17
 */
@Service
public class TbItemCatServiceImpl implements TbItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public List<EUTreeNode> getItemCategoryList(long parentId) {
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        tbItemCatExample.createCriteria().andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCats = tbItemCatMapper.selectByExample(tbItemCatExample);

        //把查询到的分类数据封装成前台所需要的EasyUI异步树数据结构
        List<EUTreeNode> euTreeNodes = new ArrayList<>();
        for(TbItemCat tbItemCat:tbItemCats){
            EUTreeNode euTreeNode = new EUTreeNode();
            euTreeNode.setId(tbItemCat.getId());
            euTreeNode.setText(tbItemCat.getName());
            euTreeNode.setState(tbItemCat.getIsParent() ? "closed":"open");
            euTreeNodes.add(euTreeNode);
        }
        return euTreeNodes;
    }
}
