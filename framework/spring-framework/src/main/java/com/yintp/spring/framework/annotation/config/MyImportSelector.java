package com.yintp.spring.framework.annotation.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.andy.java.spring.annotation.bean.Blue","com.andy.java.spring.annotation.bean.Yellow"};
    }
}
