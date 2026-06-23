package com.yintp.algorithm.leetcode.topic2;

/**
 * #283 移动零 (Move Zeroes) - Easy
 * 专题: 双指针 | 第4周 Day1
 *
 * 给定一个数组 nums，将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * 要求: 原地操作，最小化操作次数。
 *
 * 示例: [0,1,0,3,12] → [1,3,12,0,0]
 *
 * 思路: 同向双指针 - 慢指针指向下一个填充位置
 */
public class Q283MoveZeroes {
    public void moveZeroes(int[] nums) {
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                nums[slow++] = nums[fast];
            }
        }
        while (slow < nums.length) {
            nums[slow++] = 0;
        }
    }
}
