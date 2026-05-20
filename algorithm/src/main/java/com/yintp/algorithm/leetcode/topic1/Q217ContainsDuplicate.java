package com.yintp.algorithm.leetcode.topic1;

import java.util.HashSet;
import java.util.Set;

/**
 * #217 存在重复元素 (Contains Duplicate) - Easy
 * 专题: 数组 & 哈希表 | 第3周 Day1
 *
 * 给你一个整数数组 nums，如果任一值在数组中出现至少两次，返回 true；
 * 如果每个元素互不相同，返回 false。
 *
 * 示例: [1,2,3,1] → true, [1,2,3,4] → false
 *
 * 思路: HashSet 去重，add 返回 false 则重复
 */
public class Q217ContainsDuplicate {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            boolean add = set.add(nums[i]);
            if (!add) {
                return true;
            }
        }
        return false;
    }
}
