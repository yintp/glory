package com.yintp.algorithm.leetcode.phase3;

import java.util.Map;
import java.util.HashMap;

/**
 * #560 和为 K 的子数组 (Subarray Sum Equals K) - Medium
 * 专题: 前缀和 | 第11周 Day4
 *
 * 给你一个整数数组 nums 和一个整数 k，统计并返回该数组中和为 k 的子数组的个数。
 *
 * 示例: [1,1,1], k=2 → 2
 *
 * 思路: 前缀和 + HashMap
 *   prefixSum[i] - prefixSum[j] = k → prefixSum[j] = prefixSum[i] - k
 *   遍历时查找map中 prefixSum[i]-k 出现次数
 */
public class Q560SubarraySum {
    public int subarraySum(int[] nums, int k) {
        // TODO: 前缀和 + HashMap
        return 0;
    }
}
