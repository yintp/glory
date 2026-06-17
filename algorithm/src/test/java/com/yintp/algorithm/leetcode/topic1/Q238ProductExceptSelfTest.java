package com.yintp.algorithm.leetcode.topic1;

import org.junit.Assert;
import org.junit.Test;

public class Q238ProductExceptSelfTest {

    private final Q238ProductExceptSelf solution = new Q238ProductExceptSelf();

    @Test
    public void testCase1() {
        Assert.assertArrayEquals(new int[]{24, 12, 8, 6}, solution.productExceptSelf(new int[]{1, 2, 3, 4}));
    }

    @Test
    public void testWithZero() {
        Assert.assertArrayEquals(new int[]{0, 0, 9, 0, 0}, solution.productExceptSelf(new int[]{-1, 1, 0, -3, 3}));
    }
}
