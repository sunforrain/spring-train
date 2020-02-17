package com.atguigu.test;

import com.atguigu.bean.Person;
import com.atguigu.condition.MainConfigOfPropertyValues;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class IOCTest_PropertyValue {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfPropertyValues.class);
    @Test
    public void test01(){
        printBeans(applicationContext);
        System.out.println("=============");
        // 视频18 属性赋值-@Value赋值
        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person);

        // 视频19 属性赋值-@PropertySource加载外部配置文件
        // 除了在bean里面取,也可以根据在配置文件的key直接从IOC容器中取
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("person.nickName");
        System.out.println(property);
        applicationContext.close();
    }

    private void printBeans(AnnotationConfigApplicationContext applicationContext){
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }
}
