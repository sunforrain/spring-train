package com.atguigu.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 任务9 @Import-使用ImportSelector, 这里MyImportSelector就是实现类
 */
public class MyImportSelector implements ImportSelector {

    //返回值，就是到导入到容器中的组件全类名
    //AnnotationMetadata:当前标注@Import注解的类的所有注解信息,可以打debugger看看
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //importingClassMetadata
        //方法不要返回null值,会报空指针
        // 这里写全类名,就会把对应的类加到容器中
        return new String[]{"com.atguigu.bean.Blue","com.atguigu.bean.Yellow"};
    }
}
