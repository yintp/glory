package com.yintp.design.pattern.template;

/**
 * @author yintp
 */
public class Basketball extends Game {
    @Override
    void start() {
        System.out.println("Basketball start");
    }

    @Override
    void end() {
        System.out.println("Basketball end");
    }
}
