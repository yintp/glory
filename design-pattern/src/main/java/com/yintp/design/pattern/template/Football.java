package com.yintp.design.pattern.template;

/**
 * @author yintp
 */
public class Football extends Game {
    @Override
    void start() {
        System.out.println("Football start");
    }

    @Override
    void end() {
        System.out.println("Football end");
    }
}
