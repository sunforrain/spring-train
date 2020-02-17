package com.atguigu.test;

import com.atguigu.bean.Boss;
import com.atguigu.bean.Car;
import com.atguigu.bean.Color;
import com.atguigu.config.MainConifgOfAutowired;
import com.atguigu.dao.BookDao;
import com.atguigu.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_Autowired {

    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConifgOfAutowired.class);

        // 视频20 自动装配-@Autowired&@Qualifier&@Primary
        // 视频21 自动装配-@Resource&@Inject
        // 这里只有Autowire bookDao,可以看到bookDao已经被装配了
        BookService bookService = applicationContext.getBean(BookService.class);
        System.out.println(bookService);
        // service里面的bookDao与IOC容器里面的bookDao是一致的,可以比较两次打印的内存地址
        BookDao bean = applicationContext.getBean(BookDao.class);
        System.out.println(bean);

        //视频22 自动装配-方法、构造器位置的自动装配
        // 用各种方法在Boss中装配的Car,都是IOC容器中取得
        Boss boss = applicationContext.getBean(Boss.class);
        System.out.println(boss);
        Car car = applicationContext.getBean(Car.class);
        System.out.println(car);

        Color color = applicationContext.getBean(Color.class);
        System.out.println(color);

        //视频23 自动装配-Aware注入Spring底层组件&原理
        System.out.println(applicationContext);


        applicationContext.close();
    }
}
