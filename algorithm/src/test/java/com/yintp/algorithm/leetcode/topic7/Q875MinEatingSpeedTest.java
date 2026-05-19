package com.yintp.algorithm.leetcode.topic7;

import org.junit.Assert;
import org.junit.Test;

public class Q875MinEatingSpeedTest {

    private final Q875MinEatingSpeed solution = new Q875MinEatingSpeed();

    @Test
    public void testCase1() {
        Assert.assertEquals(4, solution.minEatingSpeed(new int[]{3, 6, 7, 11}, 8));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(30, solution.minEatingSpeed(new int[]{30, 11, 23, 4, 20}, 5));
    }
}
