package com.yintp.algorithm.leetcode.phase3;

import org.junit.Assert;
import org.junit.Test;

public class Q45JumpTest {

    private final Q45Jump solution = new Q45Jump();

    @Test
    public void testCase1() {
        Assert.assertEquals(2, solution.jump(new int[]{2, 3, 1, 1, 4}));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(2, solution.jump(new int[]{2, 3, 0, 1, 4}));
    }
}
