package com.yintp.design.pattern.template;

/**
 * @author yintp
 */
public abstract class Game {
    abstract void start();

    abstract void end();

    /**
     * 模板方法
     */
    public final void play() {
        start();
        end();
    }
}
