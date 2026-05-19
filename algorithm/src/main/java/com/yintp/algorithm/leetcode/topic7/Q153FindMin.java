package com.yintp.algorithm.leetcode.topic7;

/**
 * #153 寻找旋转排序数组中的最小值 (Find Minimum in Rotated Sorted Array) - Medium
 * 专题: 二分搜索 | 第9周 Day2
 *
 * 已知一个长度为 n 的数组，预先按升序排列，经由 1 到 n 次旋转后得到 nums。
 * 返回数组中的最小元素。
 *
 * 示例: [3,4,5,1,2] → 1; [4,5,6,7,0,1,2] → 0
 *
 * 思路: 变形二分 - 比较 nums[mid] 和 nums[right]
 *   - nums[mid] > nums[right]: 最小值在右半，left = mid + 1
 *   - 否则: 最小值在左半(含mid)，right = mid
 */
public class Q153FindMin {
    public int findMin(int[] nums) {
        // TODO: 变形二分，比较mid与right
        return 0;
    }
}
