package com.yintp.algorithm.leetcode.topic2;

import org.junit.Assert;
import org.junit.Test;

public class Q76MinWindowTest {

    private final Q76MinWindow solution = new Q76MinWindow();

    @Test
    public void testCase1() {
        Assert.assertEquals("BANC", solution.minWindow("ADOBECODEBANC", "ABC"));
    }

    @Test
    public void testNoResult() {
        Assert.assertEquals("", solution.minWindow("a", "aa"));
    }

    @Test
    public void testSameString() {
        Assert.assertEquals("a", solution.minWindow("a", "a"));
    }
}
