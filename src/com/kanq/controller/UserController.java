package com.kanq.controller;


import com.alibaba.fastjson.JSON;
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


    @RequestMapping("usersss")
    @ResponseBody
    private  App_assetfilesex Userssss(){
        App_assetfilesex app_assetfilesex = new App_assetfilesex();
        app_assetfilesex.setPath("a11");
        app_assetfilesex.setAssetId("2212");
        app_assetfilesex.setFileName("啥的");
        app_assetfilesex.setFileType("jpg");
        return app_assetfilesex;
       ---fauwadsad
    }

}
