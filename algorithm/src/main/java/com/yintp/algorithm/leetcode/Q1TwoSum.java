package com.yintp.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author zihao.yin
 * @since 2025/10/16 15:31
 */
public class Q1TwoSum {
    public int[] twoSum(int[] nums, int target) {
        int cap = (int) (nums.length / 0.75) + 1;
        Map<Integer, Integer> indexMap = new HashMap<>(cap);
        for (int i = 0; i < nums.length; i++) {
            if (indexMap.containsKey(target - nums[i])) {
                return new int[]{i, indexMap.get(target - nums[i])};
            } else {
                indexMap.put(nums[i], i);
            }
        }
        return new int[]{0, 0};
    }
}
