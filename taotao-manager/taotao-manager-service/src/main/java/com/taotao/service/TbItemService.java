package com.taotao.service;

import com.taotao.common.EasyUIResult;
import org.springframework.stereotype.Service;

/**
 * @author hmt
 * @date 2019/7/27 17:09
 */
@Service
public interface TbItemService {

    EasyUIResult getTbItemList(int pageNum,int pageSize);

}
