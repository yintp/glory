package com.yintp.algorithm.leetcode.topic8;

import org.junit.Assert;
import org.junit.Test;

public class Q70ClimbStairsTest {

    private final Q70ClimbStairs solution = new Q70ClimbStairs();

    @Test
    public void testCase1() {
        Assert.assertEquals(2, solution.climbStairs(2));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(3, solution.climbStairs(3));
    }
}
