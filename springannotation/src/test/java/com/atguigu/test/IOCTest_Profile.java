package com.atguigu.test;

import com.atguigu.bean.Yellow;
import com.atguigu.config.MainConfigOfProfile;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

public class IOCTest_Profile {
    //1、使用命令行动态参数: 在虚拟机参数位置加载 -Dspring.profiles.active=test
    //2、代码的方式激活某种环境；
    @Test
    public void test01(){
//        // 视频24 自动装配-@Profile环境搭建
//        AnnotationConfigApplicationContext applicationContext =
//                new AnnotationConfigApplicationContext(MainConfigOfProfile.class);
//        // 获取数据源都有哪些
//        String[] namesForType = applicationContext.getBeanNamesForType(DataSource.class);
//        for (String dataSource:namesForType) {
//            System.out.println(dataSource);
//        }

        // 视频25 自动装配-@Profile根据环境注册bean
        //1、创建一个applicationContext,因为要设置运行环境参数,不能用有参构造,这里相当于把有参构造的内部给拆开运行了
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //2、设置需要激活的环境
        applicationContext.getEnvironment().setActiveProfiles("dev");
        //3、注册主配置类
        applicationContext.register(MainConfigOfProfile.class);
        //4、启动刷新容器
        applicationContext.refresh();

        String[] namesForType = applicationContext.getBeanNamesForType(DataSource.class);
        for (String string : namesForType) {
            System.out.println(string);
        }
        // 能获取到yellow,说明不加@Profile的并不受运行环境参数的影响
        Yellow bean = applicationContext.getBean(Yellow.class);
        System.out.println(bean);
        applicationContext.close();
    }
}
