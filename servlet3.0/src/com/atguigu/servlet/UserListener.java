package com.atguigu.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 监听项目的启动和停止
 * @author lfy
 *
 */
// 视频55 servlet3.0-ServletContext注册三大组件,注册用的listener
public class UserListener implements ServletContextListener {


    //监听ServletContext销毁
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("UserListener...contextDestroyed...");
    }

    //监听ServletContext启动初始化
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        // 视频55 servlet3.0-ServletContext注册三大组件 ServletContextListener得到的ServletContext；
        ServletContext servletContext = arg0.getServletContext();
        System.out.println("UserListener...contextInitialized...");
    }

}
