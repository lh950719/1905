package com.jk.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class UserController {
    @RequestMapping("index")
    public String index(){

        return "index";
    }

    @RequestMapping("403")
    public String to403(){
        return "403";
    }

    @RequestMapping("toAdd")
    @RequiresPermissions("user:toAdd")
    public String toAdd(){
        return "addUser";
    }
}
