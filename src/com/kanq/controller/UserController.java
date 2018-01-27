package com.kanq.controller;



import com.kanq.entity.App_assetfilesex;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("get")
public class UserController {

    @RequestMapping("user")
    @ResponseBody
    private  String Users(){
        return "adasd";
    }




}
