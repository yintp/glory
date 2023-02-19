package com.yintp.algorithm.leetcode;

import org.junit.Test;

/**
 * @author yintp
 */
public class Q30FindSubstringTest {
    @Test
    public void testCase1() {
        Q30FindSubstring q30FindSubstring = new Q30FindSubstring();
        String[] words = {"foo", "bar"};
        q30FindSubstring.findSubstring("barfoothefoobarman", words);
    }
}
