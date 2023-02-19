package com.yintp.design.pattern.factory.method;

import com.yintp.design.pattern.factory.simple.Computer;
import com.yintp.design.pattern.factory.simple.HuaweiComputer;

/**
 * 当创建对象流程很复杂时，工厂类单独创建指定对象
 *
 * @author yintp
 */
public class HuaweiComputerFactory extends ComputerFactory {
    @Override
    public Computer getComputer() {
        return new HuaweiComputer();
    }
}
