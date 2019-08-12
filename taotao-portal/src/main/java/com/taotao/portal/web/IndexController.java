package com.taotao.portal.web;

import com.taotao.portal.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.text.AbstractDocument;

/**
 * @author hmt
 * @date 2019/8/2 9:52
 */
@Controller
public class IndexController {
    @Autowired
    private TbContentService tbContentService;
    @RequestMapping(path = {"/","/index"})
    public String showIndex(Model model){
        String content = tbContentService.getContent();
        model.addAttribute("ad1",content);
        return "index";
    }
}
