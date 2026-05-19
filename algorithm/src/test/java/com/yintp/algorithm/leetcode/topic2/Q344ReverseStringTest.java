package com.yintp.algorithm.leetcode.topic2;

import org.junit.Assert;
import org.junit.Test;

public class Q344ReverseStringTest {

    private final Q344ReverseString solution = new Q344ReverseString();

    @Test
    public void testCase1() {
        char[] s = {'h', 'e', 'l', 'l', 'o'};
        solution.reverseString(s);
        Assert.assertArrayEquals(new char[]{'o', 'l', 'l', 'e', 'h'}, s);
    }

    @Test
    public void testCase2() {
        char[] s = {'H', 'a', 'n', 'n', 'a', 'h'};
        solution.reverseString(s);
        Assert.assertArrayEquals(new char[]{'h', 'a', 'n', 'n', 'a', 'H'}, s);
    }
}
