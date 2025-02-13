package com.yintp.spring.framework.annotation.config;

import com.yintp.spring.framework.annotation.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * bean的生命周期：创建-->初始化-->销毁的过程
 * 容器管理的生命周期：
 * 创建：
 *  - 单实例：在容器启动的时候创建对象
 *  - 多实例：在每次获取对象的时候创建对象
 * 后置处理器BeanPostProcessor.postProcessBeforeInitialization()
 * 初始化：
 *  对象创建完成，并赋值好，调用初始化方法
 * 后置处理器BeanPostProcessor.postProcessAfterInitialization()
 * 销毁：
 *  - 单实例：容器关闭的时候
 *  - 多实例：容器不会管理这个bean，即容器不会调用销毁方法。
 *
 * 方式：
 * 1）、指定初始化和销毁方法。通过@Bean指定initMethod（定义初始化逻辑）和destroyMethod（定义销毁逻辑）
 * 2）、通过让bean实现InitializingBean（定义初始化逻辑）和DisposableBean（定义销毁逻辑）
 * 3）、使用JSR250规范注解指定初始化和销毁方法。@PostConstruct（定义初始化逻辑）和@PreDestory（定义销毁逻辑）
 * 4）、BeanPostProcessor：bean后置处理器
 *      - 在bean初始化前后进行一些处理工作
 */
@ComponentScan("com.andy.spring.framework.annotation")
@Configuration
public class LifeCycleConfig {

    @Bean(initMethod = "init", destroyMethod = "destory")
    public Car car() {
        return new Car();
    }
}
