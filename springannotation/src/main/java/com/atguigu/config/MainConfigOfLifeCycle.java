package com.atguigu.config;

import com.atguigu.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * bean的生命周期：
 * 		bean创建---初始化----销毁的过程
 * 容器管理bean的生命周期；
 * 我们可以自定义初始化和销毁方法,bean创建没有提供控制；容器在bean进行到当前生命周期的时候来调用我们自定义的初始化和销毁方法
 *
 * 生命周期的先后执行顺序:
 * 构造（对象创建以及对象属性的赋值	）,没有提供方法控制
 * 		单实例：在容器启动的时候创建对象
 * 		多实例：在每次获取的时候创建对象
 *
 * BeanPostProcessor.postProcessBeforeInitialization
 * 初始化：
 * 		对象创建完成，并赋值好，调用初始化方法。。。
 * BeanPostProcessor.postProcessAfterInitialization
 * 销毁：
 * 		单实例：容器关闭的时候
 * 		多实例：容器不会管理这个bean；容器不会调用销毁方法；
 *
 *
 * 遍历得到容器中所有的BeanPostProcessor；挨个执行beforeInitialization，
 * 一但返回null，跳出for循环，不会执行后面的BeanPostProcessor.postProcessorsBeforeInitialization
 *
 * BeanPostProcessor原理
 * org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory
 *  #doCreateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
 * populateBean(beanName, mbd, instanceWrapper);给bean进行属性赋值
 *
 * initializeBean
 * {
 * applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 * invokeInitMethods(beanName, wrappedBean, mbd);执行自定义初始化
 * applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 *}
 *
 *
 *
 * 1）、指定初始化和销毁方法；
 * 		通过@Bean指定initMethod和destroyMethod； 视频12
 * 2）、通过让Bean实现InitializingBean（定义初始化逻辑）， 视频13
 * 				DisposableBean（定义销毁逻辑）  视频13
 * 3）、可以使用JSR250；
 * 		@PostConstruct：在bean创建完成并且属性赋值完成之后来执行初始化方法  视频14
 * 		@PreDestroy：在容器销毁bean之前通知我们进行清理工作   视频14
 *
 * 	    明确一点:初始化必然在bean创建和属性赋值之后
 *
 * 4）、BeanPostProcessor【interface】：bean的后置处理器；
 * 		在bean初始化前后进行一些处理工作；
 * 		postProcessBeforeInitialization:在初始化之前工作  视频15
 * 		postProcessAfterInitialization:在初始化之后工作   视频15
 *
 * Spring底层对 BeanPostProcessor 的使用； 视频17,见脑图
 * 		bean赋值，注入其他组件，@Autowired，生命周期注解功能，@Async,xxx BeanPostProcessor;
 *
 */
@ComponentScan("com.atguigu.bean")
@Configuration
public class MainConfigOfLifeCycle {

    // 视频12 @Bean指定初始化和销毁方法
    // initMethod 指定初始化方法
    // destroyMethod 指定销毁方法
    //@Scope("prototype")
    @Bean(initMethod="init",destroyMethod="detory")
    public Car car(){
        return new Car();
    }
}
