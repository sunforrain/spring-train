package com.atguigu.servlet;

import com.atguigu.service.HelloService;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.EnumSet;
import java.util.Set;

//容器启动的时候会将@HandlesTypes指定的这个类型下面的子类（实现类，子接口等）传递过来；
//传入感兴趣的类型；
@HandlesTypes(value={HelloService.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {

    /**
     * 应用启动的时候，会运行onStartup方法；
     *
     * Set<Class<?>> arg0：感兴趣的类型的所有子类型；
     * ServletContext sc:代表当前Web应用的ServletContext；一个Web应用一个ServletContext；
     *
     * 1）、使用ServletContext注册Web组件（Servlet、Filter、Listener）
     * 2）、使用编码的方式，在项目启动的时候给ServletContext里面添加组件；
     * 		必须在项目启动的时候来添加;启动之后不能添加,基于安全考虑；
     * 		1）、ServletContainerInitializer得到的ServletContext(正如onStartup所示)；
     * 		2）、ServletContextListener得到的ServletContext；
     */
    @Override
    public void onStartup(Set<Class<?>> arg0, ServletContext sc) throws ServletException {
        // 视频54 servlet3.0-ServletContainerInitializer 用来演示运行时插件机制
        // 可以在这里获取到@HandlesTypes指定的class,利用反射等做些什么
        System.out.println("感兴趣的类型：");
        for (Class<?> claz : arg0) {
            System.out.println(claz);
        }

        // 视频55 servlet3.0-ServletContext注册三大组件
        //注册组件  ServletRegistration
        ServletRegistration.Dynamic servlet = sc.addServlet("userServlet", new UserServlet());
        //配置servlet的映射信息,测试走这个映射
        servlet.addMapping("/user");


        //注册Listener
        sc.addListener(UserListener.class);

        //注册Filter  FilterRegistration
        FilterRegistration.Dynamic filter = sc.addFilter("userFilter", UserFilter.class);
        //配置Filter的映射信息
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

    }

}
