package com.yintp.algorithm.leetcode.topic8;

/**
 * #322 零钱兑换 (Coin Change) - Medium
 * 专题: 动态规划/完全背包 | 第10周 Day2
 *
 * 给你一个整数数组 coins 表示不同面额的硬币，以及一个整数 amount 表示总金额。
 * 计算凑成总金额所需的最少硬币个数，如果无法凑成返回 -1。
 *
 * 思路: 完全背包DP
 *   dp[i] = 凑成金额i所需最少硬币数
 *   dp[0]=0, dp[i] = min(dp[i-coin]+1) for coin in coins
 */
public class Q322CoinChange {
    public int coinChange(int[] coins, int amount) {
        // TODO: 完全背包DP
        return -1;
    }
}
