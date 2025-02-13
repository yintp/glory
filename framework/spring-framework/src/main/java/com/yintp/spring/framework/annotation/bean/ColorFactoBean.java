package com.yintp.spring.framework.annotation.bean;

import org.springframework.beans.factory.FactoryBean;

public class ColorFactoBean implements FactoryBean<Color> {

    @Override
    public Color getObject() throws Exception {
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
