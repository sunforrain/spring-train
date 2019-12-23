package com.atguigu.config;

import com.atguigu.bean.Color;
import com.atguigu.bean.Person;
import com.atguigu.bean.Red;
import com.atguigu.condition.LinuxCondition;
import com.atguigu.condition.WindowsCondition;
import org.springframework.context.annotation.*;

/**
 * 没有@ComponentScan过滤条件的配置类
 * @Conditional配在类上,类中组件统一设置,则需要满足这个条件,这个类中配置的所有bean注册才能生效
 * 任务8 @Import导入组件,id默认是组件的全类名
 */
@Import({Color.class,Red.class})
@Conditional({WindowsCondition.class})
@Configuration
public class MainConfig2 {

    // 默认是单实例的

    /**
     * 任务5
     * @Scope有如下的几种值
     * ConfigurableBeanFactory#SCOPE_PROTOTYPE
     * 	ConfigurableBeanFactory#SCOPE_SINGLETON
     * 	org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST
     *  org.springframework.web.context.WebApplicationContext#SCOPE_SESSION
     *
     *  prototype:多实例的: ioc容器启动不会调用方法创建对象,每次获取的时候才会调用方法创建对象;
     *              可见在 applicationContext.getBean("person")才会创建对象;
     *  singleton:单实例的(默认值): ioc容器启动会调用方法创建对象放到ioc容器中.
     *              以后每次获取就是直接从容器(map.get())中拿
     *              可见new AnnotationConfigApplicationContext(MainConfig2.class);调用时就会创建对象
     *  request:同一次请求创建一个实例
     *  session:同一个session创建一个实例
     *
     *  任务6
     *  懒加载:
     *      单实例bean:默认在容器启动的时候创建对象;
     *      懒加载:容器启动不创建对象.第一次使用(获取)Bean创建对象,并初始化;
     *      懒加载是针对单实例bean的.多次获取还是只会创建一次
     *
     * @return
     */
    @Lazy // 任务6：06、尚硅谷_Spring注解驱动开发_组件注册-@Lazy-bean懒加载
//    @Scope("prototype")
    @Bean("person")
    public Person person() {
        System.out.println("给容器中添加Person....");
        return new Person("张三", 25);
    }

    /**
     * 任务7 @Conditional-按照条件注册bean
     *  如果系统是windows.给容器中注册("bill")
     *
     * @Conditional需要传的参数是实现了Condition接口的实现类
     * @Conditional也可以配在类上
     * @return
     */
    @Conditional({WindowsCondition.class})
    @Bean("bill")
    public Person person01() {
        return new Person("Bill Gates", 62);
    }

    /**
     * 任务7 @Conditional-按照条件注册bean
     * 如果系统是linux.给容器中注册("linus")
     *
     * @Conditional需要传的参数是实现了Condition接口的实现类
     * @return
     */
    @Conditional({LinuxCondition.class})
    @Bean("linus")
    public Person person02() {
        return new Person("linus", 48);
    }

    /**
     * 任务8：08、尚硅谷_Spring注解驱动开发_组件注册-@Import-给容器中快速导入一个组件
     * 总结一下目前给容器中注册组件的方式:
     * 1) 包扫描+组件标注注解(@Controller/@Service/@Repository/@Component)
     *      这种可以用在我们自己写的类上,但是如果是导入的包里面的类呢?
     * 2) @Bean[导入的第三方包里面的组件可以用这种方式,但是每次导入个第三方组件都要调构造器,似乎有些麻烦]
     * 3) @Import[快速给容器中导入一个组件]
     *      三种可传的参数:{@link Configuration}, {@link ImportSelector}, {@link ImportBeanDefinitionRegistrar}
     *      1)) @Import(要导入到容器的组件):容器中就会自动注册这个组件,id默认是全类名
     *      2)) @ImportSelecter:返回需要导入的组件的全类名数组
     */
}
