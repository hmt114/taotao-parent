package com.taotao.service;

import com.taotao.common.EUTreeNode;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hmt
 * @date 2019/7/27 20:48
 */
@Service
public interface TbItemCatService {
    List<EUTreeNode> getItemCategoryList(long parentId);
}
