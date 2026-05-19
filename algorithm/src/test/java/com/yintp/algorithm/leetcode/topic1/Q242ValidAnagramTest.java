package com.yintp.algorithm.leetcode.topic1;

import org.junit.Assert;
import org.junit.Test;

public class Q242ValidAnagramTest {

    private final Q242ValidAnagram solution = new Q242ValidAnagram();

    @Test
    public void testIsAnagram() {
        Assert.assertTrue(solution.isAnagram("anagram", "nagaram"));
    }

    @Test
    public void testNotAnagram() {
        Assert.assertFalse(solution.isAnagram("rat", "car"));
    }

    @Test
    public void testDifferentLength() {
        Assert.assertFalse(solution.isAnagram("a", "ab"));
    }
}
