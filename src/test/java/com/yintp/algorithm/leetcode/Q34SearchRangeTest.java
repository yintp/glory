package com.yintp.algorithm.leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yintp
 */
public class Q34SearchRangeTest {
    @Test
    public void testCase1() {
        Q34SearchRange q34SearchRange = new Q34SearchRange();
        int[] s = {5, 7, 7, 8, 8, 10};
        int[] result = q34SearchRange.searchRange(s, 8);
        int[] expect = {3, 4};
        Assert.assertArrayEquals(expect, result);
    }

    @Test
    public void testCase2() {
        Q34SearchRange q34SearchRange = new Q34SearchRange();
        int[] s = {5, 7, 7, 8, 8, 10};
        int[] result = q34SearchRange.searchRange(s, 6);
        int[] expect = {-1, -1};
        Assert.assertArrayEquals(expect, result);
    }

    @Test
    public void testCase3() {
        Q34SearchRange q34SearchRange = new Q34SearchRange();
        int[] s = {};
        int[] result = q34SearchRange.searchRange(s, 0);
        int[] expect = {-1, -1};
        Assert.assertArrayEquals(expect, result);
    }

    @Test
    public void testCase4() {
        Q34SearchRange q34SearchRange = new Q34SearchRange();
        int[] s = {1};
        int[] result = q34SearchRange.searchRange(s, 1);
        int[] expect = {0, 0};
        Assert.assertArrayEquals(expect, result);
    }
}
