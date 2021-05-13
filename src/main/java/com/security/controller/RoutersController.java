package com.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 郑超
 * @create 2021/5/11
 */
@Controller
public class RoutersController {

    @RequestMapping("/level1/{id}")
    public String level1(@PathVariable int id) {
        return "views/level1/" + id;
    }

    @RequestMapping("/level2/{id}")
    public String level2(@PathVariable int id) {
        return "views/level2/" + id;
    }

    @RequestMapping("/level3/{id}")
    public String level3(@PathVariable int id) {
        return "views/level3/" + id;
    }

}
