package com.atguigu.config;

import com.atguigu.bean.Person;
import com.atguigu.service.BookService;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * 一个配置类   等于过去的配置文件
 * @Configuration -视频2 组件注册-@Configuration&@Bean给容器中注册组件,告诉spring这是一个配置类
 * @ComponentScan 视频3 组件注册-@ComponentScan-自动扫描组件&指定扫描规则
 *                  包扫描,只要标注了@Controller,@Service,@Repositiry,@Component任何一个的组件,都会被自动扫描加入容器中
 *                  与xml配置方式中context:component-scan的一致
 *                  value:指定要扫描的包
 *                  excludeFilters = Filter[] 指定扫描时按照什么规则需要排除掉的组件,
 *                                      过滤规则有
 *                                      ANNOTATION 按照注解,
 *                                      ASSIGNABLE_TYPE 按照给定的类型, 前两个常用
 *                                      ASPECTJ ASPECTJ表达式类型,
 *                                      REGEX正则,
 *                                      CUSTOM自定义规则,
 *                                      看FilterType.java的说明发现需要有TypeFilter的实现类,自定义的在视频4讲
 *                                 classes类型是Class<?>[] 里面可以用,分隔多个注解类型
 *                  includeFilters = Filter[] 指定扫描的时候只需要包含哪些组件,配置xml中还需要把use-default_filter设为false才能生效
 *                                      这里也是一样,在@ComponentScan接口内有一个方法useDefaultFilters,设置为false
 * @ComponentScans 点进去还可以发现有 @Repeatable(ComponentScans.class) 说明这是个重复注解,
 *                          jdk8及以上的我们可以多写几次,满足重复配置
 *                  或者使用@ComponentScans,也是一样的效果
 */
@Configuration
//@ComponentScan(value = "com.atguigu",
//                includeFilters = {
//                    @ComponentScan.Filter(type=FilterType.ANNOTATION,classes = Controller.class)},
//                useDefaultFilters = false)
@ComponentScans(
        value = {
                @ComponentScan(value = "com.atguigu",
                        includeFilters = {
                                // 视频3 这里用注解和指定类型示例
//                                @ComponentScan.Filter(type=FilterType.ANNOTATION,classes = Controller.class),
//                                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BookService.class),
                                // 视频4 这里用自定义的,为了演示需要先把视频3的两个filter注掉
                                @ComponentScan.Filter(type = FilterType.CUSTOM,classes = MyTypeFilter.class)},
                        useDefaultFilters = false)
        }
)
public class MainConfig {
    /**
     * 视频2 组件注册-@Configuration&@Bean给容器中注册组件
     * 给容器注册一个bean,对应原来配置文件的bean标签
     * 类型为返回值的类型,id默认是用方法名作为id
     * 如果不想用默认方法名作为id,也可以在@Bean中指定
     * @return
     */
    @Bean("person")
    public Person person() {
        return new Person("lisi",20);
    }
}
