package com.atguigu.controller;

import com.atguigu.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
// 视频3：03、组件注册-@ComponentScan-自动扫描组件&指定扫描规则
// 视频20 自动装配-@Autowired&@Qualifier&@Primary
@Controller
public class BookController {
    @Autowired
    private BookService bookService;
}
