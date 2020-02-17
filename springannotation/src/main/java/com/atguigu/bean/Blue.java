package com.atguigu.bean;
// 视频9 @Import-使用ImportSelector, 这里MyImportSelector就是实现类
public class Blue {
	
	public Blue(){
		System.out.println("blue...constructor");
	}
	
	public void init(){
		System.out.println("blue...init...");
	}
	
	public void detory(){
		System.out.println("blue...detory...");
	}
	
	

}
