package com.yintp.algorithm.leetcode.phase3;

import org.junit.Assert;
import org.junit.Test;

public class Q560SubarraySumTest {

    private final Q560SubarraySum solution = new Q560SubarraySum();

    @Test
    public void testCase1() {
        Assert.assertEquals(2, solution.subarraySum(new int[]{1, 1, 1}, 2));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(2, solution.subarraySum(new int[]{1, 2, 3}, 3));
    }
}
