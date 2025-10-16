package com.yintp.algorithm.leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author zihao.yin
 * @since 2025/10/16 15:42
 */
public class Q1TwoSumTest {
    @Test
    public void testCase1() {
        Q1TwoSum q1TwoSum = new Q1TwoSum();
        int[] nums = {2, 1, 4, 3, 5};
        int[] result = q1TwoSum.twoSum(nums, 8);
        Assert.assertArrayEquals(result, new int[]{4, 3});
    }
}
