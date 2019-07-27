package com.taotao.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hmt
 * @date 2019/7/27 10:50
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }

    @RequestMapping("/page/{page}")
    public String toPage(@PathVariable String page){
        return page;
    }
}
