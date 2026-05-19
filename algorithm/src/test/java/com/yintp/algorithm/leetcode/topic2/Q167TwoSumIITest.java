package com.yintp.algorithm.leetcode.topic2;

import org.junit.Assert;
import org.junit.Test;

public class Q167TwoSumIITest {

    private final Q167TwoSumII solution = new Q167TwoSumII();

    @Test
    public void testCase1() {
        Assert.assertArrayEquals(new int[]{1, 2}, solution.twoSum(new int[]{2, 7, 11, 15}, 9));
    }

    @Test
    public void testCase2() {
        Assert.assertArrayEquals(new int[]{1, 3}, solution.twoSum(new int[]{2, 3, 4}, 6));
    }
}
