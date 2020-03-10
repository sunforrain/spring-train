package com.atguigu.bean;

/**
 * 视频8 @Import导入组件,id默认是组件的全类名
 * 这个类没有在配置类中做注册,也因此如果没有使用@Import是不会存在于ioc容器中的
 */
public class Color {
    // 视频22 自动装配-方法、构造器位置的自动装配
    // 用于演示是@Bean标注的方法的参数传入来赋值
    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Color [car=" + car + "]";
    }
}
