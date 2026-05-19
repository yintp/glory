package com.yintp.algorithm.leetcode.topic2;

import org.junit.Assert;
import org.junit.Test;

public class Q3LengthOfLongestSubstringTest {

    private final Q3LengthOfLongestSubstring solution = new Q3LengthOfLongestSubstring();

    @Test
    public void testCase1() {
        Assert.assertEquals(3, solution.lengthOfLongestSubstring("abcabcbb"));
    }

    @Test
    public void testAllSame() {
        Assert.assertEquals(1, solution.lengthOfLongestSubstring("bbbbb"));
    }

    @Test
    public void testCase3() {
        Assert.assertEquals(3, solution.lengthOfLongestSubstring("pwwkew"));
    }
}
