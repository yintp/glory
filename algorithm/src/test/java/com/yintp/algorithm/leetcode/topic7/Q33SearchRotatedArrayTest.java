package com.yintp.algorithm.leetcode.topic7;

import org.junit.Assert;
import org.junit.Test;

public class Q33SearchRotatedArrayTest {

    private final Q33SearchRotatedArray solution = new Q33SearchRotatedArray();

    @Test
    public void testFound() {
        Assert.assertEquals(4, solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
    }

    @Test
    public void testNotFound() {
        Assert.assertEquals(-1, solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3));
    }

    @Test
    public void testSingleElement() {
        Assert.assertEquals(-1, solution.search(new int[]{1}, 0));
    }
}
