package com.yintp.spring.framework.annotation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.andy.spring.framework.annotation.aop.introduce")
@Configuration
public class IntroduceConfig {
}
