package com.atguigu.dao;

import org.springframework.stereotype.Repository;
// 视频3：03、组件注册-@ComponentScan-自动扫描组件&指定扫描规则
// 视频20 自动装配-@Autowired&@Qualifier&@Primary
@Repository
public class BookDao {
    private String lable = "bookDao1";

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    @Override
    public String toString() {
        return "BookDao{" +
                "lable='" + lable + '\'' +
                '}';
    }
}
