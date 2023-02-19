package com.yintp.algorithm.leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yintp
 */
public class Q29DivideTest {
    @Test
    public void testCase1() {
        Q29Divide q29Divide = new Q29Divide();
        int divide = q29Divide.divide(9, -2);
        Assert.assertEquals(-4, divide);
    }
}
