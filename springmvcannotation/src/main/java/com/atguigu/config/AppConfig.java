package com.atguigu.config;


import com.atguigu.controller.MyFirstInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.*;

// 视频57 springmvc-整合
// 视频58 springmvc-定制与接管SpringMVC
//SpringMVC只扫描Controller；子容器
// 作为扫描controller等的web容器
//useDefaultFilters=false 禁用默认的过滤规则；excludeFilters不用写这个
// @EnableWebMvc 启动springMvc定制配置功能,相当于<mvc:annotation-driven/>；
@ComponentScan(value="com.atguigu",includeFilters={
        @Filter(type=FilterType.ANNOTATION,classes={Controller.class})
},useDefaultFilters=false)
@EnableWebMvc
public class AppConfig  extends WebMvcConfigurerAdapter  {

    //定制

    //视图解析器
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        //默认所有的页面都从 /WEB-INF/ xxx .jsp
        //registry.jsp();
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    //静态资源访问,将springMVC处理不了的请求交给tomcat,方便静态资源访问
    // 相当于 mvc:defualt-servlet-handler
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //super.addInterceptors(registry);
        // /** 表示拦截任意深度的任意路径
        registry.addInterceptor(new MyFirstInterceptor()).addPathPatterns("/**");
    }

}
