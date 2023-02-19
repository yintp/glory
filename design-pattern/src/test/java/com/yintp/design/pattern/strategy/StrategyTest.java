package com.yintp.design.pattern.strategy;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yintp
 */
public class StrategyTest {
    @Test
    public void testCase1() {
        Context context = new Context(new AddOperation());
        int sum = context.executeStrategy(10, 5);
        Assert.assertEquals(15, sum);
        context = new Context(new SubtractOperation());
        int diff = context.executeStrategy(10, 5);
        Assert.assertEquals(5, diff);
    }
}
