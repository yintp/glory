package com.yintp.algorithm.leetcode.topic8;

/**
 * #300 最长递增子序列 (Longest Increasing Subsequence) - Medium
 * 专题: 动态规划/LIS | 第10周 Day2
 *
 * 给你一个整数数组 nums，找到其中最长严格递增子序列的长度。
 *
 * 示例: [10,9,2,5,3,7,101,18] → 4 ([2,3,7,101])
 *
 * 思路: DP O(n²) - dp[i]=以nums[i]结尾的最长子序列长度
 *   dp[i] = max(dp[j]+1) for j<i where nums[j]<nums[i]
 *   进阶: 贪心+二分 O(n log n)
 */
public class Q300LengthOfLIS {
    public int lengthOfLIS(int[] nums) {
        // TODO: DP O(n²) 或 贪心+二分 O(n log n)
        return 0;
    }
}
