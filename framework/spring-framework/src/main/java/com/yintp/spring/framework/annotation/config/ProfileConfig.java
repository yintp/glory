package com.yintp.spring.framework.annotation.config;

import com.yintp.spring.framework.annotation.bean.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

/**
 * @Profile: spring为我们提供了可以根据当前环境，动态激活和切换一系列组件的功能。
 *  1、指定组件在哪个环境下才能被注册到容器中，默认不指定则在任何环境下都注册该组件
 *  2、默认是default环境
 *  3、配置在类上时，只有在指定环境下整个配置类里面的配置才能生效
 */
@PropertySource("classpath:/data.properties")
@Configuration
public class ProfileConfig implements EmbeddedValueResolverAware{

    @Value("${data.name}")
    private String name;
    private String pwd;

//    @Profile("default")
    @Profile("dev")
    @Bean("devData")
    public Data data1(@Value("${data.nick}") String nick) {
        Data data = new Data();
        data.setName(name);
        data.setNick(nick);
        data.setPwd(pwd);
        return data;
    }

    @Profile("test")
    @Bean("testData")
    public Data data2(@Value("${data.nick}") String nick) {
        Data data = new Data();
        data.setName(name);
        data.setNick(nick);
        data.setPwd(pwd);
        return data;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        String pwd = resolver.resolveStringValue("${data.pwd}");
        this.pwd = pwd;
    }
}
