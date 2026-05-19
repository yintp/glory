package com.yintp.algorithm.leetcode.topic4;

import org.junit.Assert;
import org.junit.Test;

public class Q20ValidParenthesesTest {

    private final Q20ValidParentheses solution = new Q20ValidParentheses();

    @Test
    public void testValid() {
        Assert.assertTrue(solution.isValid("()"));
        Assert.assertTrue(solution.isValid("()[]{}"));
        Assert.assertTrue(solution.isValid("{[]}"));
    }

    @Test
    public void testInvalid() {
        Assert.assertFalse(solution.isValid("(]"));
        Assert.assertFalse(solution.isValid("([)]"));
    }
}
