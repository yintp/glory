package com.yintp.algorithm.leetcode.topic2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class Q15ThreeSumTest {

    private final Q15ThreeSum solution = new Q15ThreeSum();

    @Test
    public void testCase1() {
        List<List<Integer>> result = solution.threeSum(new int[]{-1, 0, 1, 2, -1, -4});
        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testAllZeros() {
        List<List<Integer>> result = solution.threeSum(new int[]{0, 0, 0});
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(Arrays.asList(0, 0, 0), result.get(0));
    }
}
