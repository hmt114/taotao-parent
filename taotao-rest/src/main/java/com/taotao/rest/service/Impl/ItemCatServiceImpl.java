package com.taotao.rest.service.Impl;

import com.taotao.common.TaotaoResult;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.pojo.ItemCat;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hmt
 * @date 2019/7/31 22:21
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public TaotaoResult getItemCat() {
        //基于数据库
        //List<Object> itemCatList = loadItemCatByDB(0L);
        //return TaotaoResult.ok(itemCatList);

        //基于内存
        TbItemCatExample condition = new TbItemCatExample();
        condition.createCriteria().andStatusNotEqualTo(2);
        List<TbItemCat> tbItemCatList = tbItemCatMapper.selectByExample(condition);
        List<Object> itemCatList = loadItemCatByMemoryRecursive(tbItemCatList,0L);
        return TaotaoResult.ok(itemCatList);
    }

    /**
     * 基于内存递归组建分类树
     * @param data
     * @param parentId
     * @return
     */
    private List<Object> loadItemCatByMemoryRecursive(List<TbItemCat> data, Long parentId) {
        List<Object> result = new ArrayList<>();
        int count = 0;
        for(TbItemCat tbItemCat : data) {
            if(tbItemCat.getParentId().equals(parentId)) {
                if(tbItemCat.getIsParent()) {
                    ItemCat itemCat = new ItemCat();
                    itemCat.setU("/products/"+tbItemCat.getId()+".html");
                    if(parentId.equals(0)) {
                        itemCat.setN("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
                    }else {
                        itemCat.setN(tbItemCat.getName());
                    }
                    itemCat.setI(loadItemCatByMemoryRecursive(data, tbItemCat.getId()));
                    result.add(itemCat);
                    count++;
                    if(count >= 14) {
                        break;
                    }
                } else {
                    String itemCat = "/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName();
                    result.add(itemCat);
                }
            }
        }
        return result;
    }


    //基于数据库的查询，构造分类树
    public List<Object> loadItemCatByDB(Long parentId){
        List<Object> result = new ArrayList<>();
        TbItemCatExample example = new TbItemCatExample();
        example.createCriteria()
                .andStatusNotEqualTo(2)
                .andParentIdEqualTo(parentId);
        List<TbItemCat> itemCatList = tbItemCatMapper.selectByExample(example);
        int count = 0;
        for(TbItemCat tbItemCat:itemCatList){
            if(tbItemCat.getIsParent()){
                ItemCat itemCat = new ItemCat();
                itemCat.setU("/products/"+tbItemCat.getId()+".html");
                if(parentId.equals(0)){
                    itemCat.setN("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
                }else {
                    itemCat.setN(tbItemCat.getName());
                }
                itemCat.setI(loadItemCatByDB(tbItemCat.getId()));
                result.add(itemCat);
                count++;
                if(count >= 14){
                    break;
                }
            }else {
                String itemCat = "/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName();
                result.add(itemCat);
            }
        }
        return result;
    }
}
