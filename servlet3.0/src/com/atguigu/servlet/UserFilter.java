package com.atguigu.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

// 视频55 servlet3.0-ServletContext注册三大组件,注册用的filter
public class UserFilter implements Filter {

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
            throws IOException, ServletException {
        // 过滤请求
        System.out.println("UserFilter...doFilter...");
        //放行
        arg2.doFilter(arg0, arg1);

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}
