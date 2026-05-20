package com.yintp.algorithm.leetcode.topic1;

import java.util.HashMap;
import java.util.Map;

/**
 * #1 两数之和 (Two Sum) - Easy
 * 专题: 数组 & 哈希表 | 第3周 Day1
 *
 * 给定一个整数数组 nums 和一个整数目标值 target，找出和为目标值的两个整数下标。
 * 每种输入只有一个答案，且不能重复使用同一个元素。
 *
 * 示例: nums=[2,7,11,15], target=9 → [0,1]
 *
 * 思路: 哈希表 - 遍历时查找 target-nums[i] 是否已存在
 */
public class Q1TwoSum {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap();
        for (int i = 0;i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            } else {
                map.put(nums[i], i);
            }
        }
        return new int[0];
    }
}
