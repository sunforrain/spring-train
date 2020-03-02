package com.atguigu.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

// 视频57 springmvc-整合
//Spring的容器不扫描controller;父容器
// 作为处理service和dao等的容器
@ComponentScan(value="com.atguigu",excludeFilters={
        @Filter(type=FilterType.ANNOTATION,classes={Controller.class})
})
public class RootConfig {

}
