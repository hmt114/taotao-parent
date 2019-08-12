package com.taotao.rest.web;

import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hmt
 * @date 2019/7/31 21:45
 */
@RestController
@RequestMapping("/itemcat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/all")
    @CrossOrigin
    public Object getItemCat(){
        return itemCatService.getItemCat();
    }
}
