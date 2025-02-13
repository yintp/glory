package com.yintp.spring.framework.annotation.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    /**
     * @Autowired: 自动注入。默认为必须装配，可以指定required=false来设置非必须装配
     *  1）、默认按照类型去容器中找对应的组件（applicationContext.getBean(PersonDAO.class)）；
     *  2）、若找到多个相同类型的组件，再将属性的名称作为组件的id去容器中查找（即applicationContext.getBean("personDAO")）;
     * @Qualifier：指定需要装配组件的id，而不是使用属性名
     *
     * 除了@Autowired，spring还支持@Resource（JSR250）和@Inject（JSR330）的java规范注解
     *  @Resorce: 可以和@Autowired一样实现自动装配功能，默认是按组件名称去装配的。没有支持@Primary和required=false的功能
     *  @Inject：需要导入javax.inject的包，可以实现@Autowired一样的功能，支持@primary，但是不支持required=false的功能
     *
     * @Autowired:构造器、参数、方法、属性，都是从容器中获取组件的值
     *  1）、标注在方法的位置：@Bean+方法参数、setter方法+方法参数，参数从容器中获取。默认不用写@autowire的
     *  2）、标注在构造器上：如果组件只有一个有参构造器，则这个有参构造器的@autowired可以省略，参数位置的组件还是可以自动从容器中获取组件
     *  3）、放在参数上
     *
     * 自定义的组件想要使用spring容器底层的一些组件（ApplicationContext，BeanFactory,...），
     * 就需要自定义组件实现xxxAware，在创建对象的时候，会调用接口规定的方法注入相关组件；Aware:把spring底层的一些组件注入到自定义的Bean中；
     * xxxAware:功能使用xxxProcessor，比如ApplicationContextAware==>ApplicationCOntextAwareProcessor
     */
//    @Inject
//    @Resource
//    @Qualifier("com.andy.java.spring.annotation.bean.PersonDAO")
    @Autowired(required = false)
    private PersonDAO personDAO;

    public void print() {
        System.out.println(personDAO);
    }
}
