package com.yintp.algorithm.leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author zihao.yin
 * @since 2025/10/21 11:16
 */
public class Q49GroupAnagramsTest {
    @Test
    public void testCase1() {
        Q49GroupAnagrams q49GroupAnagrams = new Q49GroupAnagrams();
        String[] nums = {"abc", "bca", "ad", "da"};
        List<List<String>> result = q49GroupAnagrams.groupAnagrams(nums);
        Assert.assertNotNull(result);
    }
}
