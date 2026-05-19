package com.yintp.algorithm.leetcode.topic1;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class Q49GroupAnagramsTest {

    private final Q49GroupAnagrams solution = new Q49GroupAnagrams();

    @Test
    public void testCase1() {
        List<List<String>> result = solution.groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"});
        Assert.assertEquals(3, result.size());
    }

    @Test
    public void testSingleEmpty() {
        List<List<String>> result = solution.groupAnagrams(new String[]{""});
        Assert.assertEquals(1, result.size());
    }
}
