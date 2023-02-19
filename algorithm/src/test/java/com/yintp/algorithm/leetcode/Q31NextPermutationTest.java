package com.yintp.algorithm.leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yintp
 */
public class Q31NextPermutationTest {
    @Test
    public void testCase1() {
        Q31NextPermutation q31NextPermutation = new Q31NextPermutation();
        int[] nums = {1, 2, 3};
        q31NextPermutation.nextPermutation(nums);
        int[] expect = {1, 3, 2};
        Assert.assertArrayEquals(expect, nums);
    }

    @Test
    public void testCase2() {
        Q31NextPermutation q31NextPermutation = new Q31NextPermutation();
        int[] nums = {3, 2, 1};
        q31NextPermutation.nextPermutation(nums);
        int[] expect = {1, 2, 3};
        Assert.assertArrayEquals(expect, nums);
    }

    @Test
    public void testCase3() {
        Q31NextPermutation q31NextPermutation = new Q31NextPermutation();
        int[] nums = {2, 3, 1};
        q31NextPermutation.nextPermutation(nums);
        int[] expect = {3, 1, 2};
        Assert.assertArrayEquals(expect, nums);
    }

    @Test
    public void testCase4() {
        Q31NextPermutation q31NextPermutation = new Q31NextPermutation();
        int[] nums = {1, 3, 2};
        q31NextPermutation.nextPermutation(nums);
        int[] expect = {2, 1, 3};
        Assert.assertArrayEquals(expect, nums);
    }
}
