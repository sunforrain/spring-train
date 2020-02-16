package com.atguigu;

import com.atguigu.bean.Person;
import com.atguigu.config.MainConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {

    /**
     * 任务2 组件注册-@Configuration&@Bean给容器中注册组件
     * @param args
     */
    public static void main (String[] args) {
        // 使用配置的方式获取到bean
        // 获取到bean容器,需要获取到类路径下的配置文件
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
//        Person bean = (Person)applicationContext.getBean("person");
////        System.out.println(bean);

        // 使用注解的方式获取到bean
        // 获取到bean容器,需要获取到配置类
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        Person bean = applicationContext.getBean(Person.class);
        System.out.println(bean);

        // 遍历查看IOC容器中的bean的name,根据类的名字
        String[] nameForType = applicationContext.getBeanNamesForType(Person.class);
        for(String string: nameForType){
            System.out.println(string);
        }
    }
}
