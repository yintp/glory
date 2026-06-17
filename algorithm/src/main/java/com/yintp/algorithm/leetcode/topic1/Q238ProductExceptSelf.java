package com.yintp.algorithm.leetcode.topic1;

/**
 * #238 除自身以外数组的乘积 (Product of Array Except Self) - Medium
 * 专题: 数组 & 哈希表 | 第3周 Day4
 *
 * 给你一个整数数组 nums，返回数组 answer，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积。
 * 要求: 不能使用除法，O(n) 时间复杂度，O(1) 额外空间。
 *
 * 示例: [1,2,3,4] → [24,12,8,6]
 *
 * 思路: 前缀积 + 后缀积，两次遍历
 */
public class Q238ProductExceptSelf {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];
        answer[0] = 1;
        for (int i = 1; i < n; i++) {
            answer[i] = answer[i - 1] * nums[i - 1];
        }
        int suffix = 1;
        for (int i = n - 1; i >= 0; i--) {
            answer[i] *= suffix;
            suffix *= nums[i];
        }
        return answer;
    }
}
