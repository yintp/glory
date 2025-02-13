package com.yintp.spring.framework.annotation.config;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class MyTypeFilter implements TypeFilter {

    /**
     * @param metadataReader        当前正在扫描的类信息
     * @param metadataReaderFactory 可以获取其它任何类信息
     * @return
     * @throws IOException
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        // 获取当前类注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        // 获取当前类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        // 获取当前类资源
        Resource resource = metadataReader.getResource();
        System.out.println("类名--->" + classMetadata.getClassName());
        if (classMetadata.getClassName().contains("er")) {
            return true;
        }
        return false;
    }
}
