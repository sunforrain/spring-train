package com.atguigu.test;

import com.atguigu.bean.Person;
import com.atguigu.config.MainConfig;
import com.atguigu.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

public class IOCTest {
    // 任务7 因为经常获取容器,这里放在公共,生产不要这么做
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

    /**
     * 任务7：07、尚硅谷_Spring注解驱动开发_组件注册-@Conditional-按照条件注册bean
     */
    @Test
    public void test03 () {
        // 遍历查看IOC容器中所有的的bean的name
        String[] nameForType = applicationContext.getBeanDefinitionNames();
        // 动态获取环境变量的值
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        // 获取动态的操作系统名称
        String property = environment.getProperty("os.name");
        System.out.println(property);
        for(String name: nameForType){
            System.out.println(name);
        }

        Map<String, Person> persons = applicationContext.getBeansOfType(Person.class);
        System.out.println(persons);
    }

    /**
     * 任务5：05、尚硅谷_Spring注解驱动开发_组件注册-@Scope-设置组件作用域
     */
    @Test
    public void test02 () {
        // 这里引用了一个新的配置类,没有设置过滤条件
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        // 遍历查看IOC容器中所有的的bean的name
//        String[] nameForType = applicationContext.getBeanDefinitionNames();
//        for(String string: nameForType){
//            System.out.println(string);
//        }

        System.out.println("ioc容器创建完成....");
        // 通过容器获取的对象默认是单实例的,也就是说两次获取其实指向的是一个对象
        // @Scope("prototype")设置后就变成了多实例了
        Object bean = applicationContext.getBean("person");
        Object bean2 = applicationContext.getBean("person");
//        System.out.println(bean == bean2);
    }
    /**
     * 任务3：03、尚硅谷_Spring注解驱动开发_组件注册-@ComponentScan-自动扫描组件&指定扫描规则
     */
    @Test
    public void test01 () {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        // 遍历查看IOC容器中所有的的bean的name
        String[] nameForType = applicationContext.getBeanDefinitionNames();
        for(String string: nameForType){
            System.out.println(string);
        }
    }
}
