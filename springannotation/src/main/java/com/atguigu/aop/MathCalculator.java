package com.atguigu.aop;

// 视频27 AOP-AOP功能测试
// 用来测试aop的业务逻辑类
public class MathCalculator {

    public int div(int i,int j){
        System.out.println("MathCalculator...div...");
        return i/j;
    }
}
