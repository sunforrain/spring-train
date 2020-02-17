package com.atguigu.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 视频7 @Conditional  的判断条件类
 * 判断是否linux系统
 */
public class LinuxCondition implements Condition {
    /**
     *
     * @param context 判断条件能使用的上下文环境
     * @param metadata 当前标记了Condition注解的注释信息
     * @return
     */
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 1 获取到ioc使用的组件工厂beanFactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        // 2 获取类加载器
        ClassLoader classLoader = context.getClassLoader();
        // 3 获取当前环境信息
        Environment environment = context.getEnvironment();
        // 4 获取到bean定义的注册类
        BeanDefinitionRegistry beanDefinitionRegistry = context.getRegistry();
        // 可以判断容器中bean注册情况,也可以给容器中注册bean
        boolean defination = beanDefinitionRegistry.containsBeanDefinition("persons");

        String property = environment.getProperty("os.name");
        if(property.contains("linux")){
            return true;
        }
        return false;
    }
}
