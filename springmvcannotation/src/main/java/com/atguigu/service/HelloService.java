package com.atguigu.service;

import org.springframework.stereotype.Service;

// 视频57 springmvc-整合
@Service
public class HelloService {

    public String sayHello(String name){

        return "Hello "+name;
    }

}
