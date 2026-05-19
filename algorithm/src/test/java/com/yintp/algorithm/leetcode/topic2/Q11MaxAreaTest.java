package com.yintp.algorithm.leetcode.topic2;

import org.junit.Assert;
import org.junit.Test;

public class Q11MaxAreaTest {

    private final Q11MaxArea solution = new Q11MaxArea();

    @Test
    public void testCase1() {
        Assert.assertEquals(49, solution.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(1, solution.maxArea(new int[]{1, 1}));
    }
}
