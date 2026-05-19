package com.yintp.algorithm.leetcode.topic6;

import org.junit.Assert;
import org.junit.Test;

public class Q207CanFinishTest {

    private final Q207CanFinish solution = new Q207CanFinish();

    @Test
    public void testCanFinish() {
        Assert.assertTrue(solution.canFinish(2, new int[][]{{1, 0}}));
    }

    @Test
    public void testCannotFinish() {
        Assert.assertFalse(solution.canFinish(2, new int[][]{{1, 0}, {0, 1}}));
    }
}
