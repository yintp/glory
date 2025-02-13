package com.yintp.spring.framework.annotation.config;

import com.yintp.spring.framework.annotation.bean.RainRow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * 把需要添加到容器中的bean调用BeanDefinitionRegistry.registerBeanDefinition注册组件
     * @param importingClassMetadata 当前类的注解信息
     * @param registry BeanDefinition注册类
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean red = registry.containsBeanDefinition("com.andy.java.spring.annotation.bean.Red");
        boolean blue = registry.containsBeanDefinition("com.andy.java.spring.annotation.bean.Blue");
        if (red && blue) {
            // 注册bean
            registry.registerBeanDefinition("rainBow", new RootBeanDefinition(RainRow.class));
        }
    }
}
