package com.atguigu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 视频53 servlet3.0-简介&测试, 关于如何用idea创建没有web.xml的动态web项目见印象笔记
// @WebServlet 替代以前在web.xml中注册页面路径
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        System.out.println(Thread.currentThread()+" start...");
        try {
            sayHello();
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.getWriter().write("hello...");
        System.out.println(Thread.currentThread()+" end...");
    }

    public void sayHello() throws Exception{
        System.out.println(Thread.currentThread()+" processing...");
        Thread.sleep(3000);
    }

}
