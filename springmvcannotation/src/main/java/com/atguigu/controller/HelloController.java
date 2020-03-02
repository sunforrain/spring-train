package com.atguigu.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.service.HelloService;


@Controller
public class HelloController {

    @Autowired
    HelloService helloService;

    // 视频57 springmvc-整合
    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        String hello = helloService.sayHello("tomcat..");
        return hello;
    }

    // 视频58 springmvc-定制与接管SpringMVC
    //  /WEB-INF/views/success.jsp
    @RequestMapping("/suc")
    public String success(){
        return "success";
    }


}
