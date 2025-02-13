package com.yintp.spring.framework.annotation.bean;

import org.springframework.stereotype.Component;

/**
 * 默认加在容器中的的组件，容器启动会调用无参构造创建此对象，载进行初始化赋值等操作
 */
@Component
public class Boss {

    private Car car;

    /**
     * 构造器要用的组件，都是从容器中获取。如果组件只有一个有参构造器，这个有参构造器的@Autowired可以省略，参数位置的组件还是可以自动从容器中获取
     * 也可以标注在方法上
     * @param car
     */
//    @Autowired
//    public Boss(@Autowired Car car) {
    public Boss(Car car) {
        this.car = car;
        System.out.println("Boss ...有参构造");
    }

    public Car getCar() {
        return car;
    }

    /**
     * 标注在方法上，spring容器创建当前对象，就会调用方法，完成赋值。
     * 方法中使用的参数，自定义类型的值从ioc容器中获取
     * @param car
     */
//    @Autowired
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "car=" + car +
                '}';
    }
}
