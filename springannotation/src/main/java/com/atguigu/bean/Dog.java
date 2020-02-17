package com.atguigu.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

// 视频14 生命周期-@PostConstruct&@PreDestroy
@Component
public class Dog implements ApplicationContextAware {

    // 视频17 生命周期-BeanPostProcessor在Spring底层的使用
    private ApplicationContext applicationContext;

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

    // 视频17 生命周期-BeanPostProcessor在Spring底层的使用
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
