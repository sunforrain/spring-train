package com.atguigu.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

// 视频41 扩展原理-ApplicationListener用法
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {

    //当容器中发布此事件以后，方法触发
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("收到事件："+event);
    }

}
