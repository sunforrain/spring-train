package com.atguigu.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

// 视频13、生命周期-InitializingBean和DisposableBean
// @Component 加上之后直接用@ComponentScan自动扫描到容器中
@Component
public class Cat implements InitializingBean, DisposableBean {

    public Cat(){
        System.out.println("cat constructor...");
    }

    // 销毁方法
    public void destroy() throws Exception {
        System.out.println("cat...destroy...");
    }

    // 初始化方法,但是要在所有相关属性都赋值完成后才会调用
    public void afterPropertiesSet() throws Exception {
        System.out.println("cat...afterPropertiesSet...");
    }
}
