package com.yintp.spring.framework.annotation.config;

import com.yintp.spring.framework.annotation.bean.Banana;
import com.yintp.spring.framework.annotation.bean.Man;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @PropertySource：读取外部配置文件中的key-value保存到运行的环境变量中，加载完外部配置文件使用${}取出配置文件中的值
 * 相当于以前的<context:property-placeholder location=""></context:property-placeholder>
 */
@PropertySource(value = {"classpath:/man.properties"})
@Configuration
public class PropertyConfig {

    @Bean
    public Man man() {
        return new Man();
    }

    @Bean
    public Banana banana() {
        return new Banana();
    }
}
