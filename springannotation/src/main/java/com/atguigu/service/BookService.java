package com.atguigu.service;

import com.atguigu.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

// 视频3：03、组件注册-@ComponentScan-自动扫描组件&指定扫描规则
@Service
public class BookService {
    // 视频20 自动装配-@Autowired&@Qualifier&@Primary
    // @Qualifier("bookDao")：使用@Qualifier指定需要装配的组件的id，而不是使用属性名
    // 自动装配默认一定要将属性赋值好，没有就会报错；如果需要不强制,可以使用@Autowired(required=false)
    // 相同类型的组件同时存在,指定@Primary的组件与 @Qualifier的组件不同,那么就会出现两个组件都加载的情况,如果只指定primary,那就首选
    // 视频21 自动装配-@Resource&@Inject 二者的区别见配置类
//    @Qualifier("bookDao")
//    @Autowired(required=false)
//    @Resource
//    @Inject
    private BookDao bookDao;

    public void print(){
        System.out.println(bookDao);
    }

    @Override
    public String toString() {
        return "BookService [bookDao=" + bookDao + "]";
    }
}
