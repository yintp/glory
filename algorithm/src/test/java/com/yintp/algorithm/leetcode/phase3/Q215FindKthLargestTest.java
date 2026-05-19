package com.yintp.algorithm.leetcode.phase3;

import org.junit.Assert;
import org.junit.Test;

public class Q215FindKthLargestTest {

    private final Q215FindKthLargest solution = new Q215FindKthLargest();

    @Test
    public void testCase1() {
        Assert.assertEquals(5, solution.findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(4, solution.findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4));
    }
}
