package com.yintp.algorithm.leetcode.topic7;

/**
 * #33 搜索旋转排序数组 (Search in Rotated Sorted Array) - Medium
 * 专题: 二分搜索 | 第9周 Day2
 *
 * 整数数组 nums 按升序排列，在传递给函数之前经过了若干次旋转。
 * 给你旋转后的数组 nums 和整数 target，若 target 存在则返回其下标，否则返回 -1。
 *
 * 示例: nums=[4,5,6,7,0,1,2], target=0 → 4
 *
 * 思路: 变形二分 - 每次判断 mid 在哪个有序半段，再决定 target 在哪侧
 *   - 若 nums[left] <= nums[mid]，左半段有序
 *     - target 在 [nums[left], nums[mid]) 则 right=mid-1，否则 left=mid+1
 *   - 否则右半段有序，同理判断
 */
public class Q33SearchRotatedArray {
    public int search(int[] nums, int target) {
        // TODO: 变形二分，判断有序半段后缩小区间
        return -1;
    }
}
