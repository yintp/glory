package com.yintp.algorithm.leetcode.topic7;

import org.junit.Assert;
import org.junit.Test;

public class Q153FindMinTest {

    private final Q153FindMin solution = new Q153FindMin();

    @Test
    public void testCase1() {
        Assert.assertEquals(1, solution.findMin(new int[]{3, 4, 5, 1, 2}));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(0, solution.findMin(new int[]{4, 5, 6, 7, 0, 1, 2}));
    }
}
