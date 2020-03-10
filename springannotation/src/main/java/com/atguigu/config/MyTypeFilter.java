package com.atguigu.config;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 *
 * 一个TypeFilter的实现类,用来指定CUSTOM自定义规则
 */
public class MyTypeFilter implements TypeFilter {
    /**
     * 视频4 组件注册-自定义TypeFilter指定过滤规则
     * @param metadataReader 读取到的当前正在扫描的类的信息
     * @param metadataReaderFactory 可以获取到其他任何类的信息
     * @return
     * @throws IOException
     */
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        // 获取当前类注解的信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        // 获取当前正在扫描的类的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        // 获取当前类资源(类的路径)
        Resource resource = metadataReader.getResource();
        // 获取当前类的类名
        String className = classMetadata.getClassName();
        System.out.println("--->"+ className);
        // 随便写个规则
        if(className.contains("er")) {
            return true;
        }
        // return false的不会被加载到容器中
        return false;
    }
}
