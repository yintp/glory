package com.yintp.design.pattern.adapter;

/**
 * 适配器：继承被适配者，并实现目标接口
 *
 * @author zihao.yin
 */
public class ClassAdapter extends Adaptee implements Target {
    /**
     * 实现目标接口的方法
     */
    @Override
    public void request() {
        // 将目标接口的调用，转换为对被适配者方法的调用
        this.specificRequest();
    }
}
