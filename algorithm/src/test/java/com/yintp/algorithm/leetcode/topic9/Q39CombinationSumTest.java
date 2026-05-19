package com.yintp.algorithm.leetcode.topic9;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class Q39CombinationSumTest {

    private final Q39CombinationSum solution = new Q39CombinationSum();

    @Test
    public void testCase1() {
        List<List<Integer>> result = solution.combinationSum(new int[]{2, 3, 6, 7}, 7);
        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testCase2() {
        List<List<Integer>> result = solution.combinationSum(new int[]{2, 3, 5}, 8);
        Assert.assertEquals(3, result.size());
    }
}
