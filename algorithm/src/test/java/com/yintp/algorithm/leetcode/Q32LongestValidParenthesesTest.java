package com.yintp.algorithm.leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yintp
 */
public class Q32LongestValidParenthesesTest {
    @Test
    public void testCase1() {
        Q32LongestValidParentheses q32LongestValidParentheses = new Q32LongestValidParentheses();
        int length = q32LongestValidParentheses.longestValidParentheses("(()");
        Assert.assertEquals(2, length);
    }

    @Test
    public void testCase2() {
        Q32LongestValidParentheses q32LongestValidParentheses = new Q32LongestValidParentheses();
        int length = q32LongestValidParentheses.longestValidParentheses(")()())");
        Assert.assertEquals(4, length);
    }

    @Test
    public void testCase3() {
        Q32LongestValidParentheses q32LongestValidParentheses = new Q32LongestValidParentheses();
        int length = q32LongestValidParentheses.longestValidParentheses("");
        Assert.assertEquals(0, length);
    }
}
