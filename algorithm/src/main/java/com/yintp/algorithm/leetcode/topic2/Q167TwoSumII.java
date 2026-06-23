package com.yintp.algorithm.leetcode.topic2;

/**
 * #167 两数之和 II - 输入有序数组 (Two Sum II) - Easy
 * 专题: 双指针 | 第4周 Day2
 *
 * 给你一个下标从 1 开始的整数数组 numbers（已按非递减顺序排列），
 * 找出满足相加之和等于目标数 target 的两个数，返回下标数组（1-based）。
 *
 * 示例: numbers=[2,7,11,15], target=9 → [1,2]
 *
 * 思路: 相向双指针 - 有序数组利用单调性收缩区间
 */
public class Q167TwoSumII {
    public int[] twoSum(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) return new int[]{left + 1, right + 1};
            else if (sum < target) left++;
            else right--;
        }
        return new int[0];
    }
}
