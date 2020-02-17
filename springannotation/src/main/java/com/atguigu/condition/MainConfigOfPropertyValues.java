package com.atguigu.condition;

import com.atguigu.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

// 视频19 属性赋值-@PropertySource加载外部配置文件
// 使用@PropertySource读取外部配置文件中的k/v保存到运行的环境变量中;加载完外部的配置文件以后使用${}取出配置文件的值
// @PropertySources 也可以使用,里面可以套多个@PropertySource
@PropertySource(value={"classpath:/person.properties"})
@Configuration
public class MainConfigOfPropertyValues {
    // 视频18 属性赋值-@Value赋值
    @Bean
    public Person person(){
        return new Person();
    }
}
