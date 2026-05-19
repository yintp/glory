package com.yintp.algorithm.leetcode.topic8;

/**
 * #198 打家劫舍 (House Robber) - Easy
 * 专题: 动态规划 | 第10周 Day1
 *
 * 不能抢劫相邻房屋，求能抢到的最大金额。
 *
 * 示例: [1,2,3,1] → 4; [2,7,9,3,1] → 12
 *
 * 思路: 线性DP
 *   dp[i] = max(dp[i-1], dp[i-2]+nums[i])
 *   dp[i]: 抢到第i间时的最大金额
 */
public class Q198Rob {
    public int rob(int[] nums) {
        // TODO: 线性DP
        return 0;
    }
}
