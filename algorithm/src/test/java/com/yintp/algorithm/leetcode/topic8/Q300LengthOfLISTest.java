package com.yintp.algorithm.leetcode.topic8;

import org.junit.Assert;
import org.junit.Test;

public class Q300LengthOfLISTest {

    private final Q300LengthOfLIS solution = new Q300LengthOfLIS();

    @Test
    public void testCase1() {
        Assert.assertEquals(4, solution.lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
    }

    @Test
    public void testAllSame() {
        Assert.assertEquals(1, solution.lengthOfLIS(new int[]{0, 0, 0, 0, 0}));
    }
}
