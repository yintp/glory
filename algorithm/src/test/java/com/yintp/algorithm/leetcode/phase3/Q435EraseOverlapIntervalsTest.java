package com.yintp.algorithm.leetcode.phase3;

import org.junit.Assert;
import org.junit.Test;

public class Q435EraseOverlapIntervalsTest {

    private final Q435EraseOverlapIntervals solution = new Q435EraseOverlapIntervals();

    @Test
    public void testCase1() {
        Assert.assertEquals(1, solution.eraseOverlapIntervals(new int[][]{{1,2},{2,3},{3,4},{1,3}}));
    }

    @Test
    public void testNoOverlap() {
        Assert.assertEquals(0, solution.eraseOverlapIntervals(new int[][]{{1,2},{2,3}}));
    }
}
