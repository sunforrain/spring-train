package com.atguigu.bean;

import org.springframework.stereotype.Component;

// 视频12 @Bean指定初始化和销毁方法
@Component
public class Car {
    // 构造器,创建bean会用
    public Car(){
        System.out.println("car constructor...");
    }

    // 初始化方法,视类的注册类型是单例还是多例,调用实际不同
    public void init(){
        System.out.println("car ... init...");
    }

    // 销毁方法
    public void detory(){
        System.out.println("car ... detory...");
    }
}
