package com.yintp.algorithm.leetcode.topic1;

import org.junit.Assert;
import org.junit.Test;

public class Q128LongestConsecutiveTest {

    private final Q128LongestConsecutive solution = new Q128LongestConsecutive();

    @Test
    public void testCase1() {
        Assert.assertEquals(4, solution.longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(9, solution.longestConsecutive(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}));
    }
}
