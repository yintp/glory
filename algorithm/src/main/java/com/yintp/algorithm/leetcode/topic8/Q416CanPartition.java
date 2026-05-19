package com.yintp.algorithm.leetcode.topic8;

/**
 * #416 分割等和子集 (Partition Equal Subset Sum) - Medium
 * 专题: 动态规划/0-1背包 | 第10周 Day4
 *
 * 给你一个只包含正整数的非空数组 nums，判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
 *
 * 思路: 0-1背包
 *   目标: 找出能凑出 sum/2 的子集
 *   dp[j]: 是否能凑出容量j
 *   dp[0]=true; 对每个数num: 从后向前遍历 dp[j] |= dp[j-num]
 */
public class Q416CanPartition {
    public boolean canPartition(int[] nums) {
        // TODO: 0-1背包判断是否能凑出sum/2
        return false;
    }
}
