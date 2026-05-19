package com.yintp.algorithm.leetcode.topic1;

import org.junit.Assert;
import org.junit.Test;

public class Q217ContainsDuplicateTest {

    private final Q217ContainsDuplicate solution = new Q217ContainsDuplicate();

    @Test
    public void testHasDuplicate() {
        Assert.assertTrue(solution.containsDuplicate(new int[]{1, 2, 3, 1}));
    }

    @Test
    public void testNoDuplicate() {
        Assert.assertFalse(solution.containsDuplicate(new int[]{1, 2, 3, 4}));
    }

    @Test
    public void testSingleElement() {
        Assert.assertFalse(solution.containsDuplicate(new int[]{1}));
    }
}
