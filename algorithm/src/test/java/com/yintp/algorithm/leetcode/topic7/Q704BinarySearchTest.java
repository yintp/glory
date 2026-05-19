package com.yintp.algorithm.leetcode.topic7;

import org.junit.Assert;
import org.junit.Test;

public class Q704BinarySearchTest {

    private final Q704BinarySearch solution = new Q704BinarySearch();

    @Test
    public void testFound() {
        Assert.assertEquals(4, solution.search(new int[]{-1, 0, 3, 5, 9, 12}, 9));
    }

    @Test
    public void testNotFound() {
        Assert.assertEquals(-1, solution.search(new int[]{-1, 0, 3, 5, 9, 12}, 2));
    }
}
