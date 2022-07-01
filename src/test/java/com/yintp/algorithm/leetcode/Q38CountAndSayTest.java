package com.yintp.algorithm.leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yintp
 */
public class Q38CountAndSayTest {
    @Test
    public void testCase1() {
        Q38CountAndSay q38CountAndSay = new Q38CountAndSay();
        String s = q38CountAndSay.countAndSay(1);
        Assert.assertEquals("1", s);
    }

    @Test
    public void testCase2() {
        Q38CountAndSay q38CountAndSay = new Q38CountAndSay();
        String s = q38CountAndSay.countAndSay(2);
        Assert.assertEquals("11", s);
    }

    @Test
    public void testCase3() {
        Q38CountAndSay q38CountAndSay = new Q38CountAndSay();
        String s = q38CountAndSay.countAndSay(3);
        Assert.assertEquals("21", s);
    }

    @Test
    public void testCase4() {
        Q38CountAndSay q38CountAndSay = new Q38CountAndSay();
        String s = q38CountAndSay.countAndSay(4);
        Assert.assertEquals("1211", s);
    }
}
