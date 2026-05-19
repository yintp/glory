package com.yintp.algorithm.leetcode.topic1;

import org.junit.Assert;
import org.junit.Test;

public class Q1TwoSumTest {

    private final Q1TwoSum solution = new Q1TwoSum();

    @Test
    public void testCase1() {
        Assert.assertArrayEquals(new int[]{0, 1}, solution.twoSum(new int[]{2, 7, 11, 15}, 9));
    }

    @Test
    public void testCase2() {
        Assert.assertArrayEquals(new int[]{1, 2}, solution.twoSum(new int[]{3, 2, 4}, 6));
    }

    @Test
    public void testDuplicate() {
        Assert.assertArrayEquals(new int[]{0, 1}, solution.twoSum(new int[]{3, 3}, 6));
    }
}
