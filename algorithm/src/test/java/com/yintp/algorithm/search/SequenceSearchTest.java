package com.yintp.algorithm.search;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yintp
 */
public class SequenceSearchTest {
    private int[] case1Arr = {1, 3, 5, 7, 8, 9, 10, 11, 12};

    @Test
    public void testCase1() {
        SequenceSearch sequenceSearch = new SequenceSearch();
        int i = sequenceSearch.sequenceSearch(case1Arr, 9);
        Assert.assertEquals(5, i);
    }
}
