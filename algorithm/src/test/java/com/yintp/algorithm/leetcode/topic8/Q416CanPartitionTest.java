package com.yintp.algorithm.leetcode.topic8;

import org.junit.Assert;
import org.junit.Test;

public class Q416CanPartitionTest {

    private final Q416CanPartition solution = new Q416CanPartition();

    @Test
    public void testCanPartition() {
        Assert.assertTrue(solution.canPartition(new int[]{1, 5, 11, 5}));
    }

    @Test
    public void testCannotPartition() {
        Assert.assertFalse(solution.canPartition(new int[]{1, 2, 3, 5}));
    }
}
