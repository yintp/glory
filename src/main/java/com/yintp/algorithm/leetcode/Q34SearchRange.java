package com.yintp.algorithm.leetcode;

/**
 * Title：在排序数组中查找元素的第一个和最后一个
 * Desc：给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
 * 如果数组中不存在目标值 target，返回 [-1, -1]。
 * 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
 * 示例 1：
 * 输入：nums = [5,7,7,8,8,10], target = 8
 * 输出：[3,4]
 * 示例 2：
 * 输入：nums = [5,7,7,8,8,10], target = 6
 * 输出：[-1,-1]
 * 示例 3：
 * 输入：nums = [], target = 0
 * 输出：[-1,-1]
 * 提示：
 * 0 <= nums.length <= 10⁵
 * -10⁹ <= nums[i] <= 10⁹
 * nums 是一个非递减数组
 * -10⁹ <= target <= 10⁹
 *
 * @author yintp
 */
public class Q34SearchRange {
    /**
     * 思路：先用二分法找到是否存在指定数字位置，再分别从左右用二分法获取最左、右位置
     */
    public int[] searchRange(int[] nums, int target) {
        int[] result = {-1, -1};
        int l = 0, r = nums.length - 1;
        int point = -1;
        while (l < r) {
            int mid = (l + r) / 2;
            if (target == nums[mid]) {
                point = mid;
                break;
            } else if (target < nums[mid]) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        if (point == -1) {
            return result;
        }
        // 左边
        result[0] = point;
        l = 0;
        r = point;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (target == nums[mid]) {
                result[0] = mid;
                r = mid - 1;
            } else if (target > nums[mid]) {
                l = mid + 1;
            }
        }
        // 右边
        result[1] = point;
        l = point;
        r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (target == nums[mid]) {
                result[1] = mid;
                l = mid + 1;
            } else if (target < nums[mid]) {
                r = mid - 1;
            }
        }
        return result;
    }
}
