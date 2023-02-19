package com.yintp.design.pattern.factory.method;

import com.yintp.design.pattern.factory.simple.Computer;
import com.yintp.design.pattern.factory.simple.XiaomiComputer;

/**
 * @author yintp
 */
public class XiaomiComputerFactory extends ComputerFactory {
    @Override
    public Computer getComputer() {
        return new XiaomiComputer();
    }
}
