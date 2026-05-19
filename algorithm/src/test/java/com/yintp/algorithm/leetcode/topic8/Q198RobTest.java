package com.yintp.algorithm.leetcode.topic8;

import org.junit.Assert;
import org.junit.Test;

public class Q198RobTest {

    private final Q198Rob solution = new Q198Rob();

    @Test
    public void testCase1() {
        Assert.assertEquals(4, solution.rob(new int[]{1, 2, 3, 1}));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(12, solution.rob(new int[]{2, 7, 9, 3, 1}));
    }
}
