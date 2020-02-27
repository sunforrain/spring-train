package com.atguigu.tx;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;
import org.springframework.transaction.annotation.TransactionManagementConfigurationSelector;
import org.springframework.transaction.annotation.Transactional;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 声明式事务：
 * 视频36 声明式事务-环境搭建
 * 视频37 声明式事务-测试成功
 * 环境搭建：
 * 1、导入相关依赖
 * 		数据源、数据库驱动、Spring-jdbc模块
 * 2、配置数据源、JdbcTemplate（Spring提供的简化数据库操作的工具）操作数据
 * 3、给方法上标注 @Transactional 表示当前方法是一个事务方法；
 * 4、 @EnableTransactionManagement 开启基于注解的事务管理功能；
 * 		@EnableXXX
 * 5、配置事务管理器来控制事务;
 * 		@Bean
 * 		public PlatformTransactionManager transactionManager()
 *
 * 视频38 声明式事务-源码分析
 * 原理：
 * 1）、@EnableTransactionManagement
 * 			利用TransactionManagementConfigurationSelector给容器中会导入组件(@Import内导入)
 * 			导入两个组件(AdviceMode mode() default AdviceMode.PROXY;)
 * 			AutoProxyRegistrar
 * 			ProxyTransactionManagementConfiguration
 * 2）、AutoProxyRegistrar：
 * 			给容器中注册一个 InfrastructureAdvisorAutoProxyCreator 组件的beanDefinition；
 * 		    和AOP原理中AnnotationAwareAspectJAutoProxyCreator的注册是一个方法
 * 			InfrastructureAdvisorAutoProxyCreator：做了什么？
 * 			利用后置处理器机制在对象创建以后，包装对象，返回一个代理对象（增强器），代理对象执行方法利用拦截器链进行调用；
 * 		    同样参考	AnnotationAwareAspectJAutoProxyCreator的作用时机
 *
 * 3）、ProxyTransactionManagementConfiguration 做了什么？
 * 			1、给容器中注册事务增强器；
 * 		        org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration
 * 		        #transactionAdvisor()
 * 				1）、事务增强器要用事务注解的信息，AnnotationTransactionAttributeSource解析事务注解
 * 			        org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration
 * 			        #transactionAttributeSource()
 * 			            获取一些事务属性
 * 			        org.springframework.transaction.annotation.AnnotationTransactionAttributeSource
 * 			        #AnnotationTransactionAttributeSource(boolean)
 *
 * 			        org.springframework.transaction.annotation.SpringTransactionAnnotationParser
 * 			        #parseTransactionAnnotation(org.springframework.core.annotation.AnnotationAttributes)
 * 			            解析各种spring事务注解(@Transactional)的属性
 *
 * 				2）、事务拦截器：
 * 			        org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration
 * 			        #transactionInterceptor()
 * 					TransactionInterceptor；
 * 					保存了事务属性信息interceptor.setTransactionAttributeSource(transactionAttributeSource());，
 * 					事务管理器interceptor.setTransactionManager(this.txManager);；
 * 					他是一个 MethodInterceptor；
 * 					在目标方法执行的时候；
 * 						执行拦截器链；
 * 					    org.springframework.transaction.interceptor.TransactionInterceptor
 * 					    #invoke(org.aopalliance.intercept.MethodInvocation)
 * 						事务拦截器：
 * 					        org.springframework.transaction.interceptor.TransactionAspectSupport
 * 					        #invokeWithinTransaction(java.lang.reflect.Method, java.lang.Class, org.springframework.transaction.interceptor.TransactionAspectSupport.InvocationCallback)
 * 							1）、先获取事务相关的属性
 * 						        final TransactionAttribute txAttr = getTransactionAttributeSource().getTransactionAttribute(method, targetClass);
 * 							2）、再获取PlatformTransactionManager(平台事务管理器)，
 * 						         final PlatformTransactionManager tm = determineTransactionManager(txAttr);
* 							     如果事先没有添加指定任何transactionmanger,最终会从容器中按照类型获取一个PlatformTransactionManager；
 *                                  @Transactional(transactionManager = null)
 *                                  defaultTransactionManager = this.beanFactory.getBean(PlatformTransactionManager.class);
 * 							3）、执行目标方法
 * 								如果异常，获取到事务管理器，利用事务管理回滚操作；
 * 							    org.springframework.transaction.interceptor.TransactionAspectSupport
 * 							    #completeTransactionAfterThrowing(org.springframework.transaction.interceptor.TransactionAspectSupport.TransactionInfo, java.lang.Throwable)
 * 								如果正常，利用事务管理器，提交事务
 * 							    org.springframework.transaction.interceptor.TransactionAspectSupport
 * 							    #commitTransactionAfterReturning(org.springframework.transaction.interceptor.TransactionAspectSupport.TransactionInfo)
 *
 */
@EnableTransactionManagement
@ComponentScan("com.atguigu.tx")
@Configuration
public class TxConfig {

    //数据源
    @Bean
    public DataSource dataSource() throws Exception{
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://192.168.13.128:3306/atguiguDB");
        return dataSource;
    }

    //
    @Bean
    public JdbcTemplate jdbcTemplate() throws Exception{
        //Spring对@Configuration类会特殊处理；给容器中加组件的方法，多次调用都只是从容器中找组件(其实就是单实例)
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }

    //注册事务管理器在容器中
    @Bean
    public PlatformTransactionManager transactionManager() throws Exception{
        // 对于jdbc,jdbcTemplate,mybatis,事务管理器一般都用DataSourceTransactionManager
        // 注意事务管理器一定要控住数据源
        return new DataSourceTransactionManager(dataSource());
    }


}
