package com.yintp.algorithm.leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yintp
 */
public class Q33SearchTest {
    @Test
    public void testCase1() {
        Q33Search q33Search = new Q33Search();
        int[] arr = {4, 5, 6, 7, 0, 1, 2};
        int search = q33Search.search(arr, 0);
        Assert.assertEquals(4, search);
    }

    @Test
    public void testCase2() {
        Q33Search q33Search = new Q33Search();
        int[] arr = {4, 5, 6, 7, 0, 1, 2};
        int search = q33Search.search(arr, 3);
        Assert.assertEquals(-1, search);
    }

    @Test
    public void testCase3() {
        Q33Search q33Search = new Q33Search();
        int[] arr = {1};
        int search = q33Search.search(arr, 0);
        Assert.assertEquals(-1, search);
    }
}
