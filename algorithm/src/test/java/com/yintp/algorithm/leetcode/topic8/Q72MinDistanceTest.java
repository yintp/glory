package com.yintp.algorithm.leetcode.topic8;

import org.junit.Assert;
import org.junit.Test;

public class Q72MinDistanceTest {

    private final Q72MinDistance solution = new Q72MinDistance();

    @Test
    public void testCase1() {
        Assert.assertEquals(3, solution.minDistance("horse", "ros"));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(5, solution.minDistance("intention", "execution"));
    }
}
