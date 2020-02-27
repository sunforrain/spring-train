package com.atguigu.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 视频36 声明式事务-环境搭建
// 视频37 声明式事务-测试成功
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    // 给方法上标注 @Transactional 表示当前方法是一个事务方法；但是配置类不开启事务控制是无效的
    @Transactional
    public void insertUser(){
        userDao.insert();
        //otherDao.other();xxx
        System.out.println("插入完成...");
        // 模拟插入出问题了,没有事务控制不会回滚
        int i = 10/0;
    }

}
