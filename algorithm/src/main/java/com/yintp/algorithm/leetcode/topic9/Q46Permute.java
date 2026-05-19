package com.yintp.algorithm.leetcode.topic9;

import java.util.List;
import java.util.ArrayList;

/**
 * #46 全排列 (Permutations) - Medium
 * 专题: 回溯 | 第9周 Day3
 *
 * 给定一个不含重复数字的数组 nums，返回其所有可能的全排列。
 *
 * 示例: [1,2,3] → [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * 思路: 回溯 + used数组记录已使用元素
 */
public class Q46Permute {
    public List<List<Integer>> permute(int[] nums) {
        // TODO: 回溯枚举全排列
        return new ArrayList<>();
    }

    private void backtrack(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> result) {
        // TODO: 递归回溯
    }
}
