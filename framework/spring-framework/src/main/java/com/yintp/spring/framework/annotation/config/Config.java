package com.yintp.spring.framework.annotation.config;

import com.yintp.spring.framework.annotation.bean.ColorFactoBean;
import com.yintp.spring.framework.annotation.bean.Person;
import com.yintp.spring.framework.annotation.bean.Red;
import com.yintp.spring.framework.annotation.condition.LinuxCondition;
import com.yintp.spring.framework.annotation.condition.WindowsCondition;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

/**
 * @Configuration: 配置类，用于告诉spring这是一个配置类，相当于配置文件xml
 */
@Configuration
/**
 * @ComponentScan: 包扫描，相当于<context:component-scan base-package=""></context:component-scan>
 * - value属性: 指定要扫描的包
 * - excludeFilters: 过滤排除，指定扫描的时候按什么规则排除哪些组件
 * - includeFilters: 过滤包含，需先配置useDefaultFilters=false，指定扫描的时候需要只包含哪些组件
 * 该注解可以配置多个，若环境不支持重复注解也可以配置@ComponentScans
 * Filter中的type:
 *      FilterType.ANNOTATION: 过滤指定注解
 *      FilterType.ASSIGNABLE_TYPE: 过滤指定类型
 *      FilterType.CUSTOM: 自定义过滤规则
 */
@ComponentScan(value = "com.andy.spring.framework.annotation"
//        , excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Repository.class})}
        , includeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
//        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {PersonService.class})},
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})},
        useDefaultFilters = false
)
/**
 * @Import: 导入组件，id默认是组件的全类名
 * 给容器中注册组件：
 *      1）包扫描+组件标记注解（@Controller、@Service、@Repository、@Component）[导入自己的包组件]
 *      2）@Bean [导入第三方包组件]
 *      3）@Import [快速给容器中导入组件]
 *          - 直接注册bean到容器中，id为全类名
 *          - 也可以传入一个ImportSelector，返回需要导入组件的全类名
 *          - 也可以传入一个ImportBeanDefinitionRegistrar，手工注册bean到容器中
 *      4）FactoryBean: 工厂bean，
 */
@Import({Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class Config {

    /**
     * @Bean: 给容器中注册一个组件，id默认是方法名，相当于配置文件中的<bean />，类型为返回值的类型
     * - value属性: 给bean指定名称，也就是id
     * @Scope: 组件作用域
     * - value:
     * ConfigurableBeanFactory.SCOPE_SINGLETON: 单实例（默认值），ioc容器启动会调用方法创建对象并放到容器中
     * ConfigurableBeanFactory.SCOPE_PROTOTYPE: 多实例，每次获取对象才会调用方法创建对象
     * @Lazy: 懒加载，当单实例bean默认在容器启动时创建对象，懒加载则容器启动时不加载对象，第一次使用时才创建对象并放在容器中
     */
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Lazy
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Bean("person")
    public Person person() {
        return new Person("张三", 20);
    }

    /**
     * @Conditional: 按条件注册bean。按照一定的条件进行判断，满足条件给容器中注册bean（当放在类上时，满足条件，类配置的bean注册才能生效，统一配置）
     * - value: Condition的实现类
     */
    @Conditional(WindowsCondition.class)
    @Bean("bill")
    public Person person01() {
        return new Person("Bill Gates", 62);
    }

    @Conditional(LinuxCondition.class)
    @Bean("linus")
    public Person person02() {
        return new Person("linus", 48);
    }

    /**
     * 注册的组件名为方法名，但是默认注册的组件为FactoBean.getObject方法注册的对象
     * 如果要想获取工厂bean本身，则通过给id前面加&，即&colorFactoryBean
     *
     * @return
     */
    @Bean
    public FactoryBean colorFactoryBean() {
        return new ColorFactoBean();
    }
}
