package com.yintp.spring.framework.annotation.config;

import com.yintp.spring.framework.annotation.aop.LogAspects;
import com.yintp.spring.framework.annotation.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AOP:指在程序运行期间动态的将某段代码切入到方法的指定位置进行运行的编程方式
 * 1）、导入aop模块：Spring AOP(spring-aspects)
 * 2)、定义业务逻辑类（MathCalculator），在业务逻辑运行的时候进行日志打印
 * 3）、定义一个切面类（LogAspects），切面类里面的方法需要动态感知MathCalculator.div()运行到哪里，然后执行
 *      通知方法：
 *          前置通知（@Before）：在目标方法（div）运行之前运行（logStart）
 *          后置通知（@After）：在目标方法（div）运行之后运行（logEnd）
 *          返回通知（@AfterReturning）：在目标方法（div）正常返回之后运行（logReturn）
 *          异常通知（@AfterThrowing）：在目标方法（div）出现异常之后运行（logException）
 *          环绕通知（@Around）：动态代理，手动推进目标方法运行（joinPoint.proced）
 * 4）、给切面类的目标方法标注何时何地运行（通知注解）
 * 5）、将切面类和业务逻辑类（目标方法所在的类）都加入到容器中
 * 6）、必须告诉spring哪个是切面类（给切面类添加注解@Aspect）
 * 7）、给配置类添加@EnableAspectJAutoProxy告诉spring启用AspectJ自动代理，即开启基于注解的aop模式
 *      ，相当于以前的<aop:aspectj-autoproxy></aop:aspectj-autoproxy>
 *
 * 原理：@EnableAspectJAutoProxy。（看给容器中注册了什么组件，什么时候工作，功能是什么）
 *  1）、@EnableAspectJAutoProxy是什么？
 *      @Import(AspectJAutoProxyRegistrar.class)：给容器中导入AspectJAutoProxyRegistrar，
 *      利用AspectJAutoProxyRegistrar自定义给容器中注册bean。最终给容器注册了AnnotationAwareAspectJAutoProxyCreator。
 *  2）、AnnotationAwareAspectJAutoProxyCreator组件：
 *      AnnotationAwareAspectJAutoProxyCreator
 *          ->AspectJAwareAdvisorAutoProxyCreator
 *              ->AbstractAdvisorAutoProxyCreator
 *                  ->AbstractAutoProxyCreator
 *                      implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 *                      关注后置处理器（在bean初始化完成前后做的事情）、自动装配BeanFactory
 *
 *  AbstractAutoProxyCreator.setBeanFactory()
 *  AbstractAutoProxyCreator.后置处理器的逻辑
 *
 *  AbstractAdvisorAutoProxyCreator.setBeanFactory()->initBeanFactory()
 *
 *  AnnotationAwareAspectJAutoProxyCreator.initBeanFactory()
 *  3）、流程
 *      3.1）、传入配置类，创建ioc容器；
 *      3.2）、注册配置类，调用refresh()刷新容器；
 *      3.3、registerBeanPostProcessors(beanFactory);注册bean的后置处理器来方便拦截bean的创建
 *          3.3.1）、先获取ioc容器已经定义了的需要创建对象的所有BeanPostProcessor
 *          3.3.2）、给容器中加入别的BeanPostProcessor
 *          3.3.3）、先注册实现PriorityOrdered接口的BeanPostProcessor，再给容器中注册实现了Ordered接口的BeanPostProcessor，
 *              最后在注册没有实现优先接口的BeanPostProcessor
 *          3.3.4）、注册BeanPostProcessor实际上就是创建BeanPostProcessor对象在容器中
 *              创建internalAutoProxyCreator的BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)
 *              3.3.4.1）、创建bean实例
 *              3.3.4.2）、polulateBean：被bean的各种属性赋值
 *              3.3.4.3）、initializeBean：初始化bean
 *                  3.3.4.3.1）、invokeAwareMethods()：处理Aware接口的方法回调
 *                  3.3.4.3.2）、applyBeanPostProcessorBeforeInitiallization()：处理后置处理器的postProcessBeforeInitialization()
 *                  3.3.4.3.3）、invokeInitMethods()：执行自定义的初始化方法
 *                  3.3.4.3.4）、applyBeanPostProcessorAfterInitiallization()：处理后置处理器的postProcessAfterInitialization()
 *              3.3.4.4）、
 *
 *
 */
@EnableAspectJAutoProxy
@Configuration
public class AopConfig {

    /**
     * 业务逻辑类加入容器
     */
    @Bean
    public MathCalculator mathCalculator() {
        return new MathCalculator();
    }

    /**
     * 切面类加入容器
     */
    @Bean
    public LogAspects logAspects() {
        return new LogAspects();
    }
}
