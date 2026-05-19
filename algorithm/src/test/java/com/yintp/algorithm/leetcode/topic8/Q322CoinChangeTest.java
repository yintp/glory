package com.yintp.algorithm.leetcode.topic8;

import org.junit.Assert;
import org.junit.Test;

public class Q322CoinChangeTest {

    private final Q322CoinChange solution = new Q322CoinChange();

    @Test
    public void testCase1() {
        Assert.assertEquals(3, solution.coinChange(new int[]{1, 5, 10, 25}, 30));
    }

    @Test
    public void testNoSolution() {
        Assert.assertEquals(-1, solution.coinChange(new int[]{2}, 3));
    }
}
