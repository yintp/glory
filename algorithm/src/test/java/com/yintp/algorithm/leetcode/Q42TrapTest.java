package com.yintp.algorithm.leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yintp
 */
public class Q42TrapTest {
    @Test
    public void testCase1() {
        int[] height = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        Q42Trap q42Trap = new Q42Trap();
        int result = q42Trap.trap(height);
        Assert.assertEquals(6, result);
    }

    @Test
    public void testCase2() {
        int[] height = new int[]{4, 2, 0, 3, 2, 5};
        Q42Trap q42Trap = new Q42Trap();
        int result = q42Trap.trap(height);
        Assert.assertEquals(9, result);
    }
}
