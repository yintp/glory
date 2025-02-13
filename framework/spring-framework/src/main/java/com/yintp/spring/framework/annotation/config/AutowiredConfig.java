package com.yintp.spring.framework.annotation.config;

import com.yintp.spring.framework.annotation.bean.Car;
import com.yintp.spring.framework.annotation.bean.Color;
import com.yintp.spring.framework.annotation.bean.PersonDAO;
import com.yintp.spring.framework.annotation.bean.PersonService;
import org.springframework.context.annotation.*;

@Configuration
@Import({PersonService.class, PersonDAO.class})
@ComponentScan(value = "com.andy.spring.framework.annotation.bean")
public class AutowiredConfig {

    /**
     * @Primary：让spring装配的时候，默认首选的bean
     *  也可以使用@Qualifier指定需要装配组件的id
     */
    @Primary
    @Bean
    public PersonDAO personDAO1() {
        PersonDAO personDAO= new PersonDAO();
        personDAO.setLabel("personDAO1");
        return personDAO;
    }

    @Bean
    public PersonDAO personDAO2() {
        PersonDAO personDAO= new PersonDAO();
        personDAO.setLabel("personDAO2");
        return personDAO;
    }

    /**
     * @Bean标注的方法创建对象的时候，方法的参数值从容器中获取,@Autowired可以省略
     * @param car
     * @return
     */
    @Bean
//    public Color color(@Autowired Car car) {
    public Color color(Car car) {
        Color color =new Color();
        color.setCar(car);
        return color;
    }
}
