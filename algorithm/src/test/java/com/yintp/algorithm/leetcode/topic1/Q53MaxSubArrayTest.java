package com.yintp.algorithm.leetcode.topic1;

import org.junit.Assert;
import org.junit.Test;

public class Q53MaxSubArrayTest {

    private final Q53MaxSubArray solution = new Q53MaxSubArray();

    @Test
    public void testCase1() {
        Assert.assertEquals(6, solution.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }

    @Test
    public void testAllPositive() {
        Assert.assertEquals(6, solution.maxSubArray(new int[]{1, 2, 3}));
    }

    @Test
    public void testAllNegative() {
        Assert.assertEquals(-1, solution.maxSubArray(new int[]{-3, -1, -2}));
    }
}
