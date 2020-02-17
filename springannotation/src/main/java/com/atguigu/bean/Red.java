package com.atguigu.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

// 视频23 自动装配-Aware注入Spring底层组件&原理
// 找几个xxxAware看看功能

/**
 * ApplicationContextAware接口:要实现setApplicationContext,用来传入IOC容器
 * BeanNameAware接口:要实现setBeanName,用来获取保存在容器中对象的名称(id)
 * EmbeddedValueResolverAware接口:要实现setEmbeddedValueResolver,值解析器
 */
@Component
public class Red implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {

    private ApplicationContext applicationContext;

    // 获取IOC容器
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("传入的ioc："+applicationContext);
        this.applicationContext = applicationContext;
    }

    // IOC容器保存对象,这里返回保存的对象名称(id)
    public void setBeanName(String name) {
        System.out.println("当前bean的名字："+name);
    }

    // 传入的是String值的解析器
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        String resolveStringValue = resolver.resolveStringValue("你好 ${os.name} 我是 #{20*18}");
        System.out.println("解析的字符串："+resolveStringValue);
    }
}
