package com.yintp.design.pattern.factory;

import com.yintp.design.pattern.factory.method.HuaweiComputerFactory;
import com.yintp.design.pattern.factory.method.XiaomiComputerFactory;
import com.yintp.design.pattern.factory.simple.Computer;
import com.yintp.design.pattern.factory.simple.ComputerFactory;
import org.junit.Test;

/**
 * @author yintp
 */
public class FactoryTest {
    @Test
    public void testCase1() {
        Computer huawei = ComputerFactory.getComputer("huawei");
        huawei.desc();
        Computer xiaomi = ComputerFactory.getComputer("xiaomi");
        xiaomi.desc();
    }

    @Test
    public void testCase2() {
        HuaweiComputerFactory huaweiComputerFactory = new HuaweiComputerFactory();
        Computer huawei = huaweiComputerFactory.getComputer();
        huawei.desc();
        XiaomiComputerFactory xiaomiComputerFactory = new XiaomiComputerFactory();
        Computer xiaomi = xiaomiComputerFactory.getComputer();
        xiaomi.desc();
    }
}
