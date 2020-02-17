package com.atguigu.test;

import com.atguigu.config.MainConfigOfLifeCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_LifeCycle {

    // 视频12 @Bean指定初始化和销毁方法
    // 视频13、生命周期-InitializingBean和DisposableBean
    // 视频14 生命周期-@PostConstruct&@PreDestroy
    // 视频15 生命周期-BeanPostProcessor-后置处理器
    // 视频17 生命周期-BeanPostProcessor在Spring底层的使用
    @Test
    public void test01(){
        //1、创建ioc容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
        System.out.println("容器创建完成...");

        //applicationContext.getBean("car");
        //关闭容器
        applicationContext.close();
    }
}
