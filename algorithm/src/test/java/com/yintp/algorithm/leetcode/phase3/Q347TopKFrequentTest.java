package com.yintp.algorithm.leetcode.phase3;

import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;

public class Q347TopKFrequentTest {

    private final Q347TopKFrequent solution = new Q347TopKFrequent();

    @Test
    public void testCase1() {
        int[] result = solution.topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2);
        Arrays.sort(result);
        Assert.assertArrayEquals(new int[]{1, 2}, result);
    }
}
