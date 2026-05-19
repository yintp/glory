package com.yintp.algorithm.leetcode.topic4;

import org.junit.Assert;
import org.junit.Test;

public class Q84LargestRectangleTest {

    private final Q84LargestRectangle solution = new Q84LargestRectangle();

    @Test
    public void testCase1() {
        Assert.assertEquals(10, solution.largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
    }

    @Test
    public void testCase2() {
        Assert.assertEquals(4, solution.largestRectangleArea(new int[]{2, 4}));
    }
}
