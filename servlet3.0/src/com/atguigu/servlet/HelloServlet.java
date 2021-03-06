package com.atguigu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// @WebServlet 替代以前在web.xml中注册页面路径
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 视频53 servlet3.0-简介&测试, 关于如何用idea创建没有web.xml的动态web项目见印象笔记, 核心篇 4_9.springboot2.x之使用外置servlet容器原理解析
//        resp.getWriter().write("hello...");
        // 视频59 servlet3.0-异步请求 演示在没有异步请求前的样子
        System.out.println(Thread.currentThread()+" start...");
        try {
            sayHello();
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.getWriter().write("hello...");
        System.out.println(Thread.currentThread()+" end...");
    }

    // 视频59 servlet3.0-异步请求 演示在没有异步请求前的样子,默认一个线程用了很久时间,
    // 就会出现tomcat的线程池用光了,线程都堵塞外面请求进不来的情况
    public void sayHello() throws Exception{
        System.out.println(Thread.currentThread()+" processing...");
        Thread.sleep(3000);
    }

}
