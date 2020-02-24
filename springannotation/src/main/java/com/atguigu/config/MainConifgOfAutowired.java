package com.atguigu.config;

import com.atguigu.bean.Car;
import com.atguigu.bean.Color;
import com.atguigu.dao.BookDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 视频20 自动装配-@Autowired&@Qualifier&@Primary
 * 自动装配;
 * 		Spring利用依赖注入（DI），完成对IOC容器中中各个组件的依赖关系赋值；
 *
 * 1）、@Autowired：自动注入：
 * 		1）、默认优先按照类型去容器中找对应的组件:
 * 	                    	applicationContext.getBean(BookDao.class);找到就赋值
 * 		2）、如果找到多个相同类型的组件，再将属性的名称作为组件的id去容器中查找
 * 	    	(实际上这里是报错的 expected single matching bean but found 2: bookDao,bookDao2)
 * 							applicationContext.getBean("bookDao")
 * 		3）、@Qualifier("bookDao")：使用@Qualifier指定需要装配的组件的id，而不是使用属性名
 * 		4）、自动装配默认一定要将属性赋值好，没有就会报错；
 * 			可以使用@Autowired(required=false);
 * 		5）、@Primary：让Spring进行自动装配的时候，默认使用首选的bean；
 * 				也可以继续使用@Qualifier指定需要装配的bean的名字
 * 			    相同类型的组件同时存在,指定@Primary的组件与 @Qualifier的组件不同,那么就会出现两个组件都加载的情况
 * 		BookService{
 * 			@Autowired
 * 			BookDao  bookDao;
 * 		}
 *
 * 视频21 自动装配-@Resource&@Inject
 * 2）、Spring还支持使用@Resource(JSR250)和@Inject(JSR330)[java规范的注解]
 * 		@Resource:
 * 			可以和@Autowired一样实现自动装配功能；默认是按照组件名称进行装配的；
 * 			没有能支持@Primary功能没有支持@Autowired（reqiured=false）;
 * 		@Inject:
 * 			需要导入javax.inject的包，和Autowired的功能一样。没有required=false的功能；
 *  @Autowired: Spring定义的； @Resource、@Inject都是java规范
 *
 * AutowiredAnnotationBeanPostProcessor:解析完成自动装配功能；
 *
 * 视频22 自动装配-方法、构造器位置的自动装配
 * 3）、 @Autowired:构造器，参数，方法，属性；
 *      都是从容器中获取参数组件的值
 * 		1）、[标注在方法位置]：@Bean+方法参数；参数从容器中获取;默认不写@Autowired效果是一样的；都能自动装配
 * 		2）、[标在构造器上]：如果组件只有一个有参构造器，这个有参构造器的@Autowired可以省略，参数位置的组件还是可以自动从容器中获取
 * 		3）、放在参数位置：包括方法的参数和构造器的参数
 *
 * 视频23 自动装配-Aware注入Spring底层组件&原理
 * 4）、自定义组件想要使用Spring容器底层的一些组件（ApplicationContext，BeanFactory，xxx）；
 *      Aware:实现这个接口可以使用spring底层的一些功能,类似于回调函数的风格
 *      xxxAware接口都需要继承Aware接口,如果要使用spring底层的一些功能,就需要实现xxxAware,参考 Red.java
 * 		自定义组件实现xxxAware；在创建对象的时候，会调用接口规定的方法注入相关组件；
 *
 * 		把Spring底层一些组件注入到自定义的Bean中；
 * 		xxxAware：功能使用xxxProcessor；
 * 			ApplicationContextAware==》ApplicationContextAwareProcessor；
 * 		    	                看里面实现的 postProcessBeforeInitialization方法,最终返回的IOC容器
 * 		    BeanNameAware ==> initializeBean:1615, AbstractAutowireCapableBeanFactory,
 * 		                        没有进applyBeanPostProcessorsBeforeInitialization,不一样
 */
@Configuration
@ComponentScan({"com.atguigu.service","com.atguigu.dao",
        "com.atguigu.controller","com.atguigu.bean"})// 自动扫描的方式加组件到IOC容器
public class MainConifgOfAutowired {

    // 视频20 自动装配-@Autowired&@Qualifier&@Primary
    // 测试IOC容器中有两个同类型组件的情形
    // (实际上这里是报错的 expected single matching bean but found 2: bookDao,bookDao2)
    // 如果一定要强制使用这个bean,那么需要用@Primary,默认使用首选的bean；
//    @Primary
//    @Bean("bookDao2")
    public BookDao bookDao(){
        BookDao bookDao = new BookDao();
        bookDao.setLable("bookDao2");
        return bookDao;
    }

    /**
     * 视频22 自动装配-方法、构造器位置的自动装配
     * @Bean 标注的方法创建对象的时候，方法参数的值从容器中获取
     * @param car
     * @return
     */
    @Bean
    public Color color(Car car){
        Color color = new Color();
        color.setCar(car);
        return color;
    }
}
