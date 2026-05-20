package com.yintp.algorithm.leetcode.phase1.array;

/**
 * #704 二分查找 (Binary Search) - 简单
 * Topic: 数组 | Phase1 Day1
 *
 * 给定升序整数数组 nums 和目标值 target，返回目标值的下标，不存在返回 -1。
 *
 * 示例：nums=[-1,0,3,5,9,12], target=9 → 4
 *
 * 思路：左闭右闭区间二分，mid = left + (right - left) / 2 防溢出
 */
public class Q704BinarySearch {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return mid;
            else if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
}
