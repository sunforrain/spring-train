package com.atguigu.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

// 视频43 扩展原理-@EventListener与SmartInitializingSingleton
@Service
public class UserService {

    // classes可以设定是监听什么类型的事件
    @EventListener(classes={ApplicationEvent.class})
    public void listen(ApplicationEvent event){
        System.out.println("UserService。。监听到的事件："+event);
    }

}
