package com.yintp.design.pattern.builder;

import org.junit.Test;

/**
 * @author yintp
 */
public class BuilderTest {
    @Test
    public void testCase1() {
        ComputerDirector director = new ComputerDirector();
        ComputerBuilder huaweiComputerBuilder = new HuaweiComputerBuilder("I7处理器", "京东方");
        director.makeComputer(huaweiComputerBuilder);
        Computer macComputer = huaweiComputerBuilder.getComputer();
        System.out.println("huawei computer:" + macComputer.toString());

        ComputerBuilder xiaomiComputerBuilder = new XiaomiComputerBuilder("I5", "LG");
        director.makeComputer(xiaomiComputerBuilder);
        Computer xiaomiComputer = xiaomiComputerBuilder.getComputer();
        System.out.println("xiaomi computer:" + xiaomiComputer.toString());
    }
}
