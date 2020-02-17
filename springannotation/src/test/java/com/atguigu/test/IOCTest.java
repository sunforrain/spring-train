package com.atguigu.test;

import com.atguigu.bean.Blue;
import com.atguigu.bean.Person;
import com.atguigu.config.MainConfig;
import com.atguigu.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

public class IOCTest {
    // 视频7 因为经常获取容器,这里放在公共,生产不要这么做
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

    /**
     * 视频8：组件注册-@Import-给容器中快速导入一个组件
     * 需要在配置类上加注解@Import
     */
    @Test
    public void testImport () {
        printBeans(applicationContext);
        //视频9 @Import-使用ImportSelector, 配置类中引入实现类后,这里就能拿到bean
        Blue bean = applicationContext.getBean(Blue.class);
        System.out.println(bean);

        // 视频11 组件注册-使用FactoryBean注册组件
        //工厂Bean获取的是调用getObject创建的对象
        Object bean2 = applicationContext.getBean("colorFactoryBean");
        // FactoryBean如果设置非单例,这里会是两个对象
        Object bean3 = applicationContext.getBean("colorFactoryBean");
        System.out.println("bean的类型："+bean2.getClass());
        System.out.println(bean2 == bean3);

        // &开头可以获取到工厂Bean本身,原因?
        /**
         * interface BeanFactory 其中有如下参数,设置bean工厂本身的对象名为&开头
         * String FACTORY_BEAN_PREFIX = "&";
         */

        Object bean4 = applicationContext.getBean("&colorFactoryBean");
        System.out.println(bean4.getClass());
    }

    // 遍历查看IOC容器中所有的的bean的name
    private void printBeans(AnnotationConfigApplicationContext applicationContext) {
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for(String name: definitionNames){
            System.out.println(name);
        }
    }
    /**
     * 视频7：组件注册-@Conditional-按照条件注册bean
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
     * 视频5：组件注册-@Scope-设置组件作用域
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
     * 视频3：03、组件注册-@ComponentScan-自动扫描组件&指定扫描规则
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
