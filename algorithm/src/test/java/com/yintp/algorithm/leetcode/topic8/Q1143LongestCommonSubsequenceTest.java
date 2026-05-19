package com.yintp.algorithm.leetcode.topic8;

import org.junit.Assert;
import org.junit.Test;

public class Q1143LongestCommonSubsequenceTest {

    private final Q1143LongestCommonSubsequence solution = new Q1143LongestCommonSubsequence();

    @Test
    public void testCase1() {
        Assert.assertEquals(3, solution.longestCommonSubsequence("abcde", "ace"));
    }

    @Test
    public void testNoCommon() {
        Assert.assertEquals(0, solution.longestCommonSubsequence("abc", "def"));
    }
}
