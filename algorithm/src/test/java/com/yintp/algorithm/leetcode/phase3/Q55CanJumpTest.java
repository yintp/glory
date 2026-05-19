package com.yintp.algorithm.leetcode.phase3;

import org.junit.Assert;
import org.junit.Test;

public class Q55CanJumpTest {

    private final Q55CanJump solution = new Q55CanJump();

    @Test
    public void testCanJump() {
        Assert.assertTrue(solution.canJump(new int[]{2, 3, 1, 1, 4}));
    }

    @Test
    public void testCannotJump() {
        Assert.assertFalse(solution.canJump(new int[]{3, 2, 1, 0, 4}));
    }
}
