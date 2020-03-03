package com.atguigu.servlet;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// 视频59 servlet3.0-异步请求 演示异步请求的处理方式
// 详情可以查看servlet3.0规范(课件有),9.6部分
@WebServlet(value="/async",asyncSupported=true)
public class HelloAsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1、支持异步处理asyncSupported=true
        //2、开启异步模式
        System.out.println("主线程开始。。。"+Thread.currentThread()+"==>"+System.currentTimeMillis());
        AsyncContext startAsync = req.startAsync();
        boolean flag = req.isAsyncStarted();
        //3、业务逻辑进行异步处理;开始异步处理
        startAsync.start(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("副线程开始。。。"+Thread.currentThread()+"==>"+System.currentTimeMillis());
                    sayHello();
                    //获取到异步上下文
                    AsyncContext asyncContext = req.getAsyncContext();
                    //4、获取响应
                    ServletResponse response = asyncContext.getResponse();
                    response.getWriter().write("hello async...");
                    System.out.println("副线程结束。。。"+Thread.currentThread()+"==>"+System.currentTimeMillis());
                    // 例子里面这个complete为什么在获取响应的方法前面...线程直接完成了好么
                    startAsync.complete();
                } catch (Exception e) {
                }
            }
        });
        System.out.println("主线程结束。。。"+Thread.currentThread()+"==>"+System.currentTimeMillis());
    }

    public void sayHello() throws Exception{
        System.out.println(Thread.currentThread()+" processing...");
        Thread.sleep(3000);
    }
}
