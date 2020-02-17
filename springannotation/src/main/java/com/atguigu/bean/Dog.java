package com.atguigu.bean;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

// 视频14 生命周期-@PostConstruct&@PreDestroy
@Component
public class Dog {
    public Dog(){
        System.out.println("dog constructor...");
    }

    //对象创建并赋值之后调用的初始化方法
    @PostConstruct
    public void init(){
        System.out.println("Dog....@PostConstruct...");
    }

    //容器移除对象之前
    @PreDestroy
    public void detory(){
        System.out.println("Dog....@PreDestroy...");
    }
}
