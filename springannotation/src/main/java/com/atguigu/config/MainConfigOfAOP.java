package com.atguigu.config;

import com.atguigu.aop.LogAspects;
import com.atguigu.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AOP：【动态代理】
 * 		指在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式；
 * 视频27 AOP-AOP功能测试
 * 1、导入aop模块(pom)；Spring AOP：(spring-aspects)
 * 2、定义一个业务逻辑类（MathCalculator.java）；在业务逻辑运行的时候将日志进行打印（方法之前、方法运行结束、方法出现异常，xxx）
 * 3、定义一个日志切面类（LogAspects）：切面类里面的方法需要动态感知MathCalculator.div运行到哪里然后执行；
 * 		通知方法：
 * 			前置通知(@Before)：logStart：在目标方法(div)运行之前运行
 * 			后置通知(@After)：logEnd：在目标方法(div)运行结束之后运行（无论方法正常结束还是异常结束）
 * 			返回通知(@AfterReturning)：logReturn：在目标方法(div)正常返回之后运行
 * 			异常通知(@AfterThrowing)：logException：在目标方法(div)出现异常以后运行
 * 			环绕通知(@Around)：动态代理，手动推进目标方法运行（joinPoint.procced()）
 * 4、给切面类的目标方法标注何时何地运行（通知注解）；
 * 5、将切面类和业务逻辑类（目标方法所在类）都加入到容器中;
 * 6、必须告诉Spring哪个类是切面类(给切面类上加一个注解：@Aspect)
 * [7]、给配置类中加 @EnableAspectJAutoProxy 【开启基于注解的aop模式】
 * 		在Spring中很多的 @EnableXXX;
 *
 * 三步：
 * 	1）、将业务逻辑组件和切面类都加入到容器中；告诉Spring哪个是切面类（@Aspect）
 * 	2）、在切面类上的每一个通知方法上标注通知注解，告诉Spring何时何地运行（切入点表达式）
 *  3）、开启基于注解的aop模式；@EnableAspectJAutoProxy
 *
 * 视频28 AOP原理-@EnableAspectJAutoProxy
 * AOP原理：【看给容器中注册了什么组件，这个组件什么时候工作，这个组件的功能是什么？】
 * 		@EnableAspectJAutoProxy；
 * 1、@EnableAspectJAutoProxy是什么？
 * 		@Import(AspectJAutoProxyRegistrar.class)：给容器中导入AspectJAutoProxyRegistrar
 * 			利用AspectJAutoProxyRegistrar自定义给容器中注册bean；BeanDefinetion
 * 			internalAutoProxyCreator=AnnotationAwareAspectJAutoProxyCreator
 *
 * 		给容器中注册一个AnnotationAwareAspectJAutoProxyCreator；
 *
 * 2、 AnnotationAwareAspectJAutoProxyCreator：
 * 		AnnotationAwareAspectJAutoProxyCreator
 * 			->AspectJAwareAdvisorAutoProxyCreator
 * 				->AbstractAdvisorAutoProxyCreator
 * 					->AbstractAutoProxyCreator
 * 							implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 * 						关注后置处理器（在bean初始化完成前后做事情）、自动装配BeanFactory
 *
 * 视频29 AOP原理-AnnotationAwareAspectJAutoProxyCreator分析,打断点的位置
 * AbstractAutoProxyCreator.setBeanFactory()
 * AbstractAutoProxyCreator.有后置处理器的逻辑；
 * postProcessBeforeInitialization,postProcessAfterInitialization
 * postProcessBeforeInstantiation,postProcessAfterInstantiation
 *
 * AbstractAdvisorAutoProxyCreator.setBeanFactory()-》initBeanFactory()
 *
 * AnnotationAwareAspectJAutoProxyCreator.initBeanFactory()
 *
 * 视频 30 AOP原理-注册AnnotationAwareAspectJAutoProxyCreator
 * 流程：
 * 		1）、传入配置类，创建ioc容器
 * 		2）、注册配置类，调用refresh（）刷新容器,创建组件和初始化等；
 * 		3）、在refresh()中,registerBeanPostProcessors(beanFactory);注册bean的后置处理器来方便拦截bean的创建；
 * 	        在registerBeanPostProcessors(beanFactory)方法内部
 * 			1))、先获取ioc容器已经定义了的需要创建对象的所有BeanPostProcessor,这里还只是definition,还需要创建
 * 		        	包括默认需要创建的和配置类定义的,比如这里@EnableAspectJAutoProxy,里面定义的AnnotationAwareAspectJAutoProxyCreator
 * 			2))、给容器中加别的BeanPostProcessor
 * 		        	beanFactory.addBeanPostProcessor(new BeanPostProcessorChecker(beanFactory, beanProcessorTargetCount));
 * 			3))、优先注册实现了PriorityOrdered接口的BeanPostProcessor；
 * 		            // Separate between BeanPostProcessors that implement PriorityOrdered,
 * 		            // Ordered, and the rest.
 * 			4))、再给容器中注册实现了Ordered接口的BeanPostProcessor；
 * 		    	    (AnnotationAwareAspectJAutoProxyCreator的父类ProxyProcessorSupport实现了ordered接口)
 * 			5))、注册没实现优先级接口的BeanPostProcessor；
 * 			6))、注册BeanPostProcessor，实际上就是创建BeanPostProcessor对象，保存在容器中；
 * 		        IOC容器在第一次获取bean的时候里面没有实例,需要先创建
 * 				创建internalAutoProxyCreator的BeanPostProcessor【AnnotationAwareAspectJAutoProxyCreator】
 * 			    见
 * 			    org.springframework.beans.factory.support.
 * 			    AbstractAutowireCapableBeanFactory
 * 			    #doCreateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
 * 				1)))、创建Bean的实例
 * 			            instanceWrapper = createBeanInstance(beanName, mbd, args);
 * 				2)))、populateBean；给bean的各种属性赋值
 * 			            populateBean(beanName, mbd, instanceWrapper);
 * 				3)))、initializeBean：初始化bean；
 * 			            exposedObject = initializeBean(beanName, exposedObject, mbd);
 * 			            initializeBean(final String beanName, final Object bean, RootBeanDefinition mbd)内
 * 						1))))、invokeAwareMethods()：处理Aware接口的方法回调
 * 					            AnnotationAwareAspectJAutoProxyCreator父类实现了	BeanFactoryAware接口
 * 						2))))、applyBeanPostProcessorsBeforeInitialization()：应用后置处理器的postProcessBeforeInitialization（）
 * 					            这里调用的是BeanPostProcessor接口的
 * 						3))))、invokeInitMethods()；执行自定义的初始化方法
 * 						4))))、applyBeanPostProcessorsAfterInitialization()；执行后置处理器的postProcessAfterInitialization（）；
 * 					            这里调用的是BeanPostProcessor接口的
 * 			    AnnotationAwareAspectJAutoProxyCreator.initBeanFactory()内
 * 				4)))、BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)创建成功；--》aspectJAdvisorsBuilder
 * 			最终回到registerBeanPostProcessors(beanFactory)方法内部
 * 			7))、把BeanPostProcessor注册到BeanFactory中；
 * 				beanFactory.addBeanPostProcessor(postProcessor);
 * =======以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程========
 *
 * 视频31 AOP原理-AnnotationAwareAspectJAutoProxyCreator执行时机
 * 			AnnotationAwareAspectJAutoProxyCreator => InstantiationAwareBeanPostProcessor
 * 			(定义在InstantiationAwareBeanPostProcessor中)
 * 		4)、从新走test方法,在refresh()中,
 * 	    	finishBeanFactoryInitialization(beanFactory);完成BeanFactory初始化工作；创建剩下的单实例bean
 * 	        在preInstantiateSingletons()内
 * 			1))、遍历获取容器中所有的Bean，依次创建对象getBean(beanName);
 * 				getBean->doGetBean()->getSingleton()->
 *
 * 		     org.springframework.beans.factory.support.AbstractBeanFactory
 * 		     #doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)
 * 			2))、创建bean
 * 				【AnnotationAwareAspectJAutoProxyCreator在所有bean创建之前会有一个拦截，
 * 				InstantiationAwareBeanPostProcessor，会调用postProcessBeforeInstantiation()】
 * 				1)))、先从缓存中获取当前bean，如果能获取到，说明bean是之前被创建过的，直接使用，否则再创建；
 * 					只要创建好的Bean都会被缓存起来
 * 				     // Eagerly check singleton cache for manually registered singletons.
 * 		            Object sharedInstance = getSingleton(beanName);
 *
 * 		        org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory
 *			    #createBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
 *  			 // Give BeanPostProcessors a chance to return a proxy instead of the target bean instance.
 * 				2)))、createBean());创建bean；
 * 					【BeanPostProcessor是在Bean对象创建完成初始化前后调用的】
 * 					【InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理器返回对象的】
 * 				    AnnotationAwareAspectJAutoProxyCreator 实现了InstantiationAwareBeanPostProcessor
 * 				    会在任何bean创建之前先尝试返回bean的实例
 *
 * 					1))))、resolveBeforeInstantiation(beanName, mbdToUse);解析BeforeInstantiation
 * 						希望后置处理器在此能返回一个代理对象；如果能返回代理对象就使用，如果不能就继续
 * 						1)))))、后置处理器先尝试返回对象,类比较深入,debugger看；
 * 							bean = applyBeanPostProcessorsBeforeInstantiation（）：
 * 								拿到所有后置处理器，如果是InstantiationAwareBeanPostProcessor;
 * 								就执行postProcessBeforeInstantiation
 * 							if (bean != null) {
                                bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
                            }
 *
 * 					2))))、doCreateBean(beanName, mbdToUse, args);真正的去创建一个bean实例；和3.6流程一样；

 * 视频32 AOP原理-创建AOP代理
 * AnnotationAwareAspectJAutoProxyCreator【InstantiationAwareBeanPostProcessor】	的作用：
 * 1）、每一个bean创建之前，调用postProcessBeforeInstantiation()；
 *       org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator
 *       #postProcessBeforeInstantiation(java.lang.Class, java.lang.String)
 *
 * 		关心MathCalculator和LogAspect的创建
 * 		1）、判断当前bean是否在advisedBeans中（保存了所有需要增强bean）
 * 	            this.advisedBeans.containsKey(cacheKey)
 * 		2）、判断当前bean是否是基础类型的Advice、Pointcut、Advisor、AopInfrastructureBean，
 * 			或者是否是切面（@Aspect）
 * 		        isInfrastructureClass(Class<?> beanClass), 一定用f7打进去看看
 * 		3）、是否需要跳过
 * 	        shouldSkip(beanClass, beanName)
 * 			1）、获取候选的增强器（切面里面的通知方法）【List<Advisor> candidateAdvisors】
 * 				例子中每一个封装的通知方法的增强器是 InstantiationModelAwarePointcutAdvisor；
 * 				判断每一个增强器是否是 AspectJPointcutAdvisor 类型的；返回true
 * 			2）、永远返回false
 *
 * 2）、创建对象
 * postProcessAfterInitialization；
 * org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator
 * #postProcessAfterInitialization(java.lang.Object, java.lang.String)
 *
 * 		return wrapIfNecessary(bean, beanName, cacheKey);//包装如果需要的情况下
 * 		1）、获取当前bean的所有增强器（通知方法）  Object[]  specificInterceptors
 * 			1、找到候选的所有的增强器（找哪些通知方法是需要切入当前bean方法的） findEligibleAdvisors(beanClass, beanName)
 * 	 		    org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator
 *  		    #findCandidateAdvisors()
 *
 * 			2、获取到能在bean使用的增强器。
 * 		        org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator
 * 		        #findAdvisorsThatCanApply()
 *
 * 		        org.springframework.aop.support.AopUtils
 * 		        #findAdvisorsThatCanApply(java.util.List, java.lang.Class)
 * 		        #canApply(org.springframework.aop.Advisor, java.lang.Class, boolean)
 * 		        #canApply(org.springframework.aop.Pointcut, java.lang.Class, boolean)
 * 			3、给增强器排序
 * 		        org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator
 * 		        #sortAdvisors(eligibleAdvisors)
 * 		2）、保存当前bean在advisedBeans中；
 * 	            this.advisedBeans.put(cacheKey, Boolean.TRUE);
 * 		3）、如果当前bean需要增强，创建当前bean的代理对象；
 * 	            Object proxy = createProxy(
 * 					bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));
 * 			1）、获取所有增强器（通知方法）
 * 			2）、保存到proxyFactory
 * 			3）、创建代理对象：Spring自动决定
 * 		            org.springframework.aop.framework.ProxyFactory#getProxy(java.lang.ClassLoader)
 * 		            org.springframework.aop.framework.ProxyCreatorSupport#createAopProxy()
 * 		            org.springframework.aop.framework.DefaultAopProxyFactory#createAopProxy(org.springframework.aop.framework.AdvisedSupport)
 *
 * 				JdkDynamicAopProxy(config);jdk动态代理；
 * 				ObjenesisCglibAopProxy(config);cglib的动态代理；
 * 		4）、给容器中返回当前组件使用cglib增强了的代理对象；
 * 		5）、以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程；
 */

@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAOP {
    //视频27 AOP-AOP功能测试 业务逻辑类加入容器中
    @Bean
    public MathCalculator calculator(){
        return new MathCalculator();
    }

    //视频27 AOP-AOP功能测试 切面类加入到容器中
    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }
}
