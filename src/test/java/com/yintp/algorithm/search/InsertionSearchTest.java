package com.yintp.algorithm.search;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yintp
 */
public class InsertionSearchTest {
    private int[] case1Arr = {1, 3, 5, 7, 8, 9, 10, 11, 12};

    @Test
    public void testCase1() {
        InsertionSearch insertionSearch = new InsertionSearch();
        int i = insertionSearch.insertionSearch(case1Arr, 9);
        Assert.assertEquals(5, i);
    }

    @Test
    public void testCase1For2() {
        InsertionSearch insertionSearch = new InsertionSearch();
        int i = insertionSearch.insertionSearch2(case1Arr, 9);
        Assert.assertEquals(5, i);
    }
}
