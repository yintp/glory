package com.yintp.algorithm.leetcode.topic2;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * #15 三数之和 (3Sum) - Medium
 * 专题: 双指针 | 第4周 Day2
 *
 * 给你一个整数数组 nums，判断是否存在三元组 [nums[i], nums[j], nums[k]]
 * 使得 nums[i] + nums[j] + nums[k] == 0，返回所有不重复的三元组。
 *
 * 示例: [-1,0,1,2,-1,-4] → [[-1,-1,2],[-1,0,1]]
 *
 * 思路: 排序 + 固定一个数 + 相向双指针，注意跳过重复元素
 */
public class Q15ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }
}
